package pe.com.qallarix.movistarcontigo.flexplace.miequipo;

import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.ResponseSolicitudesFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceFlexplaceMiEquipoApi {
    public static final String CANCELADO = "01";
    public static final String PENDIENTE = "02";
    public static final String APROBADO = "03";
    public static final String RECHAZADO = "04";

    @GET("flexplace/team/leadership")
    Call<ResponseFlexPlaceEquipo> getFlexPlaceMiEquipo(@Query("month") int month,
                                                            @Query("year") int year);

    @GET("flexplace/team/report")
    Call<ResponseReporteFlexMiEquipo> getReporteFlexMiEquipo(@Query("month") int month,
                                                            @Query("year") int year);

}