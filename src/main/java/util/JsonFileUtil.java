package util;

import com.fasterxml.jackson.databind.SerializationFeature;import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonFileUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static final String DATA_DIR = "data";

    public static <T> List<T> readList(String fileName, Class<T> clazz) {
        try {
            File file = getDataFile(fileName);

            if (!file.exists()) {
                createEmptyJsonFile(file);
                return new ArrayList<>();
            }

            if (file.length() == 0) {
                return new ArrayList<>();
            }

            CollectionType listType = objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, clazz);

            return objectMapper.readValue(file, listType);

        } catch (Exception e) {
            throw new RuntimeException("JSON 파일 읽기 실패: " + fileName, e);
        }
    }

    public static <T> void writeList(String fileName, List<T> dataList) {
        try {
            File file = getDataFile(fileName);

            if (!file.exists()) {
                createEmptyJsonFile(file);
            }

            objectMapper.writeValue(file, dataList);

        } catch (Exception e) {
            throw new RuntimeException("JSON 파일 쓰기 실패: " + fileName, e);
        }
    }

    private static File getDataFile(String fileName) {
        File dataDir = new File(DATA_DIR);

        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        return new File(dataDir, fileName);
    }

    private static void createEmptyJsonFile(File file) throws Exception {
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        objectMapper.writeValue(file, new ArrayList<>());
    }
}
