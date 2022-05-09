package com.paytmmoney.sipmanagement.elasticsearch;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchUtil {

    private SearchUtil(){
        //Empty Constructor
    }

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDto searchRequestDto){
        try{
            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(searchRequestDto));
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(builder);
            return searchRequest;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static QueryBuilder getQueryBuilder(SearchRequestDto searchRequestDto){
        if(searchRequestDto == null)
            return null;
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(searchRequestDto.getField(), searchRequestDto.getSearchTerm()).operator(Operator.AND);
        return queryBuilder != null ? queryBuilder : null;
    }
}
