package pe.com.qallarix.movistarcontigo.notificacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.DetalleBeneficioEspecialActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.salud.DetalleSaludActivity;
import pe.com.qallarix.movistarcontigo.salud.SaludActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesActivity extends TranquiParentActivity {
    private RecyclerView rvNotificaciones;
    private List<Notification> notifications;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        configurarToolbar();
        configurarRecyclerView();
        cargarListaNotificaciones();
    }

    private void cargarListaNotificaciones() {
        Call<Notificaciones> call = WebService.getInstance(getDocumentNumber())
                .createService(ServiceNotificationApi.class)
                .getListaNotificaciones(getDocumentNumber());
        call.enqueue(new Callback<Notificaciones>() {
            @Override
            public void onResponse(Call<Notificaciones> call, Response<Notificaciones> response) {
                if (response.code() == 200){
                    notifications = response.body().getNotifications();
                    NotificacionAdapter notificacionAdapter = new NotificacionAdapter(NotificacionesActivity.this, notifications, new NotificacionAdapter.NotificacionClick() {
                        @Override
                        public void onClick(View v, int position) {
                            Intent notifyIntent;
                            Notification currentNotification = notifications.get(position);
                            String pantalla = currentNotification.getModule();
                            if (pantalla.equals("noticia")){
                                notifyIntent = new Intent(NotificacionesActivity.this, DetalleNoticiaActivity.class);
                            }else if (pantalla.equals("descuento")){
                                notifyIntent = new Intent(NotificacionesActivity.this, DetalleDescuentoActivity.class);
                                notifyIntent.putExtra("origin","Externo");
                            }else if (pantalla.equals("especial")){
                                notifyIntent = new Intent(NotificacionesActivity.this, DetalleBeneficioEspecialActivity.class);
                            }else if (pantalla.equals("salud")){
                                notifyIntent = new Intent(NotificacionesActivity.this, DetalleSaludActivity.class);
                            }else{
                                notifyIntent = new Intent(NotificacionesActivity.this, MainActivity.class);
                            }
                            notifyIntent.putExtra("id",currentNotification.getIDPost());
                            notifyIntent.putExtra("lista_notificaciones",true);
                            startActivity(notifyIntent);
                        }
                    });
                    rvNotificaciones.setAdapter(notificacionAdapter);
                }
            }

            @Override
            public void onFailure(Call<Notificaciones> call, Throwable t) {
                Toast.makeText(NotificacionesActivity.this, "Se produjo un error al intentar obtener la lista de notificaciones", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_just_account, menu);
//        View view = menu.findItem(R.id.action_account).getActionView();
//        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
//        tvIniciales.setText(obtenerIniciales());
//        tvIniciales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NotificacionesActivity.this, AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    private void configurarRecyclerView() {
        rvNotificaciones = findViewById(R.id.notificaciones_rvNotificaciones);
        rvNotificaciones.setLayoutManager(new LinearLayoutManager(this));
        rvNotificaciones.setHasFixedSize(true);
        rvNotificaciones.setNestedScrollingEnabled(false);
    }

    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notificaciones");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }
}
