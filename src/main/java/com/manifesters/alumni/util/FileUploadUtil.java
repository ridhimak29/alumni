package com.manifesters.alumni.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static String getUploadDirectory(){
        String systemOS = System.getProperty("os.name");
        String filePath = System.getProperty("user.dir")+"/src/main/upload/static/images";
        if (systemOS.contains("Mac")) {
            return filePath;
        }
        return "/src/main/upload/static/images";    }

    public static void saveFile( String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(getUploadDirectory());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
