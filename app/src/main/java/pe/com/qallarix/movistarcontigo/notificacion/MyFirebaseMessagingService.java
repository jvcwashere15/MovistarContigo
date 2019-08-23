package pe.com.qallarix.movistarcontigo.notificacion;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Map;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.especiales.DetalleEspecialActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.flexplace.history.HistoryFlexDetailActivity;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.ForApproveFlexRequestDetailActivity;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.salud.DetalleSaludActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.VacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.AprobacionVacacionesActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FIREBASE CONTIGO";
    private Context context = this;
    private int id = 0;
    private int requestCode = 0;
    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d("DATA RECIBIDA NOT", "Message data payload: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            final RemoteMessage currentRemoteMessage = remoteMessage;
            createNotificationChannel();
            PendingIntent pendingIntent = linkearPantalla(currentRemoteMessage.getData());
            if (pendingIntent !=  null) buildNotification(currentRemoteMessage,pendingIntent);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "contigo";
            String description = "canal principal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("contigo_app", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent linkearPantalla(Map<String, String> data) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        if (data.containsKey("module") && data.get("module") != null) {
            String modulo = data.get("module");
            switch (modulo) {
                case "noticia":
                    notifyIntent = new Intent(this, DetalleNoticiaActivity.class);
                    break;
                case "descuento":
                    notifyIntent = new Intent(this, DetalleDescuentoActivity.class);
                    notifyIntent.putExtra("origin", "Externo");
                    break;
                case "especial":
                    notifyIntent = new Intent(this, DetalleEspecialActivity.class);
                    break;
                case "salud":
                    notifyIntent = new Intent(this, DetalleSaludActivity.class);
                    break;
                case "flexplace":
                    if (data.containsKey("subModule") && data.get("subModule") != null) {
                        String submodulo = data.get("subModule");
                        switch (submodulo){
                            case "employee":
                                if (data.containsKey("action") && data.get("action")!= null) {
                                    String action = data.get("action");
                                    switch (action){
                                        case "cancelled":
                                            notifyIntent = new Intent(this,
                                                    HistoryFlexDetailActivity.class);
                                            break;
                                        case "approver":
                                            notifyIntent = new Intent(this,
                                                    HistoryFlexDetailActivity.class);
                                            break;
                                        case "refuse":
                                            notifyIntent = new Intent(this,
                                                    HistoryFlexDetailActivity.class);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case "leadership":
                                if (data.containsKey("action") && data.get("action")!= null) {
                                    String action = data.get("action");
                                    switch (action){
                                        case "cancelled":
                                            notifyIntent = new Intent(this,
                                                    ForApproveFlexRequestDetailActivity.class);
                                            break;
                                        case "register":
                                            notifyIntent = new Intent(this,
                                                    ForApproveFlexRequestDetailActivity.class);
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
                    if (data.containsKey("subModule") && data.get("subModule") != null) {
                        String submodulo = data.get("subModule");
                        switch (submodulo){
                            case "employee":
                                if (data.containsKey("action") && data.get("action")!= null) {
                                    String action = data.get("action");
                                    notifyIntent = new Intent(this, VacacionesActivity.class);
                                    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    switch (action){
                                        case "refuse":
                                            notifyIntent.putExtra("resultadoPedidoVacaciones", 2);
                                            break;
                                        case "approver":
                                            notifyIntent.putExtra("resultadoPedidoVacaciones", 1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case "leadership":
                                notifyIntent = new Intent(this, AprobacionVacacionesActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default: break;
            }
        }

        if (notifyIntent != null){
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notifyIntent.putExtra("id",data.get("idPost"));
        }

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context,
                requestCode++,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return notifyPendingIntent;
    }

    private void buildNotification(RemoteMessage remoteMessage,PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"contigo_app")
                .setSmallIcon(R.drawable.ic_notificacion_contigo)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setColor(ContextCompat.getColor(context, R.color.colorCanalEmbajador))

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(remoteMessage.getData().get("body")))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (!TextUtils.isEmpty(remoteMessage.getData().get("image"))){
            try {
                builder.setLargeIcon(Picasso.with(context)
                        .load(remoteMessage.getData().get("image"))
                        .get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id++,builder.build());
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("tokenNotification", token);
        editor.commit();
    }



    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }
}
