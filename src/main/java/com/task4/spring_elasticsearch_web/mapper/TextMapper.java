package com.task4.spring_elasticsearch_web.mapper;

import com.task4.spring_elasticsearch_web.dto.TextDto;
import com.task4.spring_elasticsearch_web.entity.Text;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TextMapper {

    TextMapper MAPPER = Mappers.getMapper( TextMapper.class );
    @Mapping(target = "body", source = "text")
    TextDto toDto(Text text);

    List<TextDto> toDto(List<Text> texts);
}
