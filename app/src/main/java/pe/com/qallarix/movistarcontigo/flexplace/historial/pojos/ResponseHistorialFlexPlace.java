package pe.com.qallarix.movistarcontigo.flexplace.historial.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseHistorialFlexPlace {
    private Message message;
    private FlexPlace[] historialFlexPlace;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public FlexPlace[] getHistorialFlexPlace() {
        return historialFlexPlace;
    }

    public void setHistorialFlexPlace(FlexPlace[] historialFlexPlace) {
        this.historialFlexPlace = historialFlexPlace;
    }
}
