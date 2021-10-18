package com.vten.gedeon.connector.elastic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.connector.elastic.bean.GetResponse;
import com.vten.gedeon.connector.elastic.bean.SaveResponse;
import com.vten.gedeon.connector.elastic.bean.SearchResponse;
import com.vten.gedeon.dao.connector.nosql.NoSQLConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.model.property.PropertyType;

@Configuration
public class GedeonElasticConnector extends NoSQLConnector {

	private static final Logger LOG = LoggerFactory.getLogger(GedeonElasticConnector.class);

	@Value("${elasticsearch.host}")
	private String elasticsearchHost;

	@Value("${elasticsearch.port}")
	private int elasticsearchPort;

	@Value("${elasticsearch.protocol}")
	private String elasticsearchProtocol;

	private RestClient client;
	
	@Bean(destroyMethod = "close")
	protected RestClient getClient() {
		// Security

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));

		RestClientBuilder builder = RestClient
				.builder(new HttpHost(elasticsearchHost, elasticsearchPort, elasticsearchProtocol))
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});
		return builder.build();
	}

	@PostConstruct
	public void init() {
		client = getClient();
	}

	@Override
	public int initDB() {
		return 0;
	}
	
	@Override
	public void cleanUp() {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				LOG.warn("Issue while closing Elastic client.", e);
			}
		}
	}

	@Override
	public GedeonDBObject getObject(String className, String id) {
		try {
			//Create request &terminate_after=1
			Request request = new Request("GET","/gedeon-".concat(className.toLowerCase()).concat("/_doc/").concat(id));
			//Perform request
			Response response = client.performRequest(request);
			GetResponse getResponse = new GetResponse(EntityUtils.toString(response.getEntity()));
			LOG.debug("GetObject response : {}",getResponse);
			
			return getResponse.getDbObject();
		} catch (IOException e) {
			LOG.error("getObject error", e);
			throw new GedeonRuntimeException(
					"getObject error - db connection issue while retrieving object '%s' with id '%s'", className, id);
		} catch (RuntimeException e) {
			LOG.error("getObject error", e);
			throw new GedeonRuntimeException(
					"getObject error - unexpected issue while retrieving object '%s' with id '%s', see trace logs for more information.");
		}
	}
	
	protected String dbObjectToJSONString(GedeonDBObject obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Map.Entry<String, Object> entry;
		Iterator<Map.Entry<String, Object>> it = obj.getMapData().entrySet().iterator();
		while(it.hasNext()) {
			entry = it.next();
			sb.append("\"").append(entry.getKey()).append("\":");
			
			sb.append(objectToJSONString(entry.getValue()));
			
			if(it.hasNext())
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}
	
	protected String objectToJSONString(Object o) {
		if((o instanceof String) || (o instanceof LocalDateTime) 
				|| (o instanceof PropertyType) || (o instanceof GedId)) {
			return String.format("\"%s\"", o);
		} else if (o == null){
			return "null";
		} else if(o instanceof List) {
			return ((List<?>)o).stream()
					.map(this::objectToJSONString)
					.collect(Collectors.joining(",","[","]"));
		}
		return o.toString();
	}

	@Override
	public GedeonDBObject saveObject(String className, GedeonDBObject obj) {
		try {
			// Create index request
			Request request = new Request("POST","/gedeon-".concat(className.toLowerCase().concat("/_doc/")));
			request.setJsonEntity(dbObjectToJSONString(obj));
			// Send request
			Response response = client.performRequest(request);
			SaveResponse saveResponse = new SaveResponse(EntityUtils.toString(response.getEntity()));
			LOG.debug("SaveObject response : {}",saveResponse);
			obj.setId(saveResponse.getId());
			obj.setSeqNo(saveResponse.getSeqNo());
			
		} catch (IOException e) {
			LOG.error("saveObject error", e);
			throw new GedeonRuntimeException("saveObject error - db connection issue while saving object '%s'", className);
		} catch (RuntimeException e) {
			LOG.error("saveObject error", e);
			throw new GedeonRuntimeException(
					"saveObject error - unexpected issue while saving object '%s', see trace logs for more information.",
					className);
		}
		return obj;
	}

	@Override
	public void deleteObject(String className, String id) {
		try {
			// Create delete request
			Request request = new Request("DELETE","/gedeon-".concat(className).concat("*/_doc/").concat(id));
			//Perform request
			Response deleteResponse = client.performRequest(request);
			String responseBody = EntityUtils.toString(deleteResponse.getEntity()); 
			LOG.debug("DeleteObject response : {}",responseBody);
		} catch (IOException e) {
			LOG.error("", e);
			throw new GedeonRuntimeException(
					"saveObject error - db connection issue while deleting object '%s' with id '%s'", className, id);
		} catch (RuntimeException e) {
			LOG.error("", e);
			throw new GedeonRuntimeException(
					"saveObject error - unexpected issue while deleting object '%s' with id '%s', see trace logs for more information.",
					className, id);
		}
	}

	@Override
	public List<GedeonDBObject> search(String className, String query) {
		
		try {
			String index = "/gedeon-".concat(className.toLowerCase()).concat("*/_search");
			LOG.debug("Execute query : GET {} {}",index,query);
			// Build search request
			Request request = new Request("GET",index);
			request.setJsonEntity(query);
			//Perform search
			Response searchResponse = client.performRequest(request);
			String responseBody = EntityUtils.toString(searchResponse.getEntity()); 
			LOG.debug("Search response : {}",responseBody);
			SearchResponse response = new SearchResponse(responseBody);
			//Return response list
			return response.getHits();

		} catch (IOException e) {
			throw new GedeonRuntimeException("saveObject error - db connection issue while saving object '%s'");
		}
	}

}
