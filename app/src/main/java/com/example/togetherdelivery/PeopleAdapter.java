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

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ListviewHoler>{

    Context context;
    ArrayList<PeopleModel> peopleModelArrayList;

    public PeopleAdapter(Context context, ArrayList<PeopleModel> peopleModelArrayList) {
        this.context = context;
        this.peopleModelArrayList = peopleModelArrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private PeopleAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(PeopleAdapter.OnItemClickListener listener) {

        this.mListener = listener;
    }

    @NonNull
    @Override
    public PeopleAdapter.ListviewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_order_people, parent,false);

        return new PeopleAdapter.ListviewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.ListviewHoler holder, int position) {

        PeopleModel peopleModel = peopleModelArrayList.get(position);

        holder.name.setText(peopleModel.orderId);
        holder.price.setText(peopleModel.price);
        holder.pay.setText(peopleModel.payment);
    }

    @Override
    public int getItemCount() {
        return peopleModelArrayList.size();
    }

    public class ListviewHoler extends RecyclerView.ViewHolder{

        TextView name, price, pay;

        public ListviewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_people_name);
            price = itemView.findViewById(R.id.list_people_price);
            pay = itemView.findViewById(R.id.list_people_pay);

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
