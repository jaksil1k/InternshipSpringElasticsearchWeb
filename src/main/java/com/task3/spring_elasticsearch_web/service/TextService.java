package com.task3.spring_elasticsearch_web.service;


import com.task3.spring_elasticsearch_web.entity.Text;
import com.task3.spring_elasticsearch_web.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextService {

    private final TextRepository repository;

    @Autowired
    public TextService(TextRepository repository) {
        this.repository = repository;
    }

    public void addToElasticsearch(String text) {

        Text text1 = new Text(text);
        repository.save(text1);
    }

    public Text searchInElasticsearch(String s) {
        Text text = repository.findTextByText(s);
        return text;
    }

}