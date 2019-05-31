package pe.com.qallarix.movistarcontigo.descuentos.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DetalleDescuento {
    private Message message;
    private Discount discount;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Discount getDiscount() { return discount; }
    public void setDiscount(Discount value) { this.discount = value; }
}
