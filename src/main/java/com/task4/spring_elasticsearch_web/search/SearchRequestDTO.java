package com.task4.spring_elasticsearch_web.search;

import jakarta.xml.bind.annotation.*;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@XmlRootElement(name = "search")
//@XmlType(propOrder = {"fields", "searchTerm", "sortBy", "order"})
public class SearchRequestDTO{


    private static final int DEFAULT_SIZE = 100;



    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;

    private String[] fields2;

    private int page;
    private int size;
    public SearchRequestDTO() {
    }

    public SearchRequestDTO(List<String> fields, String searchTerm, String sortBy, SortOrder order, int page, int size) {
        this.fields = fields;
        this.searchTerm = searchTerm;
        this.sortBy = sortBy;
        this.order = order;
        this.page = page;
        this.size = size;
    }

    public SearchRequestDTO(String sortBy, SortOrder order, int page, int size) {
        this.sortBy = sortBy;
        this.order = order;
        this.setPage(page);
        this.setSize(size);
    }
    @XmlTransient
    public List<String> getFields() {
        return fields;
    }

    public String[] getFields2() {
        return fields2;
    }

    @XmlElement(name = "fields")
    public void setFields2(String[] fields2) {
        this.fields2 = fields2;
    }
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

//    @XmlElement(name = "fields")
//    public void setFields(String[] fields) {
//        this.fields = List.of(fields);
//    }

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

    @XmlTransient
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @XmlTransient
    public int getSize() {
        return size != 0 ? size : DEFAULT_SIZE;
    }

    public void setSize(int size) {
        this.size = size;
    }
    @Override
    public String toString() {
        return "SearchRequestDTO{" +
                "page=" + page +
                ", size=" + size +
                ", fields=" + fields +
                ", searchTerm='" + searchTerm + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", order=" + order +
                '}';
    }
}
