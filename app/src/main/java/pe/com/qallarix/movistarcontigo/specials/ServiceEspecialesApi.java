package pe.com.qallarix.movistarcontigo.specials;


import pe.com.qallarix.movistarcontigo.specials.pojos.BeneficiosEspeciales;
import pe.com.qallarix.movistarcontigo.specials.pojos.BenefitDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceEspecialesApi {
    @GET("benefit/specials")
    Call<BeneficiosEspeciales> getBeneficiosEspeciales();
    @GET("benefit/special/{id}")
    Call<BenefitDetail> getBeneficioEspecial(@Path("id") long id);
}
