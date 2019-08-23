package pe.com.qallarix.movistarcontigo.flexplace.register.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseRegistrarFlexPlace {
    private Message message;
    private long requestId;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
