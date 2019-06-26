package pe.com.qallarix.movistarcontigo.vacaciones;

import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.salud.pojos.DataPlan;
import pe.com.qallarix.movistarcontigo.salud.pojos.DataSalud;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.VacacionesDashboard;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceVacacionesApi {
    @GET("vacation/future/joy")
    Call<VacacionesDashboard> getInfoDashboardVacaciones(@Query("employeeCode") String employeeCode);
}