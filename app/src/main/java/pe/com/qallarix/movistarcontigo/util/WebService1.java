package pe.com.qallarix.movistarcontigo.util;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService1 {

    private final String BASE_URL_WS1_QA = "https://qallarix-ws-qa.azurewebsites.net/";
    private final String BASE_URL_WS1_PRODUCCION = "https://tcqallarix.azurewebsites.net/";
    private final String BASE_URL_W1 = BASE_URL_WS1_PRODUCCION;

    private static WebService1 instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private OkHttpClient.Builder okHttpClientBuilder;
    private static final String BASIC_AUTH = "Basic " + Base64.encodeToString("qallarix:cWFsbGFyaXgqbW92aXN0YXI=".getBytes(), Base64.NO_WRAP);
    private String mDni;

    public static void setInstance(WebService1 instance) {
        WebService1.instance = instance;
    }

    public WebService1(String dni){
        mDni = dni;
        httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuild = original.newBuilder()
                                .addHeader("documentNumber",mDni)
                                .addHeader("Authorization",BASIC_AUTH)
                                .method(original.method(),original.body());
                        Request request = requestBuild.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(httpLoggingInterceptor);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_W1)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WebService1 getInstance(String dni){
        if (instance == null)
            instance = new WebService1(dni);
        return instance;
    }

    public <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
