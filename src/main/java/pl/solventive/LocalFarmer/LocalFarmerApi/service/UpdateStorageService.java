package pl.solventive.LocalFarmer.LocalFarmerApi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.UpdateApk;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UpdateRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class UpdateStorageService {

    @Autowired
    private UpdateRepository updateRepo;

    public UpdateApk storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            UpdateApk dbFile = new UpdateApk(fileName, file.getContentType(), file.getBytes());

            return updateRepo.save(dbFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public UpdateApk getFile(String fileId) {
        return updateRepo.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + fileId));
    }

    public List<UpdateApk> getAllFiles() {
        return updateRepo.findAll();
    }


    public void deleteAllFiles() {
        updateRepo.deleteAll();
    }
}