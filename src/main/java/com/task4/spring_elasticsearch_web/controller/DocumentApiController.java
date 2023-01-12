package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.dto.TextDto;
import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.mapper.TextMapper;
import com.task4.spring_elasticsearch_web.mapper.TextMapperImpl;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentApiController {

    @Autowired
    private TextService textService;

    @GetMapping
    public List<TextDto> getAllDocuments(@RequestBody SearchRequestDTO dto) {
        List<Text> texts = textService.search(dto);
        return TextMapper.MAPPER.toDto(texts);
    }

    @PostMapping
    public boolean addNewText(@RequestBody Text text) {
        text.setDate(new Date());
        return textService.index(text);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTextById(@PathVariable String id){
        return textService.deleteTextById(id);

    }

    @GetMapping("/{id}")
    public TextDto getTextById(@PathVariable String id) {
        Text text = textService.getById(id);
        return TextMapper.MAPPER.toDto(text);
    }

//    @PostMapping("/search")
//    public List<Text> search(@RequestBody final SearchRequestDTO dto) {
//        return textService.search(dto);
//    }
}
