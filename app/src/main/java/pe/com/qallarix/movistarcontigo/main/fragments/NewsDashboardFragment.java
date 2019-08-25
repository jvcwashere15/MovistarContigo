package pe.com.qallarix.movistarcontigo.main.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.news.NewsDetailActivity;
import pe.com.qallarix.movistarcontigo.news.pojos.News;

public class NewsDashboardFragment extends Fragment {

    private TextView tvTitleNews;
    private ImageView ivImageNews;
    private News news;
    private Button btSeeMore;
    private static final String ARGUMENT_NEWS = "news";

    public NewsDashboardFragment() {}

    public static NewsDashboardFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NEWS,news);
        NewsDashboardFragment fragment = new NewsDashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDataFromArguments();
        View rootView = getInflatedView(inflater,container);
        bindViews(rootView);
        return rootView;
    }

    private View getInflatedView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_news_dashboard, container, false);
    }

    private void bindViews(View mainView) {
        tvTitleNews = mainView.findViewById(R.id.noticia_dash_tvTitulo);
        ivImageNews = mainView.findViewById(R.id.noticia_dash_ivImagen);
        btSeeMore = mainView.findViewById(R.id.noticia_dash_btVerMas);
    }

    private void getDataFromArguments() {
        news = (News) getArguments().getSerializable(ARGUMENT_NEWS);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitleNews.setText(news.getTitle());
        Glide.with(getActivity())
                .load(news.getImageUrl())
                .placeholder(R.drawable.placeholder_imagen)
                .centerCrop()
                .into(ivImageNews);

        btSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id",String.valueOf(news.getId()));
                Analitycs.logEventoClickDashboardVerMasNoticia(getActivity());
                startActivity(intent);
            }
        });
    }
}
