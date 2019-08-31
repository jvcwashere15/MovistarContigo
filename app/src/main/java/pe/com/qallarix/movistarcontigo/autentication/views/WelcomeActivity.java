package pe.com.qallarix.movistarcontigo.autentication.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.main.MainActivity;


public class WelcomeActivity extends AppCompatActivity {

    private TextView tvNombreUsuario;
    private TextView btComenzar;
    private String mNombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        tvNombreUsuario = findViewById(R.id.bienvenido_tvNombreUsuario);
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains("shortName")){
            String shortName = sharedPreferences.getString("shortName","");
            if (!shortName.equals("")){
                tvNombreUsuario.setText(shortName+"!");
            }
        }

        btComenzar = findViewById(R.id.bienvenido_btComenzar);

        btComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
