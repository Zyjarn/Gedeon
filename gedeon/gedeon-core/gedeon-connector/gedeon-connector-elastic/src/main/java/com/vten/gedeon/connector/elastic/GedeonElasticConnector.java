package com.vten.gedeon.connector.elastic;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vten.gedeon.dao.connector.nosql.NoSQLConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.exception.OERuntimeException;

@Configuration
public class GedeonElasticConnector extends NoSQLConnector{
	
	private static final Logger LOG = LoggerFactory.getLogger(GedeonElasticConnector.class);

	@Value("${elasticsearch.host}")
    private String elasticsearchHost; 
	
	@Value("${elasticsearch.port}")
    private int elasticsearchPort; 
	
	@Value("${elasticsearch.protocol}")
    private String elasticsearchProtocol; 
	
	private RestHighLevelClient client;
	
	@Bean(destroyMethod = "close")
	protected RestHighLevelClient getClient() {
		//Security
		
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("", ""));
		
        RestClientBuilder builder = RestClient
                .builder(new HttpHost(
                		elasticsearchHost,
                		elasticsearchPort, elasticsearchProtocol))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        return new RestHighLevelClient(builder);
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
	public GedeonDBObject getObject(String className, String id) {
		GedeonDBObject matchObject = new GedeonDBObject();
		GetRequest getRequest = new GetRequest("gedeon-".concat(className.toLowerCase()));
		getRequest.id(id);
		try {
			GetResponse getObjectResponse = client.get(getRequest, RequestOptions.DEFAULT);
			matchObject.setMapData(
					getObjectResponse.getFields().entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getValues())));
		} catch (IOException e) {
			LOG.error("",e);
			throw new OERuntimeException("getObject error - db connection issue while retrieving object '%s' with id '%s'");
		} catch (RuntimeException e) {
			LOG.error("",e);
			throw new OERuntimeException("getObject error - unexpected issue while saving object '%s', see trace logs for more information.");
		}
		return matchObject;
	}

	@Override
	public GedeonDBObject saveObject(String className, GedeonDBObject obj) {
		try {
			//Create index request
			IndexRequest idxRequest = new IndexRequest("gedeon-".concat(className.toLowerCase()));
			//Set data of new object to request
			idxRequest.source(obj.getMapData());
		
			//Send request
			IndexResponse response = client.index(idxRequest, RequestOptions.DEFAULT);
			//TODO handle result 
			obj.setId(response.getId());
		} catch (IOException e) {
			LOG.error("",e);
			throw new OERuntimeException("saveObject error - db connection issue while saving object '%s'");
		} catch (RuntimeException e) {
			LOG.error("",e);
			throw new OERuntimeException("saveObject error - unexpected issue while saving object '%s', see trace logs for more information.");
		}
		return obj;
	}
	
	@Override
	public void deleteObject(String className, String id) {
		try {
			//Create delete request
			DeleteRequest deleteRequest = new DeleteRequest("gedeon-".concat(className.toLowerCase()), id);
			//send request
			DeleteResponse response = client.delete(deleteRequest,RequestOptions.DEFAULT);
			
		} catch (IOException e) {
			LOG.error("",e);
			throw new OERuntimeException("saveObject error - db connection issue while saving object '%s'");
		} catch (RuntimeException e) {
			LOG.error("",e);
			throw new OERuntimeException("saveObject error - unexpected issue while saving object '%s', see trace logs for more information.");
		}
	}
	
	public void search() {
		SearchRequest searchRequest = new SearchRequest("gedeon-*");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		QueryBuilder builder = QueryBuilders.matchQuery(elasticsearchHost, searchSourceBuilder);
		//builder.
		//searchSourceBuilder.query(); 
		searchRequest.source(searchSourceBuilder); 
		
		
		try {
			client.search(searchRequest, RequestOptions.DEFAULT);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cleanUp() {
		if(client != null) {
			try {
				client.close();
			} catch (IOException e) {
				LOG.warn("Issue while closing Elastic client.",e);
			}
		}
	}
	
}
