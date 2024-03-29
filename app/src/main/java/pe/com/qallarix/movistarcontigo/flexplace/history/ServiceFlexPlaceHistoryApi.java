package pe.com.qallarix.movistarcontigo.flexplace.history;

import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseDetalleFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseHistorialFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ServiceFlexPlaceHistoryApi {
    public static final String CANCELADO = "01";
    public static final String PENDIENTE = "02";
    public static final String APROBADO = "03";
    public static final String RECHAZADO = "04";

    @GET("flexplace/employee/requests")
    Call<ResponseHistorialFlexPlace> getHistorialFlexPlace(@Query("status") String status,
                                                           @Query("year") int anio);

    @GET("flexplace/employee/request/id")
    Call<ResponseDetalleFlexPlace> getDetalleFlexPlace(@Query("requestCode") int requestCode);

    @PUT("flexplace/employee/request/cancelled")
    Call<ResponseFinalizarCancelacion> finalizarCancelacionFlexPlace(@Query("observation") String observation,
                                                           @Query("requestCode") int requestCode);
}