package pe.com.qallarix.movistarcontigo.flexplace.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DashBoardFlexPlace {
    private Message message;
    private Dashboard dashboard;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
}
