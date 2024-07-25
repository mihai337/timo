package com.example.tutorial.services;

import com.example.tutorial.exceptions.FileAlreadyExistsException;
import com.example.tutorial.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final MovieRepository movieRepository;

    @Override
    @SneakyThrows
    public String uploadFile(String path, MultipartFile file) {
        String fileName = file.getOriginalFilename();

        var movies = movieRepository.findAll();
        for (var movie : movies) {
            if (movie.getPoster().equals(fileName)) {
                throw new FileAlreadyExistsException("File already exists");
            }
        }

        String filePath = path + File.separator + fileName;

        File f = new File(filePath);
        File folder = new File(path);

        if(!folder.exists()){
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
        String filePath = path + File.separator + name;
        return new FileInputStream(filePath);
    }
}
