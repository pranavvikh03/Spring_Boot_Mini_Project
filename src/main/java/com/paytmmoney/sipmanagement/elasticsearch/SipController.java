package com.paytmmoney.sipmanagement.elasticsearch;

import com.paytmmoney.sipmanagement.elasticsearch.document.SIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/es-sip")
public class SipController {

    @Autowired
    private SipService sipService;

    @PostMapping
    public void createService(@RequestBody SIP sip){
        sipService.addSip(sip);
    }

    @GetMapping("/{id}")
    public SIP findSip(@PathVariable String id){
        return sipService.findBySipId(id);
    }

    @PostMapping("/searchMatchingSips")
    public List<SIP> searchSIP(@RequestBody SearchRequestDto searchRequestDto){
        return sipService.searchMatching(searchRequestDto);
    }
}
