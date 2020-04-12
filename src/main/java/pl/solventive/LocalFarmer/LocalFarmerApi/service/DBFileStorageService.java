package pl.solventive.LocalFarmer.LocalFarmerApi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.DBFile;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.DBFileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + fileId));
    }

    public void deleteAllFiles() {
        dbFileRepository.deleteAll();
    }
}