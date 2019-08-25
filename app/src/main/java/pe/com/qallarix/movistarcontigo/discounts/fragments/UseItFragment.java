package pe.com.qallarix.movistarcontigo.discounts.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
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

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UseItFragment extends Fragment {
    private String mHowToUse,mUrlFile, mUrlWeb,mContactName,
            mContactPhone,mContactPhoneAnexo,mContactWhatsapp, mContactEmail;
    private TextView tvHowToUse, tvUrlFile, tvUrlWeb, tvContactName, tvContactPhone,
            tvContactWhatsapp,tvContactEmail;

    private View vTelefono, vWassapp, vCorreo;

    private static final String KEY_HOWTOUSE = "mHowToUse";
    private static final String KEY_URL_FILE = "mUrlFile";
    private static final String KEY_URL_WEB = "mUrlWeb";
    private static final String KEY_CONTACT_NAME = "mContactName";
    private static final String KEY_CONTACT_PHONE = "mContactPhone";
    private static final String KEY_CONTACT_PHONE_ANEXO = "mContactPhoneAnexo";

    private static final String KEY_CONTACT_WHATSAPP = "mContactWhatsapp";
    private static final String KEY_CONTACT_EMAIL = "mContactEmail";

    public UseItFragment() {
        // Required empty public constructor
    }

    public static UseItFragment newInstance(String mHowToUse, String mUrlFile,
                                            String mUrlWeb, String mContactName,
                                            String mContactPhone, String mContactPhoneAnexo,
                                            String mContactWhatsapp,
                                            String mContactEmail) {
        Bundle args = new Bundle();
        args.putString(KEY_HOWTOUSE,mHowToUse);
        args.putString(KEY_URL_FILE,mUrlFile);
        args.putString(KEY_URL_WEB,mUrlWeb);
        args.putString(KEY_CONTACT_NAME,mContactName);
        args.putString(KEY_CONTACT_PHONE,mContactPhone);
        args.putString(KEY_CONTACT_PHONE_ANEXO,mContactPhoneAnexo);
        args.putString(KEY_CONTACT_WHATSAPP,mContactWhatsapp);
        args.putString(KEY_CONTACT_EMAIL,mContactEmail);
        UseItFragment fragment = new UseItFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHowToUse = getArguments().getString(KEY_HOWTOUSE);
        mUrlFile = getArguments().getString(KEY_URL_FILE);
        mUrlWeb = getArguments().getString(KEY_URL_WEB);
        mContactName = getArguments().getString(KEY_CONTACT_NAME);
        mContactPhone = getArguments().getString(KEY_CONTACT_PHONE);
        mContactPhoneAnexo = getArguments().getString(KEY_CONTACT_PHONE_ANEXO);
        mContactWhatsapp = getArguments().getString(KEY_CONTACT_WHATSAPP);
        mContactEmail = getArguments().getString(KEY_CONTACT_EMAIL);

        View rootView =  inflater.inflate(R.layout.fragment_usalo, container, false);
        tvHowToUse = rootView.findViewById(R.id.usalo_descripcion);

        vTelefono = rootView.findViewById(R.id.contacto_vTelefono);
        tvContactPhone = rootView.findViewById(R.id.contacto_tvTelefono);

        vWassapp = rootView.findViewById(R.id.contacto_vWassapp);
        tvContactWhatsapp = rootView.findViewById(R.id.contacto_tvWassapp);
        vCorreo = rootView.findViewById(R.id.contacto_vCorreo);
        tvContactEmail = rootView.findViewById(R.id.contacto_tvCorreo);

        tvUrlWeb = rootView.findViewById(R.id.usalo_tvIrAWeb);
        tvUrlFile = rootView.findViewById(R.id.usalo_tvVerAdjunto);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvHowToUse.setText(mHowToUse);
        if (TextUtils.isEmpty(mUrlFile)) tvUrlFile.setVisibility(View.GONE);
        else {
            tvUrlFile.setVisibility(View.VISIBLE);
            configurarBotonFile();
        }

        if (TextUtils.isEmpty(mUrlWeb)) tvUrlWeb.setVisibility(View.GONE);
        else {
            tvUrlWeb.setVisibility(View.VISIBLE);
            configurarBotonWeb();
        }

        if (!TextUtils.isEmpty(mContactPhone)){
            if (!TextUtils.isEmpty(mContactPhoneAnexo)){
                tvContactPhone.setText(mContactPhone + " - "+ "Anexo " + mContactPhoneAnexo);
            }else tvContactPhone.setText(mContactPhone);
            configurarBotonTelefono();
        }
        else vTelefono.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(mContactWhatsapp)){
            tvContactWhatsapp.setText(mContactWhatsapp);
            configurarBotonWassapp(getActivity(),mContactWhatsapp);
        }
        else vWassapp.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(mContactEmail)){
            tvContactEmail.setText(mContactEmail);
            configurarBotonCorreo();
        } else vCorreo.setVisibility(View.GONE);



    }

    private void configurarBotonWeb() {
        tvUrlWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mUrlWeb));
                startActivity(i);
            }
        });
    }

    private void configurarBotonFile() {
        tvUrlFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrlFile));
                startActivity(intent);
            }
        });
    }

    private void configurarBotonCorreo() {
        vCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + mContactEmail));
                startActivity(emailIntent);
            }
        });
    }

    private void configurarBotonTelefono() {
        vTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mContactPhone));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    public void configurarBotonWassapp(final Activity activity, final String phone) {
        vWassapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String formattedNumber = "+51 " + phone;

                String url = "https://api.whatsapp.com/send?phone="+formattedNumber;

                try{
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }catch (Exception e){}

//                try{
//                    Intent sendIntent =new Intent("android.intent.action.MAIN");
//                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.setType("text/plain");
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,"");
//                    sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
//                    sendIntent.setPackage("com.whatsapp");
//                    activity.startActivity(sendIntent);
//                }
//                catch(Exception e)
//                {
//                    Toast.makeText(activity,"Puedes contactar por"+ e.toString(), Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

}
