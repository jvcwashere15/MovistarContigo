package pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos;

import java.util.List;

public class ProductBreak {
    private Message message;
    private List<ProductTypeList> productTypeList;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<ProductTypeList> getProductTypeList() { return productTypeList; }
    public void setProductTypeList(List<ProductTypeList> value) { this.productTypeList = value; }
}
