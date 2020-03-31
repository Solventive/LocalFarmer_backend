package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

public class FileMetaData {

    private String id;

    private String fileType;

    private String fileName;

    public FileMetaData(String id, String fileType, String fileName) {
        this.id = id;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
