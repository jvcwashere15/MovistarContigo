package pe.com.qallarix.movistarcontigo.health;

import pe.com.qallarix.movistarcontigo.health.pojos.DataPlan;
import pe.com.qallarix.movistarcontigo.health.pojos.DataSalud;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceHealthApi {
    @GET("health/plans")
    Call<DataSalud> getListaPlanesSalud();
    @GET("health/plan/{id}")
    Call<DataPlan> getPlanSalud(@Path("id") long id);
}
