package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(String firstPath, String secondPath) throws IOException {
        String fileText1 = Files.readString(Path.of(firstPath));
        String fileText2 = Files.readString(Path.of(secondPath));
        Map<String, Object> map1 = parse(fileText1);
        Map<String, Object> map2 = parse(fileText2);
        Set<String> allKeys = getAllKeys(map1, map2);
        StringBuilder result = new StringBuilder("{\n");
        for (String key : allKeys) {
            if (map1.containsKey(key) && !map2.containsKey(key)) {
                result.append("  - " + key + ": " + map1.get(key) + "\n");
            } else if (!map1.containsKey(key) && map2.containsKey(key)) {
                result.append("  + " + key + ": " + map2.get(key) + "\n");
            } else if (map1.containsKey(key) && map2.containsKey(key)) {
                if (map1.get(key).equals(map2.get(key))) {
                    result.append("    " + key + ": " + map1.get(key) + "\n");
                } else {
                    result.append("  - " + key + ": " + map1.get(key) + "\n");
                    result.append("  + " + key + ": " + map2.get(key) + "\n");
                }
            }
        }
        result.append("}");
        return result.toString();
    }


    public static Map<String, Object> parse(String string) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map
                = objectMapper.readValue(string, new TypeReference<Map<String, Object>>() { });
        return map;
    }

    public static Set<String> getAllKeys(Map<String, Object> map1, Map<String, Object> map2) {
        TreeSet<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());
        return allKeys;

    }
}
