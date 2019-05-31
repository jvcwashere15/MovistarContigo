package pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos;

import java.util.List;

public class QuiebreRed {
    private Message message;
    private List<NetworkTypeList> networkTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<NetworkTypeList> getNetworkTypeList() { return networkTypeList; }
    public void setNetworkTypeList(List<NetworkTypeList> value) { this.networkTypeList = value; }
}
