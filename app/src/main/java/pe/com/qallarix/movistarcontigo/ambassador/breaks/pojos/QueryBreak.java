package pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos;

import java.util.List;

public class QueryBreak {
    private Message message;
    private List<QueryTypeList> queryTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<QueryTypeList> getQueryTypeList() { return queryTypeList; }
    public void setQueryTypeList(List<QueryTypeList> value) { this.queryTypeList = value; }
}
