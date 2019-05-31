package pe.com.qallarix.movistarcontigo.noticias;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ServiceNewsApi {
    @GET("news/list")
    Call<DataNoticias> getNoticias();
    @GET("news/list/except/{id}")
    Call<DataNoticias> getNoticiasExcepto(@Path("id") int id);
    @GET("news/{id}")
    Call<DataNews> getNoticia(@Path("id") int id);
}
