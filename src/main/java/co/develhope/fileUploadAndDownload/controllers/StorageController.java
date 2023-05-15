package co.develhope.fileUploadAndDownload.controllers;

import co.develhope.fileUploadAndDownload.services.StorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class StorageController {
    @Autowired
    StorageService storageService;

    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        System.out.println("Download "+fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
            case "jpg", "jpeg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        return storageService.download(fileName);
    }

    @PostMapping("/upload")
    public void upload(@RequestParam MultipartFile file) throws IOException {

        storageService.upload(file);

    }
}
