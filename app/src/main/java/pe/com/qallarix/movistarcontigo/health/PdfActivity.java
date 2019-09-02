package pe.com.qallarix.movistarcontigo.health;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class PdfActivity extends TranquiParentActivity {

    PDFView pdfView;
    Toolbar toolbar;
    public static final String KEY_PDF_TITLE_ACTIVITY = "titulo_pdf_activity";
    public static final String KEY_URI_PDF = "uri_pdf";
    private ProgressBar progressBar;
    private String urlPdf;

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        if (savedInstanceState != null && savedInstanceState.containsKey("page"))
            currentPage = savedInstanceState.getInt("page",0);

        String titulo = getIntent().getExtras().getString(KEY_PDF_TITLE_ACTIVITY,"PDF VISOR");
        urlPdf = getIntent().getExtras().getString(KEY_URI_PDF,null);
        configurarToolbar(titulo);
        pdfView = findViewById(R.id.pdfViewer);
        progressBar = findViewById(R.id.progressbar);

        cargarPDF();
    }

    private void cargarPDF() {
        progressBar.setVisibility(View.VISIBLE);
        FileLoader.with(this)
                .load(urlPdf)
                .fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest fileLoadRequest, FileResponse<File> fileResponse) {
                        progressBar.setVisibility(View.GONE);
                        pdfView.fromFile(fileResponse.getBody())
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onRender(new OnRenderListener() {
                                    @Override
                                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                        pdfView.fitToWidth(currentPage);
                                    }
                                }).load();

                    }

                    @Override
                    public void onError(FileLoadRequest fileLoadRequest, Throwable throwable) {
                        Toast.makeText(PdfActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page",pdfView.getCurrentPage());
    }


    public void configurarToolbar(String titulo){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titulo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
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
//                Intent intent = new Intent(PdfActivity.this,AccountActivityView.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
