package com.example.examenpracticoxolotl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.examenpracticoxolotl.models.ProductsPropertis;

import java.util.ArrayList;

public class ListaProductosadapter extends RecyclerView.Adapter<ListaProductosadapter.ViewHolder> {

     private  ArrayList<ProductsPropertis> dataset;
     private Context context;

    public ListaProductosadapter(Context context) {
        this.context =context;
        dataset= new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ProductsPropertis p =dataset.get(i);
        viewHolder.nombreTextView.setText(p.getProductDisplayName());
        viewHolder.precioTextView.setText(p.getListPrice());

        Glide.with(context)
                .load(p.getSmImage())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.fotoImageView);



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adiccionarListaProductos(ArrayList<ProductsPropertis> listaProductos) {

        dataset.addAll(listaProductos);
        notifyDataSetChanged();
    }

    public void delete() {

        int size = dataset.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                dataset.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView fotoImageView;
        private TextView nombreTextView;
        private TextView precioTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoImageView=(ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView=(TextView) itemView.findViewById(R.id.nombreTextView);
           precioTextView=(TextView) itemView.findViewById(R.id.precioTextView);
        }
    }
}
