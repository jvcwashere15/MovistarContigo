package pe.com.qallarix.movistarcontigo.flexplace.solicitudes;

import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseDetalleFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseHistorialFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.ResponseSolicitudesFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ServiceFlexplaceSolicitudesApi {
    public static final String CANCELADO = "01";
    public static final String PENDIENTE = "02";
    public static final String APROBADO = "03";
    public static final String RECHAZADO = "04";

    @GET("flexplace/leadership/requests")
    Call<ResponseSolicitudesFlexPlace> getSolicitudesFlexPlace(@Query("status") String status,
                                                             @Query("year") int anio);

    @GET("flexplace/leadership/request/id")
    Call<ResponseDetalleFlexPlace> getSolicitudFlexPlace(@Query("requestCode") int requestCode);

    @PUT("flexplace/leadership/request/approver")
    Call<ResponseFinalizarCancelacion> aprobarRechazarFlexPlace(@Query("requestCode") int requestCode,
                                                                     @Query("approver") boolean approver,
                                                                     @Query("observation") String observation);
}