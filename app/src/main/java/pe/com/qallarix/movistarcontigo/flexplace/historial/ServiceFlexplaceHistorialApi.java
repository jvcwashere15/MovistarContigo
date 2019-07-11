package pe.com.qallarix.movistarcontigo.flexplace.historial;

import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseHistorialFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseRegistrarFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceFlexplaceHistorialApi {
    @GET("flexplace/employee/requests")
    Call<ResponseHistorialFlexPlace> getHistorialFlexPlace(@Query("documentNumber") String documentNumber,
                                                           @Query("status") int status,
                                                           @Query("anio") int anio);
}