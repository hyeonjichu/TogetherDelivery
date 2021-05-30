package com.example.togetherdelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListviewHoler> {
    Context context;
    ArrayList<ProductsModel> productsModelArrayList;
    Button storeBtn;

    public MyAdapter(Context context, ArrayList<ProductsModel> productsModelArrayList) {
        this.context = context;
        this.productsModelArrayList = productsModelArrayList;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public MyAdapter.ListviewHoler onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_store, parent,false);

        return new ListviewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MyAdapter.ListviewHoler holder, int position) {

        ProductsModel productsModel = productsModelArrayList.get(position);

        holder.storeName.setText(productsModel.storeName);
        holder.storeAdd.setText(productsModel.storeAdd);

    }

    @Override
    public int getItemCount() {
        return productsModelArrayList.size();
    }

    public class ListviewHoler extends RecyclerView.ViewHolder{

        TextView storeName, storeAdd;
        Button storeBtn;

        public ListviewHoler(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.list_storeName);
            storeAdd = itemView.findViewById(R.id.list_storeAdd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }
}
