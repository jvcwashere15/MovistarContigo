package pe.com.qallarix.movistarcontigo.vacations.register;

import pe.com.qallarix.movistarcontigo.vacations.register.pojos.ResponseRegistrarVacaciones;
import pe.com.qallarix.movistarcontigo.vacations.register.pojos.ResponseValidarFechas;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceRegisterVacationsApi {
    @GET("vacation/employee/request/validation")
    Call<ResponseValidarFechas> validarEntreFechas(@Query("employeeCip") String employeeCip,
                                                   @Query("dateStart") String dateStart,
                                                   @Query("dateEnd") String dateEnd);

    @POST("vacation/employee/request/register")
    Call<ResponseRegistrarVacaciones> registrarVacaciones(@Query("employeeCip") String employeeCip,
                                                          @Query("dateStart") String dateStart,
                                                          @Query("dateEnd") String dateEnd);
}