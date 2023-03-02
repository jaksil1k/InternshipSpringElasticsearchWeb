package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.dto.JaxbList;
import com.task4.spring_elasticsearch_web.dto.TextDto;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.mapper.TextMapper;
import com.task4.spring_elasticsearch_web.service.TextService;
import com.task4.spring_elasticsearch_web.service.util.XmlMarshalUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DocumentApiController.class)
public class DocumentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TextService textService;

    @Test
    public void showAllTexts() throws Exception {
        Text text = new Text();
        text.setId("1");
        text.setText("some text");
        Date date = new Date(104, Calendar.JULY, 14);
        text.setDate(date);

        List<Text> textDtoList = List.of(text);

        given(textService.search(any())).willReturn(textDtoList);

        String expectedJson = """
                [
                    {
                        "id": "1",
                        "body": "some text",
                        "date": "2004-07-13T17:00:00.000+00:00"
                    }
                ]""";

        this.mockMvc
                .perform(get("http://localhost:8081/api/v1/documents").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    void getTextById() throws Exception {
        Text text = new Text();
        text.setId("1");
        text.setText("some text");
        Date date = new Date(104, Calendar.JULY, 14);
        text.setDate(date);

        given(textService.getById("1")).willReturn(text);

        String expectedJson = """
                {
                    "id": "1",
                    "body": "some text",
                    "date": "2004-07-13T17:00:00.000+00:00"
                }
                """;

        this.mockMvc
                .perform(get("http://localhost:8081/api/v1/documents/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void deleteTextById() throws Exception {

        given(textService.deleteById("1")).willReturn(true);

        String expectedJson = """
                {
                    "id": "1",
                    "body": "some text",
                    "date": "2004-07-13T17:00:00.000+00:00"
                }
                """;

        this.mockMvc
                .perform(delete("http://localhost:8081/api/v1/documents/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void searchWithXml() throws Exception {
        Text text = new Text();
        text.setId("1");
        text.setText("some text");
        text.setDate(new Date(104, Calendar.JULY, 14));
        Text text2 = new Text();
        text2.setId("2");
        text2.setText("some novel");
        text.setDate(new Date(104, Calendar.JULY, 14));

        List<Text> texts = List.of(text, text2);

        List<TextDto> textDtoList = TextMapper.MAPPER.toDto(texts);

        given(textService.searchWithXml(any())).willReturn(XmlMarshalUtil.marshal(new JaxbList(textDtoList)));

        String requestXml = """
                <search>
                    <fields>text</fields>
                    <searchTerm>some</searchTerm>
                </search>
                """;

        String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><texts><text><body>some text</body><date>2004-07-14T00:00:00+07:00</date><id>1</id></text><text><body>some novel</body><id>2</id></text></texts>";

        this.mockMvc.perform(post("http://localhost:8081/api/v1/documents/search")
                        .contentType(MediaType.APPLICATION_XML_VALUE).content(requestXml))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(responseXml));
    }
}
