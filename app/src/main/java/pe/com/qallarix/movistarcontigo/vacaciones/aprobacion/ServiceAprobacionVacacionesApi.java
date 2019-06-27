package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseListaSolicitudes;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseRegistrarAprobacion;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseSolicitudAprobacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAprobacionVacacionesApi {

    @GET("vacation/leadership/requests/pending")
    Call<ResponseListaSolicitudes> obtenerListaSolicitudesAprobar(@Query("employeeCip") String employeeCip,
                                                                @Query("page") int page);
    @GET("vacation/leadership/request/consult")
    Call<ResponseSolicitudAprobacion> obtenerDetalleSolicitudAprobar(@Query("employeeCip") String employeeCip,
                                                               @Query("requestCode") String requestCode);

    @POST("vacation/leadership/request/approver")
    Call<ResponseRegistrarAprobacion> aprobarRechazarSolicitud(@Query("employeeCip") String employeeCip,
                                                               @Query("requestCode") String requestCode,
                                                               @Query("approver") boolean approver);
}