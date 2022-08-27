package jp.cron.sample.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static void saveFileFromStream(InputStream stream, File file) throws IOException {
        Files.copy(
                stream,
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        stream.close();
    }
}