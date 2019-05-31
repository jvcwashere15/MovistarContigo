package pe.com.qallarix.movistarcontigo.openlearning;

import pe.com.qallarix.movistarcontigo.openlearning.pojos.BeneficioEstudios;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Benefit;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Categorias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceEstudioApi {
    @GET("benefit/study")
    Call<BeneficioEstudios> getListaBeneficiosEstudio();
    @GET("benefit/study")
    Call<BeneficioEstudios> getListaBeneficiosEstudio(@Query("category") long idCategoria);
    @GET("benefit/study/{id}")
    Call<Benefit> getBeneficioEstudio(@Path("id") long id);
    @GET("benefit/study/categories")
    Call<Categorias> getCategorias();
}
