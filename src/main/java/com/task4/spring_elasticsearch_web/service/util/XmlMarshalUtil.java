package com.task4.spring_elasticsearch_web.service.util;


import com.task4.spring_elasticsearch_web.dto.JaxbList;
import com.task4.spring_elasticsearch_web.search.SearchRequestDTO;
import com.task4.spring_elasticsearch_web.validator.SearchEventHandler;
import jakarta.xml.bind.*;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlMarshalUtil {

    public static String marshal(JaxbList texts) throws JAXBException {
//        System.out.println(texts.size());
        StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(JaxbList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(texts, stringWriter);
        return stringWriter.toString();
    }

    public static SearchRequestDTO unmarshall(String xmlString) throws JAXBException, SAXException, FileNotFoundException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //File file = new File(Objects.requireNonNull(XmlMarshalUtil.class.getResource("validators/searchValidator.xsd")).getFile());
        File file = ResourceUtils.getFile("classpath:validators/searchValidator.xsd");
        Schema schema = sf.newSchema(file);

        StringReader stringReader = new StringReader(xmlString);
        JAXBContext jaxbContext = JAXBContext.newInstance(SearchRequestDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SearchEventHandler());
        SearchRequestDTO searchRequestDTO = (SearchRequestDTO) unmarshaller.unmarshal(stringReader);
        //System.out.println(searchRequestDTO.toString());
        return searchRequestDTO;
    }
}
