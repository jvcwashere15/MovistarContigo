package pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseReporteFlexMiEquipo {
    private String file;
    private Message message;
    private String nameFile;

    public String getFile() { return file; }
    public void setFile(String value) { this.file = value; }

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public String getNameFile() { return nameFile; }
    public void setNameFile(String value) { this.nameFile = value; }
}
