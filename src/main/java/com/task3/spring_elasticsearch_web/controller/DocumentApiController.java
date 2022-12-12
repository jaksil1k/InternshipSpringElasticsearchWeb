package com.task3.spring_elasticsearch_web.controller;

import com.task3.spring_elasticsearch_web.entity.Text;
import com.task3.spring_elasticsearch_web.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Text result = textService.addToElasticsearch(text);
        return result;
    }

    @DeleteMapping("/{id}")
    public String deleteTextById(@RequestParam String id){
        textService.deleteFromElasticsearchById(id);
        return "Text with ID=" + id +" was successfully deleted";
    }
}
