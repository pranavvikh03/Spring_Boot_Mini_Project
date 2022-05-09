package com.paytmmoney.sipmanagement.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytmmoney.sipmanagement.dao.SchemeRepo;
import com.paytmmoney.sipmanagement.elasticsearch.document.SIP;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.Scheme;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SipService {

    @Autowired
    private SipRepo sipRepo;

    @Autowired
    private SchemeRepo schemeRepo;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper mapper = new ObjectMapper();

    public void addSip(SIP sip){
        sipRepo.save(sip);
    }

    @PostConstruct
    public void saveSipData(){
        List<Scheme> schemes = new ArrayList<>(schemeRepo.findAll());
        for(Scheme s : schemes){
            sipRepo.save(SIP.builder().schemeId(s.getSchemeId()).schemeName(s.getSchemeName()).build());
        }
    }

    public SIP findBySipId(String id){
        return sipRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No SIP available with given id")
        );
    }

    public List<SIP> searchMatching(SearchRequestDto searchRequestDto){
        SearchRequest searchRequest = SearchUtil.buildSearchRequest("sip", searchRequestDto);
        if(searchRequest == null)
            return Collections.emptyList();

        try{
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<SIP> sipList = new ArrayList<>(searchHits.length);
            for(SearchHit searchHit : searchHits){
                sipList.add(
                        mapper.readValue(searchHit.getSourceAsString(), SIP.class)
                );
            }
            return sipList;
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
