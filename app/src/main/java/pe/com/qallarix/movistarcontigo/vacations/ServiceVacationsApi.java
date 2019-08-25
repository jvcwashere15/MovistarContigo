package pe.com.qallarix.movistarcontigo.vacations;

import pe.com.qallarix.movistarcontigo.vacations.pojos.VacacionesDashboard;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceVacationsApi {
    @GET("vacation/future/joy")
    Call<VacacionesDashboard> getInfoDashboardVacaciones(@Query("employeeCode") String employeeCode);
}