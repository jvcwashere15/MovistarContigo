package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseRegistrarFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceFlexplaceRegistrarApi {
    @POST("flexplace/employee/request/register")
    Call<ResponseRegistrarFlexPlace> registrarFlexPlace(@Query("documentNumber") String documentNumber,
                                                        @Query("dateStart") String dateStart,
                                                        @Query("dateEnd") String dateEnd,
                                                        @Query("dayWeek") int dayWeek);
}