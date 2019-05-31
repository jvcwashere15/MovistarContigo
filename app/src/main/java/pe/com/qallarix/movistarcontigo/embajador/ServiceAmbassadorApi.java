package pe.com.qallarix.movistarcontigo.embajador;

import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.EmbajadorHogar;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.EmbajadorMovil;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.Quiebre;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.QuiebreConsulta;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.QuiebrePregunta;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.QuiebreProducto;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.QuiebreRed;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos.QuiebreRegistrado;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAmbassadorApi {
    @GET("ambassador/benefits/movil")
    Call<EmbajadorMovil> getDataEmbajadorMovil();
    @GET("ambassador/benefits/fijo")
    Call<EmbajadorHogar> getDataEmbajadorHogar();

    @GET("ambassador/type/network")
    Call<QuiebreRed> getDataComboRed();
    @GET("ambassador/type/query")
    Call<QuiebreConsulta> getDataComboConsulta(@Query("codeNetwork") String codeNetwork);
    @GET("ambassador/type/product")
    Call<QuiebreProducto> getDataComboProducto(@Query("codeNetwork") String codeNetwork, @Query("codeQuery") String codeQuery);

    @GET("ambassador/break/validate")
    Call<QuiebrePregunta> getDataPreguntas(@Query("codeProduct") String codeProduct);

    @POST("ambassador/break")
    Call<QuiebreRegistrado> setQuiebre(@Body Quiebre quiebre);
}
