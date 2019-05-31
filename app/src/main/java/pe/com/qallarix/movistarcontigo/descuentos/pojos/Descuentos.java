package pe.com.qallarix.movistarcontigo.descuentos.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class Descuentos {
    private Message message;
    private List<Discount> discounts;
    private long totalItems;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Discount> getDiscounts() { return discounts; }
    public void setDiscounts(List<Discount> value) { this.discounts = value; }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
