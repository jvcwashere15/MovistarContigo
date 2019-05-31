package pe.com.qallarix.movistarcontigo.beneficioespeciales;


import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.BeneficiosEspeciales;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.BenefitDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceBeneficiosEspecialesApi {
    @GET("benefit/specials")
    Call<BeneficiosEspeciales> getBeneficiosEspeciales();
    @GET("benefit/special/{id}")
    Call<BenefitDetail> getBeneficioEspecial(@Path("id") long id);
}
