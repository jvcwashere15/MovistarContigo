package pe.com.qallarix.movistarcontigo.descuentos.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class Categorias {
    private Message message;
    private List<Category> categories;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> value) { this.categories = value; }
}
