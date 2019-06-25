package pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos;

import pe.com.qallarix.movistarcontigo.vacaciones.pojos.VacacionesDashboard;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceRegistrarVacacionesApi {
    @GET("vacation/employee/request/validation")
    Call<ResponseValidarFechas> validarEntreFechas(@Query("employeeCip") String employeeCip,
                                                         @Query("dateStart") String dateStart,
                                                         @Query("dateEnd") String dateEnd);
}