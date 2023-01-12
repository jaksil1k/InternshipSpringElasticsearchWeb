package com.task4.spring_elasticsearch_web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.mapstruct.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;
public class Text {
    private String id;

    private String text;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Text(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Text() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
