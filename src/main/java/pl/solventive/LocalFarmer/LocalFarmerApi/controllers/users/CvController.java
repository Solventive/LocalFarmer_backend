package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Cv;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.FileMetaData;
import pl.solventive.LocalFarmer.LocalFarmerApi.service.CvService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/download/pdf/cv")
public class CvController {

    private static final Logger logger = LoggerFactory.getLogger(CvController.class);

    @Autowired
    private CvService cvService;

    @PostMapping(path = "/upload", produces={"application/json; charset=UTF-8"})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        cvService.deleteAllFiles();
        Cv uFile = cvService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(uFile.getId())
                .toUriString();

        return  ResponseEntity.created(URI.create(fileDownloadUri))
                .body(new FileMetaData(uFile.getId(), uFile.getFileType(), uFile.getFileName()));
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllFiles() {
        cvService.deleteAllFiles();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    public ResponseEntity<Resource> downloadFile() {
        // Load file from database
        List<Cv> files = cvService.getAllFiles();
        Cv file = files.get(0);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

}