package pl.solventive.LocalFarmer.LocalFarmerApi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Cv;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.CvRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class CvService {

    @Autowired
    private CvRepository cvRepo;

    public Cv storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Cv dbFile = new Cv(fileName, file.getContentType(), file.getBytes());

            return cvRepo.save(dbFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Cv getFile(String fileId) {
        return cvRepo.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + fileId));
    }

    public List<Cv> getAllFiles() {
        return cvRepo.findAll();
    }


    public void deleteAllFiles() {
        cvRepo.deleteAll();
    }
}