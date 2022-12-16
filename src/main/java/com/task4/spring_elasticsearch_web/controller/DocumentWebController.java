package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
public class DocumentWebController {

    @Autowired
    private TextService textService;

    @GetMapping("/create")
    public String createText(Model model) {
        Text text = new Text();
        model.addAttribute("text", text);

        return "create-text";
    }

    @PostMapping("/create")
    public String saveText(@ModelAttribute("text") Text text){
        textService.addToElasticsearch(text);
        return"redirect:/all";
    }

    @GetMapping("/find")
    public String findText(Model model) {
        Text text = new Text();
        model.addAttribute("text", text);
        return "find-text";
    }

//    @PostMapping("/find")
//    public String searchText(@ModelAttribute("text") Text text) {
//        Text text1 = textService.searchInElasticsearch(text.getText());
//
//        return "redirect:/api/v1/documents/" + text1.getId();
//    }
}
