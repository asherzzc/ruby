package com.road.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhouc
 * @date 2021/11/10 21:26
 * @description
 * @since 1.0
 */
public class CustomerAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper codec = (ObjectMapper) jsonParser.getCodec();
        JsonNode treeNode = codec.readTree(jsonParser);
        // 获得Json数据
        Iterator<JsonNode> elements = treeNode.elements();
        // 最终的List集合
        List<GrantedAuthority> list = new LinkedList<>();
        while (elements.hasNext()) {
            JsonNode authority = elements.next().get("authority");
            list.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return list;
    }
}
