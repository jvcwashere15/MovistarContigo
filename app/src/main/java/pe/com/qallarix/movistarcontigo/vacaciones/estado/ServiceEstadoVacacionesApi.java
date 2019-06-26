package pe.com.qallarix.movistarcontigo.vacaciones.estado;

import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.ResponseDetalleSolicitud;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.ResponseListaEstados;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseValidarFechas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceEstadoVacacionesApi {
    public static final String APROBADAS = "03";
    public static final String PENDIENTES = "02";
    public static final String RECHAZADAS = "04";


    @GET("vacation/employee/requests")
    Call<ResponseListaEstados> obtenerListaEstadoVacaciones(@Query("employeeCip") String employeeCip,
                                                            @Query("requestType") String requestType,
                                                            @Query("page") int page);
    @GET("vacation/employee/request/consult")
    Call<ResponseDetalleSolicitud> obtenerDetalleVacaciones(@Query("employeeCip") String employeeCip,
                                                            @Query("requestCode") String requestCode);
}