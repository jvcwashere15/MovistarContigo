package pe.com.qallarix.movistarcontigo.embajador.movil.pojos;

import java.io.Serializable;

public class SelfhelpList implements Serializable {
    private String selfHelpName;
    private String selfHelpImageURL;
    private String selfHelpDescription;
    private String selfHelpLink;
    private String selfHelpLinkText;

    public String getSelfHelpName() {
        return selfHelpName;
    }

    public void setSelfHelpName(String selfHelpName) {
        this.selfHelpName = selfHelpName;
    }

    public String getSelfHelpImageURL() {
        return selfHelpImageURL;
    }

    public void setSelfHelpImageURL(String selfHelpImageURL) {
        this.selfHelpImageURL = selfHelpImageURL;
    }

    public String getSelfHelpDescription() {
        return selfHelpDescription;
    }

    public void setSelfHelpDescription(String selfHelpDescription) {
        this.selfHelpDescription = selfHelpDescription;
    }

    public String getSelfHelpLink() {
        return selfHelpLink;
    }

    public void setSelfHelpLink(String selfHelpLink) {
        this.selfHelpLink = selfHelpLink;
    }

    public String getSelfHelpLinkText() {
        return selfHelpLinkText;
    }

    public void setSelfHelpLinkText(String selfHelpLinkText) {
        this.selfHelpLinkText = selfHelpLinkText;
    }
}
