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

public class WebServiceVacaciones {
    private final String BASE_URL_WS2_QA = "https://qallarix-vc-qa.azurewebsites.net/";
    private final String BASE_URL_WS2_PRODUCCION = "https://vcqallarix.azurewebsites.net";
    private final String BASE_URL_W2 = BASE_URL_WS2_QA;


    private static WebServiceVacaciones instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private OkHttpClient.Builder okHttpClientBuilder;
    private static final String BASIC_AUTH = "Basic " + Base64.encodeToString("qallarix:cWFsbGFyaXgqbW92aXN0YXI=".getBytes(), Base64.NO_WRAP);
    private String mDni;

    public static void setInstance(WebServiceVacaciones instance) {
        WebServiceVacaciones.instance = instance;
    }

    public WebServiceVacaciones(String dni){
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
                .baseUrl(BASE_URL_W2)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WebServiceVacaciones getInstance(String dni){
        if (instance == null)
            instance = new WebServiceVacaciones(dni);
        return instance;
    }

    public <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}