package pe.com.qallarix.movistarcontigo.discounts;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.discounts.pojos.Discount;


public class DiscountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_DESCUENTO= 0, VIEW_TYPE_LOADING = 1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Discount> discounts;
    Context context;
    ClickDescuento clickDescuento;
    int visibleTreshold;
    int lastVisibleItem, totalItemCount;
    private static final String DESCUENTO_EXTERNO = "Externo";
    private static final String DESCUENTO_PRIX = "Prix";


    public interface ClickDescuento{
        void onClick(View view, int position);
    }

    public DiscountAdapter(RecyclerView recyclerView, Activity activity, List<Discount> discounts, Context context, final int visibleTreshold, ClickDescuento clickDescuento) {
        this.activity = activity;
        this.discounts = discounts;
        this.context = context;
        this.clickDescuento = clickDescuento;
        this.visibleTreshold = visibleTreshold;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (!isLoading && totalItemCount <= lastVisibleItem){
                    if (loadMore != null){
                        loadMore.onLoadMore();
                    }
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return discounts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DESCUENTO;
    }

    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_DESCUENTO){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_descuento,viewGroup,false);
            return new DescuentoHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,viewGroup,false);
            return new LoadingHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof DescuentoHolder){
            DescuentoHolder descuentoHolder = (DescuentoHolder) viewHolder;
            Discount currentDiscount = discounts.get(position);
            descuentoHolder.bindTitulo(currentDiscount.getBrand());
            if (TextUtils.isEmpty(currentDiscount.getDiscount())) descuentoHolder.bindCantidad("");
            else descuentoHolder.bindCantidad(currentDiscount.getDiscount());
            descuentoHolder.bindDescripcion1(currentDiscount.getTitle());
            descuentoHolder.bindDescripcion2(currentDiscount.getDiscountDetail());
            descuentoHolder.bindImagen(currentDiscount.getDiscountImage(),context);
            if (currentDiscount.getOrigin().equals(DESCUENTO_EXTERNO)) {
                descuentoHolder.tvTitulo.setTextColor(context.getResources().getColor(R.color.colorCanalEmbajador));
                descuentoHolder.setImagenTipoDescuento(R.drawable.ic_solapa_movistar, context);
            }else {
                descuentoHolder.tvTitulo.setTextColor(context.getResources().getColor(R.color.colorSalud));
                descuentoHolder.setImagenTipoDescuento(R.drawable.ic_solapa_prix, context);
            }
            descuentoHolder.flItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickDescuento.onClick(v,position);
                }
            });
        }else if (viewHolder instanceof LoadingHolder){
            LoadingHolder loadingHolder = (LoadingHolder) viewHolder;
            loadingHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    static class DescuentoHolder extends RecyclerView.ViewHolder{
        ImageView ivImagen;
        ImageView ivTipoDescuento;
        TextView tvTitulo;
        TextView tvCantidad;
        TextView tvDescripcion1;
        TextView tvDescripcion2;
        FrameLayout flItemView;

        public DescuentoHolder(View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.item_descuento_ivImagen);
            tvTitulo = itemView.findViewById(R.id.item_descuento_tvTitulo);
            tvCantidad = itemView.findViewById(R.id.item_descuento_tvCantidad);
            tvDescripcion1 = itemView.findViewById(R.id.item_descuento_tvDescripcion_1);
            tvDescripcion2 = itemView.findViewById(R.id.item_descuento_tvDescripcion_2);
            flItemView = itemView.findViewById(R.id.flItemView);
            ivTipoDescuento = itemView.findViewById(R.id.item_descuento_ivTipoDescuento);
        }

        public void bindImagen(String urlImagen, Context context){
            Picasso.with(context).load(urlImagen).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        }

        public void bindTitulo(String titulo){
            tvTitulo.setText(titulo);
        }

        public void bindCantidad(String cantidad){
            tvCantidad.setText(cantidad);
        }

        public void bindDescripcion1(String descripcion){
            tvDescripcion1.setText(descripcion);
        }

        public void bindDescripcion2(String descripcion){
            tvDescripcion2.setText(descripcion);
        }

        public void setImagenTipoDescuento(int resourceImagen, Context context){
            ivTipoDescuento.setImageResource(resourceImagen);
        }
    }

    public static class LoadingHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}
