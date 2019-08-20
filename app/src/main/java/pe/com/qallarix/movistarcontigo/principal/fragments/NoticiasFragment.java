package pe.com.qallarix.movistarcontigo.principal.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.noticias.DataNoticias;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.noticias.News;
import pe.com.qallarix.movistarcontigo.noticias.NoticiasAdapter;
import pe.com.qallarix.movistarcontigo.noticias.ServiceNewsApi;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {
    private RecyclerView rvNoticias;
    private RecyclerView.LayoutManager layoutManager;
    private NoticiasAdapter noticiasAdapter;
    private List<News> noticias;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    private String mDni;
    private View placeholderNoticias;
    private MainActivity parentActivity;
    private View viewSinConexion;
    private static final String ARGUMENT_NOTICIAS = "noticias";
    private static final String ARGUMENT_DNI = "mDni";
    private boolean isLoading = true;


    public NoticiasFragment() {
        // Required empty public constructor
    }

    public static NoticiasFragment newInstance(List<News> noticias, String mDni) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NOTICIAS, (Serializable) noticias);
        args.putString(ARGUMENT_DNI,mDni);
        NoticiasFragment fragment = new NoticiasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentActivity = (MainActivity) getActivity();
        mDni = getArguments().getString(ARGUMENT_DNI);
        noticias =(List<News>) getArguments().getSerializable(ARGUMENT_NOTICIAS);
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
        rvNoticias = rootView.findViewById(R.id.noticias_rvNoticias);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mShimmerViewContainer = rootView.findViewById(R.id.noticias_shimerFrameLayout);
        viewSinConexion = rootView.findViewById(R.id.view_sin_conexion);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvNoticias.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvNoticias.setLayoutManager(layoutManager);
        if (noticias == null){
            cargarNoticiasFromNetWork();
        }else{
            cargarNoticiasFromCache();
            mostrarViewNoticias();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onLoadNoticiasFromNetwork();
            }
        });
    }

    private void cargarNoticiasFromNetWork() {
        if (parentActivity.internetConectionExists()){
            mostrarViewLoading();
            onLoadNoticiasFromNetwork();
        }else{
            mostrarViewSinConexion();
        }
    }


    private void onLoadNoticiasFromNetwork() {
        Call<DataNoticias> call = WebService1.getInstance(mDni)
                .createService(ServiceNewsApi.class)
                .getNoticias();
        call.enqueue(new Callback<DataNoticias>() {
            @Override
            public void onResponse(Call<DataNoticias> call, Response<DataNoticias> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == 200){
                        noticias = response.body().getNews();
                        parentActivity.setNoticias(noticias);
                        cargarNoticiasFromCache();
                        mostrarViewNoticias();

                }else{
                    mostrarViewSinConexion();
                }
            }
            @Override
            public void onFailure(Call<DataNoticias> call, Throwable t) {
                mostrarViewSinConexion();
            }
        });
    }

    private void cargarNoticiasFromCache() {
        noticiasAdapter = new NoticiasAdapter(noticias, getActivity(), new NoticiasAdapter.OnClickNoticia() {
            @Override
            public void notiClick(int position) {
                Intent intent = new Intent(getActivity(),DetalleNoticiaActivity.class);
                News currentNews = noticias.get(position);
                intent.putExtra("id",String.valueOf(currentNews.getId()));
                startActivity(intent);
            }
        });
        rvNoticias.setAdapter(noticiasAdapter);
    }

    private void mostrarViewNoticias() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        viewSinConexion.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        isLoading = false;
    }

    private void mostrarViewLoading() {
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        viewSinConexion.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        isLoading = true;
    }

    private void mostrarViewSinConexion() {
        if (mShimmerViewContainer.isShimmerStarted())mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        viewSinConexion.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        isLoading = false;
    }
}
