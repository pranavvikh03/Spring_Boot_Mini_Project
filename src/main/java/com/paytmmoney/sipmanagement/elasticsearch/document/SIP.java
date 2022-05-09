package com.paytmmoney.sipmanagement.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sip")
@Setting(settingPath = "static/es-settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class SIP {

    @Id
    @Field(type = FieldType.Keyword)
    private String schemeId;

    @Field(type = FieldType.Text)
    private String schemeName;
}
