package pe.com.qallarix.movistarcontigo.beneficioespeciales.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.dynamic.IFragmentWrapper;

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactoFragment extends Fragment {

    private static String KEY_DESCRIPTION = "descripcion";
    private static String KEY_PHONE_NUMBER = "phoneNumber";
    private static String KEY_PHONE_ANEXO = "phoneAnexo";
    private static String KEY_CELL_NUMBER = "cellNumber";
    private static String KEY_EMAIL = "email";

    private String mDescripcion;
    private String mPhoneNumber;
    private String mPhoneAnexo;

    private String mCellNumber;
    private String mEmail;

    private TextView tvDescription;
    private TextView tvPhoneNumber;
    private TextView tvCellNumber;
    private TextView tvEmail;

    private View vTelefono;
    private View vCelular;
    private View vCorreo;


    public ContactoFragment() {
        // Required empty public constructor
    }

    public static ContactoFragment newInstance(String descripcion, String phoneNumber,
                                               String phoneAnexo,
                                               String cellNumber, String email) {
        Bundle args = new Bundle();
        args.putString(KEY_DESCRIPTION,descripcion);
        args.putString(KEY_PHONE_NUMBER,phoneNumber);
        args.putString(KEY_PHONE_ANEXO,phoneAnexo);
        args.putString(KEY_CELL_NUMBER,cellNumber);
        args.putString(KEY_EMAIL,email);
        ContactoFragment fragment = new ContactoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDescripcion = getArguments().getString(KEY_DESCRIPTION);
        mPhoneNumber = getArguments().getString(KEY_PHONE_NUMBER);
        mPhoneAnexo = getArguments().getString(KEY_PHONE_ANEXO);
        mCellNumber = getArguments().getString(KEY_CELL_NUMBER);
        mEmail = getArguments().getString(KEY_EMAIL);
        View rootView = inflater.inflate(R.layout.fragment_contacto, container, false);

        vTelefono = rootView.findViewById(R.id.contacto_vTelefono);
        vCelular = rootView.findViewById(R.id.contacto_vCellNumber);
        vCorreo = rootView.findViewById(R.id.contacto_vCorreo);

        tvDescription = rootView.findViewById(R.id.contacto_tvDescripcion);

        tvPhoneNumber = rootView.findViewById(R.id.contacto_tvTelefono);
        tvCellNumber = rootView.findViewById(R.id.contacto_tvCellNumber);
        tvEmail = rootView.findViewById(R.id.contacto_tvCorreo);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvDescription.setText(mDescripcion);
        if (!TextUtils.isEmpty(mPhoneNumber)){
            configurarBotonTelefono();
        }else vTelefono.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mCellNumber)){
            configurarBotonCelular();
        }else vCelular.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mEmail)){
            configurarBotonEmail();
        }else vCorreo.setVisibility(View.GONE);
    }


    private void configurarBotonTelefono() {
        vTelefono.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty( mPhoneAnexo)){
            tvPhoneNumber.setText(mPhoneNumber + " - " + "Anexo " + mPhoneAnexo);
        }
        else tvPhoneNumber.setText(mPhoneNumber);
        vTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mPhoneNumber));
                startActivity(intent);
            }
        });
    }
    private void configurarBotonCelular() {
        vCelular.setVisibility(View.VISIBLE);
        tvCellNumber.setText(mCellNumber);
        vCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mCellNumber));
                startActivity(intent);
            }
        });
    }

    private void configurarBotonEmail() {
        vCorreo.setVisibility(View.VISIBLE);
        tvEmail.setText(mEmail);
        vCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + mEmail));
                startActivity(emailIntent);
            }
        });
    }

}
