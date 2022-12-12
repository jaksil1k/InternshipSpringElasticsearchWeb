package com.task3.spring_elasticsearch_web.repository;


import com.task3.spring_elasticsearch_web.entity.Text;
import org.springframework.data.repository.CrudRepository;



public interface TextRepository extends CrudRepository<Text, String> {
    public Text findTextByText(String text);

}
