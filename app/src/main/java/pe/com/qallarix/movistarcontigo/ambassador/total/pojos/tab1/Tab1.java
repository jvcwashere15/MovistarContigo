package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1;
import java.io.Serializable;
import java.util.List;

public class Tab1 implements Serializable {
    private String title;
    private List<Block> block;

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public List<Block> getBlock() { return block; }
    public void setBlock(List<Block> value) { this.block = value; }
}
