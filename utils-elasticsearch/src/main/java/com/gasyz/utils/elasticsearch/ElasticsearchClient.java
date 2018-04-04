package com.gasyz.utils.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoang on 2018/3/28.
 */
public class ElasticsearchClient {
    private String hostname;
    private Integer port;
    private String scheme;
    private static RestHighLevelClient restHighLevelClient;

    public ElasticsearchClient(String hostname, Integer port, String scheme) {
        this.hostname = hostname;
        this.port = port;
        this.scheme = scheme;
        if (restHighLevelClient == null) {
            restHighLevelClient = this.getClient(hostname,port,scheme);
        }
    }

    private RestHighLevelClient getClient(String hostname, Integer port, String scheme) {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
    }

    public IndexResponse index(String index,String type,String id,Map<String, Object> jsonMap) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(jsonMap);
        return this.index(indexRequest);
    }

    public IndexResponse index(IndexRequest indexRequest) throws IOException {
        return this.restHighLevelClient.index(indexRequest);
    }

    public void indexAsync(String index,String type,String id,Map<String, Object> jsonMap,ActionListener<IndexResponse> listener) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(jsonMap);
        this.indexAsync(indexRequest,listener);
    }

    public void indexAsync(IndexRequest indexRequest,ActionListener<IndexResponse> listener) throws IOException {
        this.restHighLevelClient.indexAsync(indexRequest, listener);
    }

    public BulkResponse bulk(BulkRequest bulkRequest) throws IOException {
        return this.restHighLevelClient.bulk(bulkRequest);
    }

    public void bulkAsync(BulkRequest bulkRequest,ActionListener<BulkResponse> listener) throws IOException {
        this.restHighLevelClient.bulkAsync(bulkRequest,listener);
    }

    public GetResponse get(String index,String type,String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, type, id);
        return this.get(getRequest);
    }

    public GetResponse get(GetRequest getRequest) throws IOException {
        return this.restHighLevelClient.get(getRequest);
    }

    public void getAsync(String index,String type,String id,ActionListener<GetResponse> listener) throws IOException {
        GetRequest getRequest = new GetRequest(index, type, id);
        this.getAsync(getRequest,listener);
    }

    public void getAsync(GetRequest getRequest,ActionListener<GetResponse> listener) throws IOException {
        this.restHighLevelClient.getAsync(getRequest,listener);
    }

    public DeleteResponse delete(String index,String type,String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        return this.delete(deleteRequest);
    }

    public DeleteResponse delete(DeleteRequest deleteRequest) throws IOException {
        return this.restHighLevelClient.delete(deleteRequest);
    }

    public void deleteAsync(String index,String type,String id,ActionListener<DeleteResponse> listener) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        this.deleteAsync(deleteRequest,listener);
    }

    public void deleteAsync(DeleteRequest deleteRequest,ActionListener<DeleteResponse> listener) throws IOException {
        this.restHighLevelClient.deleteAsync(deleteRequest,listener);
    }

    public UpdateResponse update(String index,String type,String id,Map<String, Object> jsonMap) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(jsonMap);
        return this.update(updateRequest);
    }

    public UpdateResponse update(UpdateRequest updateRequest) throws IOException {
        return this.restHighLevelClient.update(updateRequest);
    }

    public void updateAsync(String index,String type,String id,Map<String, Object> jsonMap,ActionListener<UpdateResponse> listener) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(jsonMap);
        this.updateAsync(updateRequest,listener);
    }

    public void updateAsync(UpdateRequest updateRequest,ActionListener<UpdateResponse> listener) throws IOException {
        this.restHighLevelClient.updateAsync(updateRequest,listener);
    }

    public SearchResponse search(SearchRequest searchRequest) throws IOException {
        return this.restHighLevelClient.search(searchRequest);
    }

    public void searchAsync(SearchRequest searchRequest,ActionListener<SearchResponse> listener) throws IOException {
        this.restHighLevelClient.searchAsync(searchRequest,listener);
    }

    public void close() throws IOException {
        this.restHighLevelClient.close();
    }
    public static void main(String[] args) throws IOException {
        ElasticsearchClient client = new ElasticsearchClient("115.159.0.122", 9200, "http");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("user", "yy");
        jsonMap.put("createTime", new Date());
        jsonMap.put("message", "test Elasticsearch");
        IndexResponse indexResponse = client.index("jxzc_test", "user", "1", jsonMap);
        Map<String, Object> jsonMap2 = new HashMap<String, Object>();
        jsonMap2.put("user", "yy1");
        jsonMap2.put("createTime", new Date());
        jsonMap2.put("message", "test yy");
        IndexResponse indexResponse2 = client.index("jxzc_test2", "user", "1", jsonMap2);
        GetResponse documentFields = client.get("jxzc_test", "user", "1");
        GetResponse documentFields2 = client.get("jxzc_test2", "user", "1");
        System.out.println(indexResponse);
        System.out.println(indexResponse2);
        System.out.println(documentFields);
        System.out.println(documentFields2);
        System.out.println("------------------------------------");
        SearchRequest searchRequest = new SearchRequest();
        //searchRequest.types("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();//复合查询
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery("yy","message").fuzziness(Fuzziness.AUTO));
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        System.out.println(searchResponse);
        client.close();
    }
}
