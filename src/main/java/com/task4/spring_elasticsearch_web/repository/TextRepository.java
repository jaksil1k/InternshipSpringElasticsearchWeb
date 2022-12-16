package com.task4.spring_elasticsearch_web.repository;


import com.task4.spring_elasticsearch_web.entity.Text;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


public interface TextRepository extends CrudRepository<Text, String> {
    public Text findTextByText(String text);

}
