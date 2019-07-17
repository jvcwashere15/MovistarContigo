package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseRegistrarFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseValidarFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceFlexplaceRegistrarApi {
    @POST("flexplace/employee/request/validate")
    Call<ResponseValidarFlexPlace> validarFlexPlace(@Query("dateStart") String dateStart,
                                                    @Query("monthsTaked") int monthsTaked);

    @POST("flexplace/employee/request/register")
    Call<ResponseRegistrarFlexPlace> registrarFlexPlace(@Query("dateStart") String dateStart,
                                                        @Query("monthsTaked") int monthsTaked);
}