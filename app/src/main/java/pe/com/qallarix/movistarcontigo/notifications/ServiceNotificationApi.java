package pe.com.qallarix.movistarcontigo.notifications;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceNotificationApi {
    @GET("notification/list")
    Call<ResponseListNotifications> getListaNotificaciones();
}
