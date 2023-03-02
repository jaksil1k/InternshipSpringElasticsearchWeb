package com.task4.spring_elasticsearch_web.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.task4.spring_elasticsearch_web.dao.TextDao;
import com.task4.spring_elasticsearch_web.entity.JaxbList;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.helper.Indices;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.search.util.SearchUtil;
import com.task4.spring_elasticsearch_web.service.util.XmlMarshalUtil;
import jakarta.xml.bind.JAXBException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class TextService {

    //Autowired
//    private final TextRepository repository;
    private final RestHighLevelClient client;
    private final TextDao textDao;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

    @Autowired
    public TextService(RestHighLevelClient client, TextDao textDao) {
        this.client = client;
        this.textDao = textDao;
    }

    public Optional<Text> index(final Text text) throws IOException {
//        try {
//            final String textAsString = MAPPER.writeValueAsString(text);
//
//            final IndexRequest request = new IndexRequest(Indices.TEXT_INDEX);
//            request.id(text.getId());
//            request.source(textAsString, XContentType.JSON);
//
//            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//
//            return response != null && response.status().equals(RestStatus.OK);
//
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            return false;
//        }
        final String textAsString = MAPPER.writeValueAsString(text);

        final IndexRequest request = new IndexRequest(Indices.TEXT_INDEX);
        request.id(text.getId());
        request.source(textAsString, XContentType.JSON);

        final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        if (response != null && response.status().equals(RestStatus.OK)) {
            text.setId(response.getId());
            return Optional.of(text);
        }
        return Optional.empty();
    }

    public Text getById(final String textId) {
        Optional<Text> optional = textDao.getById(textId);
        if (optional.isEmpty()) {
            return new Text("no such text exists");
        }
        return optional.get();
    }

    //Repository is not working
//    public void addToElasticsearchByFieldText(String text) {
//        Text text1 = new Text(text, new Date());
//        repository.save(text1);
//    }
//
//    public Text searchInElasticsearch(String s) {
//        Text text = repository.findTextByText(s);
//        return text;
//    }
//
//    public Iterable<Text> getAllTexts() {
//        Iterable<Text> texts = repository.findAll();
//        return texts;
//    }
//
//    public Text addToElasticsearch(Text text) {
//        repository.save(text);
//        return text;
//    }

public boolean deleteTextById(String id){
        return textDao.deleteTextById(id);
}

//    public Text getTextById(String id) {
//        return repository.findById(id).orElse(null);
//    }

    public List<Text> search(final SearchRequestDTO dto) {
        return textDao.search(dto);
    }

    public String searchWithXml(String xmlSearchRequest) throws FileNotFoundException, JAXBException {


        SearchRequestDTO requestDTO = null;
        try {
            requestDTO = XmlMarshalUtil.unmarshall(xmlSearchRequest);
        } catch (JAXBException | SAXException e) {
            return "<error>" + "bad request" + "</error>";
        }
        requestDTO.setFields(List.of(requestDTO.getFields2()));
        for (String field :
                requestDTO.getFields()) {
            if (!field.equals("text") && !field.equals("id") && !field.equals("date")) {

                return "<error>no such searchTerm</error>";
            }
        }
//        System.out.println(requestDTO.getFields());
//        System.out.println(requestDTO.getSearchTerm());
        List<Text> texts = textDao.search(requestDTO);
//        System.out.println(texts.size());
        return XmlMarshalUtil.marshal(new JaxbList(texts));
    }
}
