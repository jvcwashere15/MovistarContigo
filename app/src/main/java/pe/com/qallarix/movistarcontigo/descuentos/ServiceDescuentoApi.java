package pe.com.qallarix.movistarcontigo.descuentos;


import pe.com.qallarix.movistarcontigo.descuentos.pojos.Descuentos;
import pe.com.qallarix.movistarcontigo.descuentos.pojos.DetalleDescuento;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Categorias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceDescuentoApi {
    @GET("discount/list/pag")
    Call<Descuentos> getListaDescuentos(@Query("page") int numPagina);
    @GET("discount/list/pag")
    Call<Descuentos> getListaDescuentos(@Query("page") int numPagina, @Query("category") long idCategoria);
    @GET("discount/id")
    Call<DetalleDescuento> getDescuento(@Query("id") String id, @Query("origin") String origin);
    @GET("discount/categories")
    Call<Categorias> getCategorias();

}
