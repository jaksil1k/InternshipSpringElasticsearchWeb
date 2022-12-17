package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.entity.Text;
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
    public List<Text> getAllDocuments(@RequestBody SearchRequestDTO dto) {
        return textService.search(dto);
    }

    @PostMapping
    public boolean addNewText(@RequestBody Text text) {
        text.setDate(new Date());
        boolean result = textService.index(text);
        return result;
    }

    @DeleteMapping("/{id}")
    public boolean deleteTextById(@PathVariable String id){
        return textService.deleteTextById(id);

    }

    @GetMapping("/{id}")
    public Text getTextById(@PathVariable String id) {
        return textService.getById(id);
    }

    @PostMapping("/search")
    public List<Text> search(@RequestBody final SearchRequestDTO dto) {
        return textService.search(dto);
    }
}
