package pe.com.qallarix.movistarcontigo.vacations.forapprove;

import pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos.RegistroVacaciones;
import pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos.ResponseListaSolicitudes;
import pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos.ResponseRegistrarAprobacion;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceForApproveVacationsApi {

    @GET("vacation/leadership/requests/pending")
    Call<ResponseListaSolicitudes> obtenerListaSolicitudesAprobar(@Query("employeeCip") String employeeCip,
                                                                @Query("page") int page);

    @POST("vacation/leadership/request/approver")
    Call<ResponseRegistrarAprobacion> aprobarRechazarSolicitud(@Body RegistroVacaciones registroVacaciones);
}