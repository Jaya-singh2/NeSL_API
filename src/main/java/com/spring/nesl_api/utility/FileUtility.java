package com.spring.nesl_api.utility;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class FileUtility {

    public String convertFileToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        byte[] base64Bytes = Base64.getEncoder().encode(fileBytes);
        return new String(base64Bytes);
    }

    public String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "C:\\Users\\User\\nesl_data";
        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Construct the file path
        String filePath = uploadDir + File.separator + fileName;

        // Save the file to disk
        file.transferTo(new File(filePath));

        return filePath;
    }

}
