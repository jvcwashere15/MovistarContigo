package pe.com.qallarix.movistarcontigo.ambassador;

import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.Break;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.AmbassadorHome;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.AmbassadorMobile;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.QueryBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.QuestionBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.ProductBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.NetworkBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.RegisteredBreak;
import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.ResponseTraceability;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.ResponseMovistarTotal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAmbassadorApi {
    @GET("ambassador/benefit/mobile")
    Call<AmbassadorMobile> getDataAmbassadorMobile();
    @GET("ambassador/benefit/home")
    Call<AmbassadorHome> getDataAmbassadorHome();

    @GET("ambassador/break/type/network")
    Call<NetworkBreak> getDataComboRed();
    @GET("ambassador/break/type/query")
    Call<QueryBreak> getDataComboConsulta(@Query("codeNetwork") String codeNetwork);
    @GET("ambassador/break/type/product")
    Call<ProductBreak> getDataComboProducto(@Query("codeNetwork") String codeNetwork, @Query("codeQuery") String codeQuery);

    @GET("ambassador/break/validate")
    Call<QuestionBreak> getDataPreguntas(@Query("codeProduct") String codeProduct);

    @POST("ambassador/break")
    Call<RegisteredBreak> setQuiebre(@Body Break aBreak);

    @GET("ambassador/break/traceability")
    Call<ResponseTraceability> getTraceability();

    @GET("ambassador/benefit/total")
    Call<ResponseMovistarTotal> getMovistarTotal();


//    @GET("ambassador/benefits/movil")
//    Call<AmbassadorMobile> getDataAmbassadorMobile();
//    @GET("ambassador/benefits/fijo")
//    Call<AmbassadorHome> getDataAmbassadorHome();
//    @GET("ambassador/type/network")
//    Call<NetworkBreak> getDataComboRed();
//    @GET("ambassador/type/query")
//    Call<QueryBreak> getDataComboConsulta(@Query("codeNetwork") String codeNetwork);
//    @GET("ambassador/type/product")
//    Call<ProductBreak> getDataComboProducto(@Query("codeNetwork") String codeNetwork, @Query("codeQuery") String codeQuery);
//
//    @GET("ambassador/break/validate")
//    Call<QuestionBreak> getDataPreguntas(@Query("codeProduct") String codeProduct);
//
//    @POST("ambassador/break")
//    Call<RegisteredBreak> setQuiebre(@Body Break quiebre);


}
