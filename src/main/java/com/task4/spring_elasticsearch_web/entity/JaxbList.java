package com.task4.spring_elasticsearch_web.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="List")
public class JaxbList{
    protected List<Text> list;

    public JaxbList(){}

    public JaxbList(List<Text> list){
        this.list=list;
    }

    @XmlElement(name="Text")
    public List<Text> getList(){
        return list;
    }
}
