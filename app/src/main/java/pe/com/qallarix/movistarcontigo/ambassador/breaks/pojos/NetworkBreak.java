package pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos;

import java.util.List;

public class NetworkBreak {
    private Message message;
    private List<NetworkTypeList> networkTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<NetworkTypeList> getNetworkTypeList() { return networkTypeList; }
    public void setNetworkTypeList(List<NetworkTypeList> value) { this.networkTypeList = value; }
}
