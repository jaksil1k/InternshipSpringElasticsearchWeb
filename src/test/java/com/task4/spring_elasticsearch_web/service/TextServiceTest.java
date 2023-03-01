package com.task4.spring_elasticsearch_web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.helper.Indices;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith({
        MockitoExtension.class
})
public class TextServiceTest {

    @InjectMocks
    private TextService textService;

    private IndexRequest indexRequest;

    @Mock
    private RestHighLevelClient client;

    @BeforeAll
    static void init() {
//        System.out.println("Before all: " + this);
    }

    @BeforeEach
    void prepare() throws IOException {
//        System.out.println("Before each" + this);
//        indexRequest = new IndexRequest()
//                .index(Indices.TEXT_INDEX)
//                .id("1")
//                .source(new ObjectMapper()
//                        .writeValueAsString(new Text("some text", new Date())));
//
//
//
//        doReturn(new IndexResponse()).when(client).index(any()
//                , RequestOptions.DEFAULT);
//        doReturn(null).when(client).index(null, RequestOptions.DEFAULT);

        IndexResponse indexResponse = mock(IndexResponse.class);
        when(indexResponse.status()).thenReturn(RestStatus.OK);
        doReturn(indexResponse).when(client).index(any(), RequestOptions.DEFAULT);
//        doReturn(new IndexRequest())
    }

    @Test
    void throwNullPointerExceptionIfTextIsNull() {
//        Assertions.assertTrue(true);
        Assertions.assertThrows(NullPointerException.class, ()->textService.index(null));
    }

    @Test
    void AddText() {
        Text text = new Text("abcd", new Date());
        assertThat(textService.index(text)).isTrue();
    }

}
