
package pe.com.qallarix.movistarcontigo.ambassador.breaks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.NetworkTypeList;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.ProductTypeList;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.QueryTypeList;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.Question;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.Break;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.QueryBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.QuestionBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.ProductBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.NetworkBreak;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.RegisteredBreak;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AmbassadorBreaksActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private View vTitular;
    private View vFormulario;
    private View vPregunta;
    private View vAveriasHogar;
    private String eventExtOrForm = "";

    private TextView btnContinuar;
    private RadioGroup rgTitular;
    private Spinner spNetwork;
    private Spinner spQuery;
    private Spinner spProduct;
    private RecyclerView rvQuiebrePreguntas;
    private QuestionAdapter questionAdapter;
    private ProgressDialog progressDialog;

    private static final String QUIEBRE_FORMULARIO= "FORM";
    private static final String QUIEBRE_AVERIA= "EXT";

    private TextView tvAveriaHogarTitulo;
    private TextView tvAveriaHogarDescripcion;
    private TextView tvAveriaHogarLink;

    private boolean altaNueva = false;


    private List<String> opcionesCombo1 = new ArrayList<>();
    private List<String> opcionesCombo2 = new ArrayList<>();
    private List<String> opcionesCombo3 = new ArrayList<>();

    private List<NetworkTypeList> networkTypeLists = new ArrayList<>();
    private List<QueryTypeList> queryTypeLists = new ArrayList<>();
    private List<ProductTypeList> productTypeLists = new ArrayList<>();

    private List<Question> questionList = new ArrayList<>();

    private EditText etNombre, etDni, etTelefonoContacto, etEmail, etTelefonoConsulta;
    private EditText etEmbajadorEmail, etEmbajadorCelular, etEmbajadorDescripcion;
    private TextView btnRegistrarCaso;

    private Dialog dialog;
    private String mName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_reclamos);
        mName = getFullName();
        configurarToolbar();
        bindearVistas();
        configurarRadioGroupTitular();
        configurarComboNetwork();
        configurarRecyclerView();
        configurarBotonContinuar();
        configurarBotonRegistrarQuiebre();
    }


    private void configurarBotonRegistrarQuiebre() {
        btnRegistrarCaso = findViewById(R.id.embajador_quiebres_btnRegistrarCaso);
        btnRegistrarCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioEsValido()){
                    progressDialog = ProgressDialog.show(AmbassadorBreaksActivity.this, "Embajador #YoteAyudo",
                            "Registrando el caso...", true);
                    String mCodigos = productTypeLists.get(spProduct.getSelectedItemPosition()-1).getCode();
                    String codeNetwork = mCodigos.substring(0,mCodigos.indexOf("-"));
                    String aux = mCodigos.substring(mCodigos.indexOf("-")+1);
                    String codeQuery = aux.substring(0,aux.indexOf("-"));
                    String codeProduct = aux.substring(aux.indexOf("-")+1);
                    Break mBreak = new Break(
                            getDocumentNumber(),
                            etEmbajadorEmail.getText().toString(),
                            mName,
                            etEmbajadorCelular.getText().toString(),
                            codeNetwork,
                            codeProduct,
                            codeQuery,
                            etTelefonoContacto.getText().toString(),
                            etEmbajadorDescripcion.getText().toString(),
                            etDni.getText().toString(),
                            etEmail.getText().toString(),
                            etNombre.getText().toString(),
                            etTelefonoConsulta.getText().toString()
                    );
                    Call<RegisteredBreak> call = WebServiceAmbassador.getInstance(getDocumentNumber())
                            .createService(ServiceAmbassadorApi.class)
                            .setQuiebre(mBreak);
                    call.enqueue(new Callback<RegisteredBreak>() {
                        @Override
                        public void onResponse(Call<RegisteredBreak> call, Response<RegisteredBreak> response) {
                            progressDialog.dismiss();
                            if (response.code() == 200)
                                mostrarDialogQuiebreRegistrado(response.body().getSaveBreak().getCode());
                            else{
                                Toast.makeText(AmbassadorBreaksActivity.this, "Error al intentar registrar quiebre", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<RegisteredBreak> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(AmbassadorBreaksActivity.this, "Error al intentar registrar quiebre", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }


    private boolean formularioEsValido() {
        if (TextUtils.isEmpty(etNombre.getText().toString().trim())){
            mostrarMensaje("Datos del titular del servicio: debe indicar el nombre");
            etNombre.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etDni.getText().toString().trim())){
            mostrarMensaje("Datos del titular del servicio: debe indicar el DNI/C.E.");
            etDni.requestFocus();
            return false;
        }else if (etDni.getText().toString().trim().length() < 8){
            mostrarMensaje("Datos del titular del servicio: documento inválido. Dni(8 dígitos) y C.E.(9 dígitos)");
            etDni.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etTelefonoContacto.getText().toString().trim())){
            mostrarMensaje("Datos del titular del servicio: debe indicar el teléfono de contacto");
            etTelefonoContacto.requestFocus();
            return false;
        }else if (etTelefonoContacto.getText().toString().trim().length() < 7){
            mostrarMensaje("Datos del titular del servicio: el teléfono de contacto debe tener 7(fijo) ó 9(celular) dígitos");
            etTelefonoContacto.requestFocus();
            return false;
        }
        if (!altaNueva){
            if (TextUtils.isEmpty(etTelefonoConsulta.getText().toString().trim())){
                mostrarMensaje("Datos del titular del servicio: debe indicar el teléfono en consulta");
                etTelefonoConsulta.requestFocus();
                return false;
            }else if (etTelefonoConsulta.getText().toString().trim().length() < 7){
                mostrarMensaje("Datos del titular del servicio: el teléfono de consulta debe tener 7(fijo) ó 9(celular) dígitos");
                etTelefonoConsulta.requestFocus();
                return false;
            }
        }

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())){
            mostrarMensaje("Datos del titular del servicio: debe indicar el correo electrónico");
            etEmail.requestFocus();
            return false;
        }else if (!mailValido(etEmail.getText().toString())){
            mostrarMensaje("Datos del titular del servicio: correo electrónico con formato incorrecto");
            etEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etEmbajadorEmail.getText().toString().trim())){
            mostrarMensaje("Datos del embajador: debe indicar el correo electrónico");
            etEmbajadorEmail.requestFocus();
            return false;
        }else if (!mailValido(etEmbajadorEmail.getText().toString())){
            mostrarMensaje("Datos del embajador: correo electrónico con formato incorrecto");
            etEmbajadorEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etEmbajadorCelular.getText().toString().trim())){
            mostrarMensaje("Datos del embajador: debe indicar el número de celular");
            etEmbajadorEmail.requestFocus();
            return false;
        }else if (etEmbajadorCelular.getText().toString().trim().length() < 9){
            mostrarMensaje("Datos del embajador: el número de celular debe tener 9 dígitos");
            etEmbajadorCelular.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etEmbajadorDescripcion.getText().toString().trim())){
            mostrarMensaje("Descripción del caso: debe proporcionar una breve descripción del caso a registrar");
            etEmbajadorDescripcion.requestFocus();
            return false;
        }
        return true;
    }

    public boolean mailValido(String cadena){
        if (cadena.indexOf(" ") == -1){
            Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(cadena);
            List<String> emails = new ArrayList<>();
            while(matcher.find()) {
                emails.add(matcher.group());
            }
            if (emails.size()>0) return true;
        }

        return false;
    }

    private void configurarRecyclerView(){
        rvQuiebrePreguntas = findViewById(R.id.quiebres_rvPreguntas);
        rvQuiebrePreguntas.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvQuiebrePreguntas.setLayoutManager(layoutManager);
        questionAdapter = new QuestionAdapter(this);
        rvQuiebrePreguntas.setAdapter(questionAdapter);
        rvQuiebrePreguntas.setNestedScrollingEnabled(false);
    }

    private void configurarComboNetwork() {
        final Call<NetworkBreak> call = WebServiceAmbassador.getInstance(getDocumentNumber())
                .createService(ServiceAmbassadorApi.class)
                .getDataComboRed();
        progressDialog = ProgressDialog.show(AmbassadorBreaksActivity.this, "Embajador #YoteAyudo",
                "Cargando Canal Embajador #YoteAyudo...", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.cancel();
                        finish();
                    }
                });
        progressDialog.setCanceledOnTouchOutside(false);

        call.enqueue(new Callback<NetworkBreak>() {
            @Override
            public void onResponse(Call<NetworkBreak> call, Response<NetworkBreak> response) {
                progressDialog.dismiss();
                if (response.code() == 200){
                    if (opcionesCombo1.size()==0){
                        opcionesCombo1.add("Seleccione una opción");
                        networkTypeLists = response.body().getNetworkTypeList();
                        for (NetworkTypeList networkTypeList : networkTypeLists){
                            opcionesCombo1.add(networkTypeList.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbassadorBreaksActivity.this,android.R.layout.simple_spinner_dropdown_item ,opcionesCombo1);
                        spNetwork.setAdapter(adapter);
                    }

                }else{
                    Toast.makeText(AmbassadorBreaksActivity.this, "No se puedo cargar #YoteAyudo", Toast.LENGTH_SHORT).show();;
                    finish();
                }
            }
            @Override
            public void onFailure(Call<NetworkBreak> call, Throwable t) {
                Toast.makeText(AmbassadorBreaksActivity.this, "No se puedo cargar #YoteAyudo", Toast.LENGTH_SHORT).show();;
                finish();
            }
        });

        spNetwork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vPregunta.setVisibility(View.GONE);vFormulario.setVisibility(View.GONE);vTitular.setVisibility(View.GONE);
                vAveriasHogar.setVisibility(View.GONE);
                rgTitular.clearCheck();
                spQuery.setAdapter(null);spProduct.setAdapter(null);
                int currentPosition = position;
                if (currentPosition > 0){
                    NetworkTypeList itemRed = networkTypeLists.get(position-1);
                    String codigoNetwork = itemRed.getCode();
                    opcionesCombo2.clear();
                    Call<QueryBreak> call1 = WebServiceAmbassador.getInstance(getDocumentNumber())
                            .createService(ServiceAmbassadorApi.class)
                            .getDataComboConsulta(codigoNetwork);
                    call1.enqueue(new Callback<QueryBreak>() {
                        @Override
                        public void onResponse(Call<QueryBreak> call, Response<QueryBreak> response) {
                            if (response.code() == 200){
                                configurarComboQuery(response.body());
                            }else{
                                Toast.makeText(AmbassadorBreaksActivity.this, "Problemas con el servidor", Toast.LENGTH_SHORT).show();;
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<QueryBreak> call, Throwable t) {
                            Toast.makeText(AmbassadorBreaksActivity.this, "Problemas con el servidor", Toast.LENGTH_SHORT).show();;
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void configurarComboQuery(final QueryBreak queryBreak){
        if (opcionesCombo2.size()==0){
            opcionesCombo2.add("Seleccione una opción");
            queryTypeLists = queryBreak.getQueryTypeList();
            for (QueryTypeList queryTypeList : queryTypeLists){
                opcionesCombo2.add(queryTypeList.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbassadorBreaksActivity.this,android.R.layout.simple_spinner_dropdown_item ,opcionesCombo2);
            spQuery.setAdapter(adapter);
        }

        spQuery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vPregunta.setVisibility(View.GONE);vFormulario.setVisibility(View.GONE);
                vTitular.setVisibility(View.GONE);vAveriasHogar.setVisibility(View.GONE);
                rgTitular.clearCheck();
                spProduct.setAdapter(null);
                btnContinuar.setEnabled(false);
                int currentPosition = position;
                if (currentPosition > 0){
                    QueryTypeList queryTypeList = queryTypeLists.get(position-1);
                    String codigoQuery = queryTypeList.getCode();
                    String codeNetwork = networkTypeLists.get(spNetwork.getSelectedItemPosition()-1).getCode();
                    opcionesCombo3.clear();
                    Call<ProductBreak> call = WebServiceAmbassador.getInstance(getDocumentNumber())
                            .createService(ServiceAmbassadorApi.class)
                            .getDataComboProducto(codeNetwork,codigoQuery);

                    call.enqueue(new Callback<ProductBreak>() {
                        @Override
                        public void onResponse(Call<ProductBreak> call, Response<ProductBreak> response) {
                            if (response.code() == 200){
                                configurarComboProducto(response.body());
                            }else{
                                Toast.makeText(AmbassadorBreaksActivity.this, "Problemas con el servidor", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductBreak> call, Throwable t) {
                            Toast.makeText(AmbassadorBreaksActivity.this, "Problemas con el servidor", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void configurarComboProducto(ProductBreak productBreak){
        if (opcionesCombo3.size()==0){
            opcionesCombo3.add("Seleccione una opción");
            productTypeLists = productBreak.getProductTypeList();
            for (ProductTypeList productTypeList : productTypeLists){
                opcionesCombo3.add(productTypeList.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbassadorBreaksActivity.this,android.R.layout.simple_spinner_dropdown_item ,opcionesCombo3);
            spProduct.setAdapter(adapter);
        }

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    eventExtOrForm = productTypeLists.get(position-1).getEvent();
                    if (eventExtOrForm.equals(QUIEBRE_AVERIA)){
                        vAveriasHogar.setVisibility(View.VISIBLE);
                        findViewById(R.id.quiebre_averias_hogar_tvClick)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://storageqallarix.blob.core.windows.net/wpqallarixblob/2019/05/TutorialAveriasFija.pdf";
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        btnContinuar.setEnabled(true);
                                    }
                                });
                    }else
                        btnContinuar.setEnabled(true);

                }else vAveriasHogar.setVisibility(View.GONE);
                vPregunta.setVisibility(View.GONE);
                vFormulario.setVisibility(View.GONE);
                vTitular.setVisibility(View.GONE);
                rgTitular.clearCheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void bindearVistas() {
        spNetwork = findViewById(R.id.embajador_quiebres_sp1);
        spQuery = findViewById(R.id.embajador_quiebres_sp2);
        spProduct = findViewById(R.id.embajador_quiebres_sp3);
        vTitular = findViewById(R.id.quiebres_lytTitular);
        vFormulario = findViewById(R.id.quiebres_lytFormulario);
        vPregunta = findViewById(R.id.quiebres_lytPreguntas);
        vAveriasHogar = findViewById(R.id.quiebres_lytAveriasHogar);
        btnContinuar = findViewById(R.id.embajador_quiebres_btnContinuar);
        rgTitular = findViewById(R.id.embajador_quiebres_rgTitular);

        etNombre = findViewById(R.id.quiebre_formulario_etNombre);
        etDni = findViewById(R.id.quiebre_formulario_etDni);
        etEmail = findViewById(R.id.quiebre_formulario_etEmail);
        etTelefonoConsulta = findViewById(R.id.quiebre_formulario_etTelefonoConsulta);
        etTelefonoContacto = findViewById(R.id.quiebre_formulario_etTelefonoContacto);
        etEmbajadorCelular = findViewById(R.id.quiebre_formulario_etEmbajadorCelular);
        etEmbajadorEmail = findViewById(R.id.quiebre_formulario_etEmbajadorEmail);
        etEmbajadorDescripcion = findViewById(R.id.quiebre_formulario_etEmbajadorDescripcion);

        btnRegistrarCaso = findViewById(R.id.embajador_quiebres_btnRegistrarCaso);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("#YoteAyudo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void configurarBotonContinuar(){
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(combosValidos()){
                    ProductTypeList currentProductoTypeList = productTypeLists.get(spProduct.getSelectedItemPosition()-1);
                    String codigoEvento = currentProductoTypeList.getEvent();
                    if (codigoEvento.equals(QUIEBRE_AVERIA)){
                        String url = currentProductoTypeList.getURL();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }else{
                        String codigoProducto = productTypeLists.get(spProduct.getSelectedItemPosition()-1).getCode();
                        if (codigoProducto.equals("MOVIL-PEDIDOS-ALTA") || codigoProducto.equals("FIJA" +
                                "-PEDIDOS-ALTA")){
                            altaNueva = true;
                            etTelefonoConsulta.setVisibility(View.GONE);
                        }else{
                            altaNueva = false;
                            etTelefonoConsulta.setVisibility(View.VISIBLE);
                        }
                        questionList.clear();
                        Call<QuestionBreak> call = WebServiceAmbassador.getInstance(getDocumentNumber())
                                .createService(ServiceAmbassadorApi.class)
                                .getDataPreguntas(codigoProducto);
                        progressDialog = ProgressDialog.show(AmbassadorBreaksActivity.this, "Embajador #YoteAyudo",
                                "Cargando el caso...", true);
                        call.enqueue(new Callback<QuestionBreak>() {
                            @Override
                            public void onResponse(Call<QuestionBreak> call, Response<QuestionBreak> response) {
                                progressDialog.dismiss();
                                if (response.code() == 200) {
                                    questionList = response.body().getQuestions();
                                    questionAdapter.setQuestions(questionList);
                                    vPregunta.setVisibility(View.VISIBLE);
                                    vTitular.setVisibility(View.VISIBLE);
                                }else{
                                    Toast.makeText(AmbassadorBreaksActivity.this, "El servidor no responde", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<QuestionBreak> call, Throwable t) {
                                Toast.makeText(AmbassadorBreaksActivity.this, "El servidor no responde", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean combosValidos() {
        if (spNetwork.getSelectedItemPosition() > 0
                && spQuery.getSelectedItemPosition() > 0
                && spProduct.getSelectedItemPosition() > 0){
            return true;
        }else{
            Toast.makeText(AmbassadorBreaksActivity.this, "Debe especificar tipo de red, consulta y producto", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void configurarRadioGroupTitular(){
        rgTitular.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton)rgTitular.findViewById(checkedId);
                int index = rgTitular.indexOfChild(rb);
                if (index >= 0){
                    if (vFormulario.getVisibility() == View.GONE) vFormulario.setVisibility(View.VISIBLE);
                }
                if (index == 0){
                    SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
                    if (sharedPreferences != null){
                        if (sharedPreferences.contains("documentNumber")){
                            String documentNumber = sharedPreferences.getString("documentNumber","");
                            if (!documentNumber.equals("")){
                                etDni.setText(documentNumber);
                            }
                        }
                        if (sharedPreferences.contains("fullName")){
                            String fullName = sharedPreferences.getString("fullName","");
                            if (!fullName.equals("")){
                                etNombre.setText(fullName);
                            }
                        }
                        if (sharedPreferences.contains("email")){
                            String email = sharedPreferences.getString("email","");
                            if (!email.equals("email")){
                                etEmail.setText(email);
                                etEmbajadorEmail.setText(email);
                            }
                        }
                    }
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
                    if (sharedPreferences != null){
                        if (sharedPreferences.contains("email")){
                            String email = sharedPreferences.getString("email","");
                            if (!email.equals("email")){
                                etEmbajadorEmail.setText(email);
                            }
                        }
                    }
                    etEmail.setText("");
                    etNombre.setText("");
                    etDni.setText("");
                    etTelefonoConsulta.setText("");
                    etTelefonoContacto.setText("");
                    etTelefonoConsulta.setText("");
                    etEmbajadorCelular.setText("");
                    etEmbajadorDescripcion.setText("");
                }
            }
        });
    }


    public void mostrarDialogQuiebreRegistrado(String m){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_quiebres_layout);
        dialog.show();



        TextView tvCasoRegistrado = dialog.findViewById(R.id.dialog_quiebre_tvCasoRegistrado);
        Button btHome = dialog.findViewById(R.id.dialog_quiebre_btIrHome);
        TextView tvHacerOtroReclamo = dialog.findViewById(R.id.dialog_quiebre_tvHacerOtroReclamo);

        tvCasoRegistrado.setText("Hemos registrado con éxito su caso embajador Nº" + m);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        tvHacerOtroReclamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarQuiebre();
                dialog.dismiss();
            }
        });
    }

    public void limpiarQuiebre(){
        limpiarFormulario();
        limpiarRadio();
        limpiarPreguntas();
        limpiarCombos();
    }

    private void limpiarRadio() {
        rgTitular.clearCheck();
    }

    private void limpiarFormulario() {
        etEmbajadorDescripcion.setText("");
        etEmbajadorCelular.setText("");
        etEmbajadorEmail.setText("");
        etTelefonoConsulta.setText("");
        etTelefonoContacto.setText("");
        etEmail.setText("");
        etNombre.setText("");
        etDni.setText("");
    }

    private void limpiarPreguntas(){
        vPregunta.setVisibility(View.GONE);
    }

    private void limpiarCombos(){
        spProduct.setAdapter(null);
        spQuery.setAdapter(null);
        spNetwork.setSelection(0);
    }

    public String getFullName(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("fullName","");
    }
}
