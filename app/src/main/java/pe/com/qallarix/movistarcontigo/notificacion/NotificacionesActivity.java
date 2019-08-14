package pe.com.qallarix.movistarcontigo.notificacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.DetalleBeneficioEspecialActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.flexplace.historial.DetalleFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.DetalleSolicitudFlexActivity;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.salud.DetalleSaludActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceNotification;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.AprobacionVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pendientes.PendientesVacacionesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesActivity extends TranquiParentActivity {
    private RecyclerView rvNotificaciones;
    private List<Notification> notifications;
    private Toolbar toolbar;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        bindearVistas();
        configurarToolbar();
        configurarRecyclerView();
        cargarListaNotificaciones();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListaNotificacionesFromNetwork();
            }
        });
    }

    private void bindearVistas() {
        rvNotificaciones = findViewById(R.id.notificaciones_rvNotificaciones);
        mShimmerViewContainer = findViewById(R.id.notificaciones_shimerFrameLayout);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
    }

    private void cargarListaNotificaciones() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        getListaNotificacionesFromNetwork();
    }

    private void getListaNotificacionesFromNetwork() {
        Call<ResponseListNotifications> call = WebServiceNotification
                .getInstance(getDocumentNumber())
                .createService(ServiceNotificationApi.class)
                .getListaNotificaciones();
        call.enqueue(new Callback<ResponseListNotifications>() {
            @Override
            public void onResponse(Call<ResponseListNotifications> call, Response<ResponseListNotifications> response) {
                if (response.code() == 200){
                    swipeRefreshLayout.setRefreshing(false);
                    notifications = response.body().getList();
                    final NotificacionAdapter notificacionAdapter = new NotificacionAdapter(NotificacionesActivity.this, notifications, new NotificacionAdapter.NotificacionClick() {
                        @Override
                        public void onClick(View v, int position) {
                            Notification notification = notifications.get(position);
                            Intent notifyIntent = null;

                            if (notification.getModule()!= null &&
                                    !TextUtils.isEmpty(notification.getModule())) {
                                String modulo = notification.getModule();
                                switch (modulo) {
                                    case "noticia":
                                        notifyIntent = new Intent(NotificacionesActivity.this, DetalleNoticiaActivity.class);
                                        break;
                                    case "descuento":
                                        notifyIntent = new Intent(NotificacionesActivity.this, DetalleDescuentoActivity.class);
                                        notifyIntent.putExtra("origin", "Externo");
                                        break;
                                    case "especial":
                                        notifyIntent = new Intent(NotificacionesActivity.this, DetalleBeneficioEspecialActivity.class);
                                        break;
                                    case "salud":
                                        notifyIntent = new Intent(NotificacionesActivity.this, DetalleSaludActivity.class);
                                        break;
                                    case "flexplace":
                                        if (notification.getSubModule() != null &&
                                                !TextUtils.isEmpty(notification.getSubModule())) {
                                            String submodulo = notification.getSubModule();
                                            switch (submodulo){
                                                case "employee":
                                                    if (notification.getAction() != null &&
                                                            !TextUtils.isEmpty(notification.getAction())) {
                                                        String action = notification.getAction();
                                                        switch (action){
                                                            case "cancelled":
                                                                notifyIntent = new Intent(NotificacionesActivity.this,
                                                                        DetalleFlexPlaceActivity.class);
                                                                break;
                                                            case "approver":
                                                                notifyIntent = new Intent(NotificacionesActivity.this,
                                                                        DetalleFlexPlaceActivity.class);
                                                                break;
                                                            case "refuse":
                                                                notifyIntent = new Intent(NotificacionesActivity.this,
                                                                        DetalleFlexPlaceActivity.class);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                    break;
                                                case "leadership":
                                                    if (notification.getAction() != null &&
                                                            !TextUtils.isEmpty(notification.getAction())) {
                                                        String action = notification.getAction();
                                                        switch (action){
                                                            case "cancelled":
                                                                notifyIntent = new Intent(NotificacionesActivity.this,
                                                                        DetalleSolicitudFlexActivity.class);
                                                                break;
                                                            case "register":
                                                                notifyIntent = new Intent(NotificacionesActivity.this,
                                                                        DetalleSolicitudFlexActivity.class);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        break;
                                    case "vacation":
                                        if (notification.getSubModule() != null &&
                                                !TextUtils.isEmpty(notification.getSubModule())) {
                                            String submodulo = notification.getSubModule();
                                            switch (submodulo){
                                                case "employee":
                                                    if (notification.getAction() != null &&
                                                            !TextUtils.isEmpty(notification.getAction())) {
                                                        String action = notification.getAction();
                                                        notifyIntent = new Intent(NotificacionesActivity.this, PendientesVacacionesActivity.class);
                                                        switch (action){
                                                            case "refuse":
                                                                notifyIntent.putExtra("tabSelected", 2);
                                                                break;
                                                            case "approver":
                                                                notifyIntent.putExtra("tabSelected", 1);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                    break;
                                                case "leadership":
                                                    notifyIntent = new Intent(NotificacionesActivity.this, AprobacionVacacionesActivity.class);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        break;
                                    default: break;
                                }
                            }


                            notifyIntent.putExtra("id",notification.getIdPost());
                            notifyIntent.putExtra("lista_notificaciones",true);
                            startActivity(notifyIntent);

                        }
                    });
                    rvNotificaciones.setAdapter(notificacionAdapter);
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseListNotifications> call, Throwable t) {
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
                Toast.makeText(NotificacionesActivity.this, "Se produjo un error al intentar obtener la lista de notificaciones", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void configurarRecyclerView() {
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

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
