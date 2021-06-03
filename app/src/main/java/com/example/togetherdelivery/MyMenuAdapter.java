package com.example.togetherdelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuAdapter.ListviewHoler> {
    Context context;
    ArrayList<MenuModel> menuModelArrayList;
    ArrayList<MenuModel> chkmenuArrayList = new ArrayList<>();

    public MyMenuAdapter(Context context, ArrayList<MenuModel> menuModelArrayList){
        this.context = context;
        this.menuModelArrayList = menuModelArrayList;
    }


    @NonNull
    @NotNull
    @Override

    public MyMenuAdapter.ListviewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_store_menu, parent, false);

        return new ListviewHoler(v);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyMenuAdapter.ListviewHoler holder, int position) {
        MenuModel menuModel = menuModelArrayList.get(position);

        holder.menuName.setText(menuModel.menuName);
        holder.menuPrice.setText(menuModel.menuPrice);
        holder.menuInfo.setText(menuModel.menuInfo);

        holder.Menu_checkBox.setOnCheckedChangeListener(null);  // 체크박스 초기화
        holder.Menu_checkBox.setChecked(menuModel.isSelected());

        holder.Menu_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                menuModel.setSelected(isChecked);   // set your object`s last status
            }
        });
        holder.Menu_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                menuModel.setSelected(true);
                menuModelArrayList.get(position).setSelected(true);

            }
        });

    }

    @Override
    public int getItemCount() { return menuModelArrayList.size(); }

    public  class ListviewHoler extends RecyclerView.ViewHolder {
        TextView menuName, menuInfo, menuPrice;
        CheckBox Menu_checkBox;


        public ListviewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.list_menuName);
            menuPrice = itemView.findViewById(R.id.list_menuPrice);
            menuInfo = itemView.findViewById(R.id.list_menuInfo);
            Menu_checkBox = (CheckBox) itemView.findViewById(R.id.Menu_checkBox);
        }
    }




}
