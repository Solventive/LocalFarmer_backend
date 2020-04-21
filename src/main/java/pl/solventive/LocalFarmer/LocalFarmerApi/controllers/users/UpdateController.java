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
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.FileMetaData;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.UpdateApk;
import pl.solventive.LocalFarmer.LocalFarmerApi.service.UpdateStorageService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/download/apk/localfarmer")
public class UpdateController {

    private static final Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private UpdateStorageService updateStorageService;

    @PostMapping(path = "/upload", produces={"application/json; charset=UTF-8"})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        updateStorageService.deleteAllFiles();
        UpdateApk uFile = updateStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(uFile.getId())
                .toUriString();

        return  ResponseEntity.created(URI.create(fileDownloadUri))
                .body(new FileMetaData(uFile.getId(), uFile.getFileType(), uFile.getFileName()));
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllFiles() {
        updateStorageService.deleteAllFiles();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    public ResponseEntity<Resource> downloadFile() {
        // Load file from database
        List<UpdateApk> files = updateStorageService.getAllFiles();
        UpdateApk file = files.get(0);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

}