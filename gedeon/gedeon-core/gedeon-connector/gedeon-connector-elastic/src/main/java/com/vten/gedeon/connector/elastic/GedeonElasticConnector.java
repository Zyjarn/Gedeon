package com.vten.gedeon.connector.elastic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.vten.gedeon.api.utils.GedId;
import com.vten.gedeon.connector.elastic.bean.GetResponse;
import com.vten.gedeon.connector.elastic.bean.SaveResponse;
import com.vten.gedeon.connector.elastic.bean.SearchResponse;
import com.vten.gedeon.dao.connector.nosql.DatabaseConnector;
import com.vten.gedeon.dao.data.GedeonDBObject;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.model.property.PropertyType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration
@Service
public class GedeonElasticConnector extends DatabaseConnector {

	private static final String GEDEON_INDICES_ALL_COLLECTION = "/gedeon-%s*";
	private static final String GEDEON_INDICES_FORMAT = "/gedeon-%s-%s%s";
	private static final String API_DOC = "/_doc/";

	@Autowired
	private RestClient client;

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
				log.warn("Issue while closing Elastic client.", e);
			}
		}
	}

	@Override
	public GedeonDBObject getObject(String collectionName, String className, String id) {
		try {
			String uri = String
					.format(GEDEON_INDICES_FORMAT, collectionName.toLowerCase(), className.toLowerCase(), API_DOC)
					.concat(id);

			log.debug("GetObject GET '{}'", uri);
			// Create request &terminate_after=1
			Request request = new Request("GET", uri);
			// Perform request
			Response response = client.performRequest(request);
			GetResponse getResponse = new GetResponse(EntityUtils.toString(response.getEntity()));
			log.debug("GetObject response : {}", getResponse);

			return getResponse.getDbObject();
		} catch (IOException e) {
			log.error("getObject error", e);
			throw new GedeonRuntimeException(e,
					"getObject error - db connection issue while retrieving object '%s' with id '%s'", className, id);
		} catch (RuntimeException e) {
			log.error("getObject error", e);
			throw new GedeonRuntimeException(e,
					"getObject error - unexpected issue while retrieving object '%s' with id '%s', see trace logs for more information.",
					className, id);
		}
	}

	protected String dbObjectToJSONString(GedeonDBObject obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		Map.Entry<String, Object> entry;
		Iterator<Map.Entry<String, Object>> it = obj.getMapData().entrySet().iterator();
		while (it.hasNext()) {
			entry = it.next();
			sb.append("\"").append(entry.getKey()).append("\":");

			sb.append(objectToJSONString(entry.getValue()));

			if (it.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	protected String objectToJSONString(Object o) {
		if ((o instanceof String) || (o instanceof LocalDateTime) || (o instanceof PropertyType)
				|| (o instanceof GedId)) {
			return String.format("\"%s\"", o);
		} else if (o == null) {
			return "null";
		} else if (o instanceof List) {
			return ((List<?>) o).stream().map(this::objectToJSONString).collect(Collectors.joining(",", "[", "]"));
		}
		return o.toString();
	}

	@Override
	public GedeonDBObject createObject(String collectionName, String className, GedeonDBObject obj) {
		try {
			String uri = String
					.format(GEDEON_INDICES_FORMAT, collectionName.toLowerCase(), className.toLowerCase(), API_DOC)
					.concat(StringUtils.isNotBlank(obj.getId()) ? obj.getId() : StringUtils.EMPTY);
			String jsonObj = dbObjectToJSONString(obj);

			log.debug("createObject POST '{}' : {}", uri, jsonObj);
			// Create index request
			Request request = new Request("POST", uri);
			request.setJsonEntity(jsonObj);
			// Send request
			Response response = client.performRequest(request);
			SaveResponse saveResponse = new SaveResponse(EntityUtils.toString(response.getEntity()));
			log.debug("createObject response : {}", saveResponse);
			obj.setId(saveResponse.getId());
			obj.setSeqNo(saveResponse.getSeqNo());

		} catch (IOException e) {
			log.error("createObject error", e);
			throw new GedeonRuntimeException(e,
					"createObject error - db connection issue while saving object '%s' - %s", className,
					e.getMessage());
		} catch (RuntimeException e) {
			log.error("createObject error", e);
			throw new GedeonRuntimeException(e, "createObject error - unexpected issue while saving object '%s', %s",
					className, e.getMessage());
		}
		return obj;
	}

	@Override
	public GedeonDBObject saveObject(String collectionName, String className, GedeonDBObject obj) {
		try {
			// Create index request
			Request request = new Request("POST",
					String.format(GEDEON_INDICES_FORMAT, collectionName.toLowerCase(), className.toLowerCase(), API_DOC)
							.concat(StringUtils.isNotBlank(obj.getId()) ? obj.getId() : StringUtils.EMPTY));
			request.setJsonEntity(dbObjectToJSONString(obj));
			// Send request
			Response response = client.performRequest(request);
			SaveResponse saveResponse = new SaveResponse(EntityUtils.toString(response.getEntity()));
			log.debug("SaveObject response : {}", saveResponse);
			obj.setId(saveResponse.getId());
			obj.setSeqNo(saveResponse.getSeqNo());

		} catch (IOException e) {
			log.error("saveObject error", e);
			throw new GedeonRuntimeException(e, "saveObject error - db connection issue while saving object '%s'",
					className);
		} catch (RuntimeException e) {
			log.error("saveObject error", e);
			throw new GedeonRuntimeException(e,
					"saveObject error - unexpected issue while saving object '%s', see trace logs for more information.",
					className);
		}
		return obj;
	}

	@Override
	public void deleteObject(String collectionName, String className, String id) {
		try {
			// Create delete request
			Request request = new Request("DELETE",
					String.format(GEDEON_INDICES_FORMAT, collectionName.toLowerCase(), className.toLowerCase(), API_DOC)
							.concat(id));
			// Perform request
			Response deleteResponse = client.performRequest(request);
			String responseBody = EntityUtils.toString(deleteResponse.getEntity());
			log.debug("deleteObject response : {}", responseBody);
		} catch (IOException e) {
			log.error("", e);
			throw new GedeonRuntimeException(e,
					"deleteObject error - db connection issue while deleting object '%s' with id '%s'", className);
		} catch (RuntimeException e) {
			log.error("", e);
			throw new GedeonRuntimeException(e,
					"deleteObject error - unexpected issue while deleting object '%s' with id '%s', see trace logs for more information.",
					className, id);
		}
	}

	@Override
	public void deleteCollection(String collectionName) {
		try {
			// Create delete request
			Request request = new Request("DELETE",
					String.format(GEDEON_INDICES_ALL_COLLECTION, collectionName.toLowerCase()));
			// Perform request
			Response deleteResponse = client.performRequest(request);
			String responseBody = EntityUtils.toString(deleteResponse.getEntity());
			log.debug("deleteObject response : {}", responseBody);
		} catch (IOException e) {
			log.error("", e);
			throw new GedeonRuntimeException(e,
					"deleteCollection error - db connection issue while deleting collection '%s'", collectionName);
		} catch (RuntimeException e) {
			log.error("", e);
			throw new GedeonRuntimeException(e,
					"deleteCollection error - unexpected issue while deleting collection '%s', see trace logs for more information.",
					collectionName);
		}
	}

	@Override
	public List<GedeonDBObject> search(String collectionName, String className, String query) {

		try {
			String index = String
					.format(GEDEON_INDICES_FORMAT, collectionName.toLowerCase(), className.toLowerCase(), "*")
					.concat("/_search");
			log.debug("Execute query : GET {} {}", index, query);
			// Build search request
			Request request = new Request("GET", index);
			request.setJsonEntity(query);
			// Perform search
			Response searchResponse = client.performRequest(request);
			String responseBody = EntityUtils.toString(searchResponse.getEntity());
			log.debug("Search response : {}", responseBody);
			SearchResponse response = new SearchResponse(responseBody);
			// Return response list
			return response.getHits();

		} catch (IOException e) {
			throw new GedeonRuntimeException(e, "saveObject error - db connection issue while searching object '%s.%s'",
					collectionName, className);
		}
	}

}
