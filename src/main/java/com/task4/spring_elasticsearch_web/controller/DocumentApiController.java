package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.dto.TextDto;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.mapper.TextMapper;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.service.TextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "Texts", description = "Methods to work with texts")
public class DocumentApiController {

    @Autowired
    private TextService textService;

    @GetMapping
    @Operation(summary = "get all texts")
    public List<TextDto> getAllDocuments(
            @Parameter(description = "can send with orderBy, sortField, page and size parameters")
            @RequestBody SearchRequestDTO dto) {
        List<Text> texts = textService.search(dto);
        return TextMapper.MAPPER.toDto(texts);
    }

    @PostMapping
    @Operation(summary = "add text")
    public TextDto addNewText(
            @Parameter(description = "can send id, text, date. But shouldn't send date, because in any case will be current date in system")
            @RequestBody Text text) throws IOException {
        text.setDate(new Date());
        Optional<Text> optionalText = textService.index(text);
        if (optionalText.isEmpty()) {
            return new TextDto("something went wrong, please try again later");
        }
        return TextMapper.MAPPER.toDto(optionalText.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete text by id")
    public boolean deleteTextById(@PathVariable String id){
        return textService.deleteTextById(id);

    }

    @GetMapping("/{id}")
    @Operation(summary = "find text by id")
    public TextDto getTextById(@PathVariable String id) {
        Text text = textService.getById(id);
        return TextMapper.MAPPER.toDto(text);
    }

    @PostMapping(value = "/search", consumes = {MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE})
    public String search(@RequestBody String xmlSearchRequest /*final SearchRequestDTO dto*/) throws JAXBException, SAXException, FileNotFoundException {

        return textService.searchWithXml(xmlSearchRequest);
    }
}
