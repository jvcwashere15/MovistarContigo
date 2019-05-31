package pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos;

import java.util.List;

public class QuiebreConsulta {
    private Message message;
    private List<QueryTypeList> queryTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<QueryTypeList> getQueryTypeList() { return queryTypeList; }
    public void setQueryTypeList(List<QueryTypeList> value) { this.queryTypeList = value; }
}
