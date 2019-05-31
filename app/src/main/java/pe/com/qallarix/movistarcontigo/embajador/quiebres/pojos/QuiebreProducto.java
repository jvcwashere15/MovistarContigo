package pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos;

import java.util.List;

public class QuiebreProducto {
    private Message message;
    private List<ProductTypeList> productTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<ProductTypeList> getProductTypeList() { return productTypeList; }
    public void setProductTypeList(List<ProductTypeList> value) { this.productTypeList = value; }
}
