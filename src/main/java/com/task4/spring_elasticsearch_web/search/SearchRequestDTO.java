package com.task4.spring_elasticsearch_web.search;

import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

public class SearchRequestDTO extends PageRequestDTO{
    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;

    public SearchRequestDTO(String sortBy, SortOrder order, int page, int size) {
        this.sortBy = sortBy;
        this.order = order;
        super.setPage(page);
        super.setSize(size);
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }
}
