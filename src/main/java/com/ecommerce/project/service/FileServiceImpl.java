package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name of current / original file
        String originalFileName = file.getOriginalFilename();
        //Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        //mat.jpg + 1234 -> 1234.jpg
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;
        //Check if path exist and create
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        //upload to server
        //Imagine mat.jpg is 500 KB.
        //
        //file.getInputStream() opens a stream to read 500 KB.
        //
        //Files.copy(...) reads those bytes and writes them to a new file like abc123.jpg.
        //
        //Result: A new 500 KB file named abc123.jpg appears on your server.
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //returning file name
        return fileName;
    }

}
