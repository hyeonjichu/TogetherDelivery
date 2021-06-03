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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListviewHoler> {

    Context context;
    ArrayList<ProductsModel> productsModelArrayList;



    public MyAdapter(Context context, ArrayList<ProductsModel> productsModelArrayList) {
        this.context = context;
        this.productsModelArrayList = productsModelArrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {

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
        holder.storeAddr.setText(productsModel.storeAddr);
        //holder.storeId.setText(productsModel.storeId);

    }

    @Override
    public int getItemCount() {
        return productsModelArrayList.size();
    }

    public class ListviewHoler extends RecyclerView.ViewHolder{

        TextView storeName, storeAddr, storeId;



        public ListviewHoler(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.list_storeName);
            storeAddr = itemView.findViewById(R.id.list_storeAdd);
            //storeId = itemView.findViewById(R.id.textView2);

            //itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null);
                        {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }

}
