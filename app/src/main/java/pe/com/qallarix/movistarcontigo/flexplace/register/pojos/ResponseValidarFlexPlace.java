package pe.com.qallarix.movistarcontigo.flexplace.register.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseValidarFlexPlace {
    private Message message;
    private String title;
    private String detail;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDetail() { return detail; }
    public void setDetail(String value) { this.detail = value; }
}
