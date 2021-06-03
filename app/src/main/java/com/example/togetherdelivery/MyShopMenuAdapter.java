package com.example.togetherdelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyShopMenuAdapter extends RecyclerView.Adapter<MyShopMenuAdapter.ListviewHoler>{
    Context context;
    ArrayList<MenuModel> menuModelArrayList;

    public MyShopMenuAdapter(Context context, ArrayList<MenuModel> menuModelArrayList){
        this.context = context;
        this.menuModelArrayList = menuModelArrayList;
    }

    @NonNull
    @Override
    public ListviewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_shopbag_menu, parent, false);

        return new ListviewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListviewHoler holder, int position) {

        MenuModel menuModel = menuModelArrayList.get(position);

        holder.menuName.setText(menuModel.menuName);
        holder.menuPrice.setText(menuModel.menuPrice);
    }

    @Override
    public int getItemCount() {
        return menuModelArrayList.size();
    }

    public  class ListviewHoler extends RecyclerView.ViewHolder {
        TextView menuName, menuPrice;

        public ListviewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.list_shop_menuName);
            menuPrice = itemView.findViewById(R.id.list_shop_menuPrice);
        }
    }
}
