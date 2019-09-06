package pe.com.qallarix.movistarcontigo.flexplace;

import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.register.pojos.ResponseValidarFlexPlace;
import pe.com.qallarix.movistarcontigo.pojos.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceFlexplaceApi {
    @GET("flexplace/employee/dashboard")
    Call<DashBoardFlexPlace> getInfoDashboardFlexplace();

    @POST("flexplace/employee/request/popup")
    Call<Message> registerPopUp(@Query("answer") boolean answer);



}