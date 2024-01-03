package utility;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Properties {
    private static Map<String, Object> properties;

    static {
        loadYaml();
    }

    private static void loadYaml() {
        Yaml yaml = new Yaml();
        try (InputStream in = Properties.class.getClassLoader().getResourceAsStream("application.yml")) {
            properties = yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load application config properties", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String key, Class<T> clazz) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = properties;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
            if (current == null) {
                return null;
            }
        }
        return clazz.cast(current.get(keys[keys.length - 1]));
    }

}
