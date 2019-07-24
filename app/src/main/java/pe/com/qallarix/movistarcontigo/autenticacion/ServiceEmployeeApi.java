package pe.com.qallarix.movistarcontigo.autenticacion;

import pe.com.qallarix.movistarcontigo.autenticacion.pojos.CerrarSesionToken;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ValidacionToken;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceEmployeeApi {
    @GET("employee/generateTokenOTP/generate")
    Call<ResponseToken> getGenerateToken(@Query("documentType") String documentType,
                                         @Query("documentNumber") String documentNumber);

    @GET("employee/validateTokenOTP/validate")
    Call<ValidacionToken> validarToken(@Query("documentType") String documentType,
                                       @Query("documentNumber") String documentNumber,
                                       @Query("tokenAccess") String tokenAccess,
                                       @Query("tokenNotification") String tokenNotification,
                                       @Query("typeMovile") String typeMovile);

    @GET("employee/validateSession/validate")
    Call<ValidacionToken> validarSesion(@Query("documentType") String documentType,
                                        @Query("documentNumber") String documentNumber,
                                        @Query("tokenAccess") String tokenAccess,
                                        @Query("tokenNotification") String tokenNotification,
                                        @Query("typeMovile") String typeMovile);

    @GET("employee/closeSession/close")
    Call<CerrarSesionToken> cerrarSesion(@Query("documentType") String documentType,
                                         @Query("documentNumber") String documentNumber,
                                         @Query("tokenAccess") String tokenAccess);

}