package com.task4.spring_elasticsearch_web.entity;


import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
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
