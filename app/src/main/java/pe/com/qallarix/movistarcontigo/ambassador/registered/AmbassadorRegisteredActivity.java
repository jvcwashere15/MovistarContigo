package pe.com.qallarix.movistarcontigo.ambassador.registered;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import pe.com.qallarix.movistarcontigo.R;

public class AmbassadorRegisteredActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvListaRegistrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_registrados);
        configurarToolbar();
        rvListaRegistrados = findViewById(R.id.ambassador_registered_rvLista);
        rvListaRegistrados.setHasFixedSize(true);
        rvListaRegistrados.setLayoutManager(new LinearLayoutManager(this));

        RegisteredAdapter registeredAdapter = new RegisteredAdapter();
        rvListaRegistrados.setAdapter(registeredAdapter);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Quiebres registrados");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
