package swp490.g7.OnlineLearningSystem.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.amazon.service.AmazonClient;

import java.util.List;

@RestController
@RequestMapping("/api/storage/")
public class S3Controller {

    @Autowired
    private AmazonClient amazonClient;

    @PostMapping("/single-file-upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return amazonClient.uploadFile(file);
    }

    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        return amazonClient.downloadFile(filename);
    }

    @PostMapping("multi-file-upload")
    public void uploadMultipleFile(@RequestPart(value = "files") List<MultipartFile> files) {
        amazonClient.multipleFileUpload(files);
    }
}
