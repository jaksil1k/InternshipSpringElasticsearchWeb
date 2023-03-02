package com.task4.spring_elasticsearch_web.dto;

import com.task4.spring_elasticsearch_web.dto.TextDto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name="texts")
public class JaxbList{
    protected List<TextDto> list;

    public JaxbList(){}

    public JaxbList(List<TextDto> list){
        this.list=list;
    }

    @XmlElement(name="text")
    public List<TextDto> getList(){
        return list;
    }
}
