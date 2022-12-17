package com.task4.spring_elasticsearch_web.controller;

import com.task4.spring_elasticsearch_web.entity.Text;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.service.TextService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

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

//    @PostMapping("/create")
//    public String saveText(@ModelAttribute("text") Text text){
//        textService.addToElasticsearch(text);
//        return "redirect:/all";
//    }

    @GetMapping("/find")
    public String findText(Model model) {
        Text text = new Text();
        model.addAttribute("text", text);
        return "find-text";
    }

    @RequestMapping("/all")
    public String showAll(Model model
            , @RequestParam(value = "sortField", defaultValue = "id") String sortBy
            , @RequestParam(value = "order", defaultValue = "ASC") SortOrder order
            , @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "size", defaultValue = "3") int size) {
        if (page < 0){
            page = 0;
        }
        List<Text> texts = textService.search(new SearchRequestDTO(sortBy, order, page, size));
        Page<Text> textPage = new PageImpl<>(texts, PageRequest.of(page, size), texts.size());
        model.addAttribute("texts", textPage);
        return "all";
    }

//    @PostMapping("/find")
//    public String searchText(@ModelAttribute("text") Text text) {
//        Text text1 = textService.searchInElasticsearch(text.getText());
//
//        return "redirect:/api/v1/documents/" + text1.getId();
//    }
}
