package pe.com.qallarix.movistarcontigo.notificacion;


import pe.com.qallarix.movistarcontigo.pojos.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceNotificationApi {
    @GET("notification/list")
    Call<Notificaciones> getListaNotificaciones(@Query("idUsuario") String idUsuario);
    @GET("notification/save")
    Call<Message> notificacionRecibida(@Query("idNotification") int idNotification, @Query("idUsuario") String idUsuario);
    @GET("notification/viewed")
    Call<Message> notificacionVista(@Query("idNotification") int idNotification, @Query("idUsuario") String idUsuario);
}
