package pe.com.qallarix.movistarcontigo.noticias;

import java.io.Serializable;

public class News implements Serializable {
    private long id;
    private String title;
    private String description;
    private String imageUrl;
    private String date;
    private boolean outstanding;
    private String attachedUrl;
    private String attachedUrlName;
    private String attachedFile;
    private String attachedFileName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isOutstanding() {
        return outstanding;
    }

    public void setOutstanding(boolean outstanding) {
        this.outstanding = outstanding;
    }

    public String getAttachedUrl() {
        return attachedUrl;
    }

    public void setAttachedUrl(String attachedUrl) {
        this.attachedUrl = attachedUrl;
    }

    public String getAttachedUrlName() {
        return attachedUrlName;
    }

    public void setAttachedUrlName(String attachedUrlName) {
        this.attachedUrlName = attachedUrlName;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getAttachedFileName() {
        return attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }
}
