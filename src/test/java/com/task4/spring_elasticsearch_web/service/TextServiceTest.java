package com.task4.spring_elasticsearch_web.service;

import com.task4.spring_elasticsearch_web.dao.TextDao;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import jakarta.xml.bind.JAXBException;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith({
        MockitoExtension.class
})
public class TextServiceTest {

    @InjectMocks
    private TextService textService;

    @Mock
    private RestHighLevelClient client;

    @Mock
    private TextDao textDao;

    @BeforeAll
    static void init() {
//        System.out.println("Before all: " + this);
    }

    @BeforeEach
    void prepare() {

    }

    @Test
    void throwNullPointerExceptionIfTextIsNull() {
//        Assertions.assertTrue(true);
        Assertions.assertThrows(NullPointerException.class, ()->textService.index(null));
    }

    @Test
    void successfulAddText() throws IOException {
        IndexResponse indexResponse = mock(IndexResponse.class);
        when(indexResponse.status()).thenReturn(RestStatus.OK);
        doReturn(indexResponse).when(client).index(any(), eq(RequestOptions.DEFAULT));
        Text text = new Text("abcd", new Date());
        assertThat(textService.index(text)).isPresent();
    }
    @Test
    void EmptyIfTextInTextIsNull() throws IOException {
        Text text = new Text(null, new Date());
        assertThat(textService.index(text)).isEmpty();
    }

    @Test
    void successfulGetById() {
        doReturn(Optional.of(new Text("some text"))).when(textDao).getById("some id of text");
        assertThat(textService.getById("some id of text").getText()).isNotEqualTo("no such text exists");
    }

    @Test
    void textNotExistsIfNoTextByThisId() {
        doReturn(Optional.of(new Text("no such text exists"))).when(textDao).getById("id of text that not exists");
        assertThat(textService.getById("id of text that not exists").getText()).isEqualTo("no such text exists");
    }

    @Test
    void successfulDeleteById() {
        doReturn(true).when(textDao).deleteTextById("1");
        assertThat(textService.deleteById("1")).isTrue();
    }
    @Test
    void notSuccessfulDeleteById() {
        assertThat(textService.deleteById("some id")).isFalse();
    }

    @Test
    void successfulSearch() {
        doReturn(new ArrayList<Text>()).when(textDao).search(any());
        assertThat(textService.search(new SearchRequestDTO())).isNotEmpty();
    }

    @Test
    void notSuccessfulSearch() {
        doReturn(Collections.emptyList()).when(textDao).search(any());
        assertThat(textService.search(new SearchRequestDTO())).isEmpty();
    }

    @Test
    void successfulSearchWithXml() throws JAXBException, FileNotFoundException {
        doReturn(new ArrayList<Text>()).when(textDao).search(any());
        assertThat(textService.searchWithXml("""
                <search>
                    <fields>text</fields>
                    <searchTerm>some text</searchTerm>
                </search>
                """))
                .isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><List/>");
    }
    @Test
    void noSuchSearchTermInSearchWithXml() throws JAXBException, FileNotFoundException {
        assertThat(textService.searchWithXml("""
                <search>
                    <fields>dummy</fields>
                    <searchTerm>some text</searchTerm>
                </search>
                """))
                .isEqualTo("<error>no such searchTerm</error>");
    }

    @Test
    void badRequestIfFewFields() throws JAXBException, FileNotFoundException {
        assertThat(textService.searchWithXml("""
                <search>
                    <fields>text</fields>
                    <fields>date</fields>
                    <searchTerm>some text</searchTerm>
                </search>
                """))
                .isEqualTo("<error>bad request</error>");
    }


}
