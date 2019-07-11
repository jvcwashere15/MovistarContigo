package pe.com.qallarix.movistarcontigo.flexplace.historial.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseHistorialFlexPlace {
    private Message message;
    private List<FlexPlace> list;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<FlexPlace> getList() {
        return list;
    }

    public void setList(List<FlexPlace> list) {
        this.list = list;
    }
}
