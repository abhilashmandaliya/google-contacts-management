package app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String toJson(Object o) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(o);
  }
}
