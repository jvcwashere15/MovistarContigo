package pe.com.qallarix.movistarcontigo.news;

import pe.com.qallarix.movistarcontigo.news.pojos.DataNews;
import pe.com.qallarix.movistarcontigo.news.pojos.DataNoticias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceNewsApi {
    @GET("news/list")
    Call<DataNoticias> getNoticias();
    @GET("news/list/except/{id}")
    Call<DataNoticias> getNoticiasExcepto(@Path("id") int id);
    @GET("news/{id}")
    Call<DataNews> getNoticia(@Path("id") int id);
}
