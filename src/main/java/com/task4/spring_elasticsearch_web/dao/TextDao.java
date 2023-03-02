package com.task4.spring_elasticsearch_web.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.helper.Indices;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.search.util.SearchUtil;
import com.task4.spring_elasticsearch_web.service.TextService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TextDao {

    private final RestHighLevelClient client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

    @Autowired
    public TextDao(RestHighLevelClient client) {
        this.client = client;
    }

    public Optional<Text> getById(final String textId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.TEXT_INDEX, textId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()){
                return Optional.empty();
            }
            return Optional.of(MAPPER.readValue(documentFields.getSourceAsString(), Text.class));
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

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
}
