package pe.com.qallarix.movistarcontigo.flexplace.historial;

import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseDetalleFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseHistorialFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseRegistrarFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceFlexplaceHistorialApi {
    public static final String CANCELADO = "01";
    public static final String PENDIENTE = "02";
    public static final String APROBADO = "03";
    public static final String RECHAZADO = "04";

    @GET("flexplace/employee/requests")
    Call<ResponseHistorialFlexPlace> getHistorialFlexPlace(@Query("status") String status,
                                                           @Query("year") int anio);

    @GET("flexplace/employee/request/id")
    Call<ResponseDetalleFlexPlace> getDetalleFlexPlace(@Query("requestCode") int requestCode);
}