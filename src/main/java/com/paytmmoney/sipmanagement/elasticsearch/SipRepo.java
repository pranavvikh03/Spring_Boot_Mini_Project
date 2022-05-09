package com.paytmmoney.sipmanagement.elasticsearch;

import com.paytmmoney.sipmanagement.elasticsearch.document.SIP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SipRepo extends ElasticsearchRepository<SIP,String> {
}
