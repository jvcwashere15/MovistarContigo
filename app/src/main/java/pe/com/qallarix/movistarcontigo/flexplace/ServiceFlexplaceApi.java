package pe.com.qallarix.movistarcontigo.flexplace;

import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceFlexplaceApi {
    @GET("flexplace/employee/dashboard")
    Call<DashBoardFlexPlace> getInfoDashboardFlexplace(@Query("documentNumber") String documentNumber);

}