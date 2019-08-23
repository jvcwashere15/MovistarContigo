package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.ResponseDetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.ResponseSolicitudesFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ServiceFlexplaceforApproveApi {
    public static final String CANCELADO = "01";
    public static final String PENDIENTE = "02";
    public static final String APROBADO = "03";
    public static final String RECHAZADO = "04";

    @GET("flexplace/leadership/requests")
    Call<ResponseSolicitudesFlexPlace> getSolicitudesFlexPlace(@Query("status") String status,
                                                             @Query("year") int anio);

    @GET("flexplace/leadership/request/id")
    Call<ResponseDetalleSolicitudFlex> getSolicitudFlexPlace(@Query("requestCode") int requestCode);


    @POST("flexplace/leadership/request/approver")
    Call<ResponseFinalizarCancelacion> aprobarRechazarFlexPlace(@Query("requestCode") int requestCode,
                                                                @Query("approver") boolean approver,
                                                                @Query("observation") String observation);


    @PUT("flexplace/leadership/request/cancelled")
    Call<ResponseFinalizarCancelacion> notificarCancelacionFexPlace(@Query("requestCode") int requestCode);

}