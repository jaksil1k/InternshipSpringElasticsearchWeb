package com.task4.spring_elasticsearch_web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;


import java.util.Date;

@Schema(description = "Information about text")
@XmlRootElement
public class TextDto {

    @Schema(description = "Id of text")
    private String id;
    @Schema(description = "body of text")
    private String body;

    @Schema(description = "the date when it was created")
    private Date date;

    public TextDto(String body) {
        this.body = body;
    }

    public TextDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
