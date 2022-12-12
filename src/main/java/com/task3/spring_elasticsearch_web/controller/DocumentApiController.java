package com.task3.spring_elasticsearch_web.controller;

import com.task3.spring_elasticsearch_web.entity.Text;
import com.task3.spring_elasticsearch_web.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentApiController {

    @Autowired
    private TextService textService;
}
