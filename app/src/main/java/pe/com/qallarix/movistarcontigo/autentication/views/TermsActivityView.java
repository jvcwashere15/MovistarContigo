package pe.com.qallarix.movistarcontigo.autentication.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pe.com.qallarix.movistarcontigo.R;


public class TermsActivityView extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);
        bindViews();
        setUpToolbar();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
    }


    private void setUpToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Términos y Condiciones");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
