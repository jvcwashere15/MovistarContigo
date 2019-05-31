package pe.com.qallarix.movistarcontigo.salud;

import pe.com.qallarix.movistarcontigo.salud.pojos.DataPlan;
import pe.com.qallarix.movistarcontigo.salud.pojos.DataSalud;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceSaludApi {
    @GET("health/plans")
    Call<DataSalud> getListaPlanesSalud();
    @GET("health/plan/{id}")
    Call<DataPlan> getPlanSalud(@Path("id") long id);
}
