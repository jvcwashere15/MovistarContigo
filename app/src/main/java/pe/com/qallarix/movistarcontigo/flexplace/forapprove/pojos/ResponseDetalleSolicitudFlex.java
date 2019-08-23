package pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseDetalleSolicitudFlex {
    private Message message;
    private FlexRequestDetail request;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public FlexRequestDetail getRequest() {
        return request;
    }

    public void setRequest(FlexRequestDetail request) {
        this.request = request;
    }
}
