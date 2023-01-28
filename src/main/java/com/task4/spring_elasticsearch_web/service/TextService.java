package com.task4.spring_elasticsearch_web.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.task4.spring_elasticsearch_web.entity.JaxbList;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.helper.Indices;
import com.task4.spring_elasticsearch_web.repository.TextRepository;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.search.util.SearchUtil;
import com.task4.spring_elasticsearch_web.service.util.XmlMarshalUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.coyote.Response;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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

import javax.xml.validation.Schema;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class TextService {

    //Autowired
//    private final TextRepository repository;
    private final RestHighLevelClient client;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

    @Autowired
    public TextService(RestHighLevelClient client) {
        this.client = client;
    }

    public Boolean index(final Text text) {
        try {
            final String textAsString = MAPPER.writeValueAsString(text);

            final IndexRequest request = new IndexRequest(Indices.TEXT_INDEX);
            request.id(text.getId());
            request.source(textAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Text getById(final String textId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.TEXT_INDEX, textId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()){
                return null;
            }
            return MAPPER.readValue(documentFields.getSourceAsString(), Text.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
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

        try {
            DeleteRequest request = new DeleteRequest(
                    Indices.TEXT_INDEX,
                    id
            );

            DeleteResponse response = client.delete(
                    request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

//    public Text getTextById(String id) {
//        return repository.findById(id).orElse(null);
//    }

    public List<Text> search(final SearchRequestDTO dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.TEXT_INDEX,
                dto
        );

        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Text> texts = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                texts.add(
                        MAPPER.readValue(hit.getSourceAsString(), Text.class)
                );
            }
            return texts;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public String searchWithXml(String xmlSearchRequest) throws FileNotFoundException, JAXBException {


        SearchRequestDTO requestDTO = null;
        try {
            requestDTO = XmlMarshalUtil.unmarshall(xmlSearchRequest);
        } catch (JAXBException | SAXException e) {
            return "<error>" + "bad request" + "</error>";
        }
        requestDTO.setFields(List.of(requestDTO.getFields2()));
//        System.out.println(requestDTO.getFields());
//        System.out.println(requestDTO.getSearchTerm());
        List<Text> texts = search(requestDTO);
//        System.out.println(texts.size());
        return XmlMarshalUtil.marshal(new JaxbList(texts));
    }
}
