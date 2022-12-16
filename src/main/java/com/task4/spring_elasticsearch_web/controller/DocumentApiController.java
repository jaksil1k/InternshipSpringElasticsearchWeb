package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentApiController {

    @Autowired
    private TextService textService;

    @GetMapping("/")
    public Iterable<Text> getAllDocuments() {
        Iterable<Text> texts = textService.getAllTexts();
        return texts;
    }

    @PostMapping("/")
    public Text addNewText(@RequestBody Text text) {
        text.setDate(new Date());
        Text result = textService.addToElasticsearch(text);
        return result;
    }

    @DeleteMapping("/{id}")
    public String deleteTextById(@PathVariable String id){
        textService.deleteFromElasticsearchById(id);
        return "Text with ID=" + id +" was successfully deleted";
    }

    @GetMapping("/{id}")
    public Optional<Text> getTextById(@PathVariable String id) {
        return textService.getTextById(id);
    }
}
