package pe.com.qallarix.movistarcontigo.notificacion;

public class Notification {
    private String description;
    private long id;
    private String idPost;
    private String image;
    private String module;
    private String moduleTitle;
    private String title;
    private long viewed;

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getIDPost() { return idPost; }
    public void setIDPost(String value) { this.idPost = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }

    public String getModule() { return module; }
    public void setModule(String value) { this.module = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public long getViewed() { return viewed; }
    public void setViewed(long value) { this.viewed = value; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }
}
