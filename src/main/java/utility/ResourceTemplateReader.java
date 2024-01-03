package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ResourceTemplateReader {
    public static String getScreen(Class<?> tClass) {
        final ClassLoader classLoader = ResourceTemplateReader.class.getClassLoader();
        final String path = "screens/" + tClass.getSimpleName() + ".html";

        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
                    return scanner.useDelimiter("\\A").next();
                }
            } else {
                throw new IOException("Screen not found by path: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
