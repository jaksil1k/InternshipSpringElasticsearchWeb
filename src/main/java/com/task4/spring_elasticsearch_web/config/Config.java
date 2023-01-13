package com.task4.spring_elasticsearch_web.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableElasticsearchRepositories(basePackages
        = "com.task4.spring_elasticsearch_web.repository")
@ComponentScan(basePackages = {"com.task4.spring_elasticsearch_web"})
public class Config extends
        AbstractElasticsearchConfiguration{
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration =
                ClientConfiguration
                        .builder()
                        .connectedTo("localhost:9200")
                        .build();

        return RestClients.create(clientConfiguration).rest();
    }

    //this mehtod not worked
//    @Bean
//    public OpenAPI openAPI() {
//        Contact contact = new Contact()
//                .email("nartai53@gmail.com")
//                .name("Zhaksylyk");
//        Info info = new Info()
//                .title("DocumentApi")
//                .version("v1.0")
//                .contact(contact);
//
//        return new OpenAPI()
//                .info(info);
//    }
}
