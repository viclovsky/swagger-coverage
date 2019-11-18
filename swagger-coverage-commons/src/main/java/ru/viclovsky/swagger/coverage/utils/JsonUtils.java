package ru.viclovsky.swagger.coverage.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

    public static String dumpToJson(Object o) {
        //dump to json
        ObjectWriter ow = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer().withDefaultPrettyPrinter();
        String json = null;

        try {
            json = ow.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not process json", e);
        }
        return json;
    }
}
