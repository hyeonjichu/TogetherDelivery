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

public class TogetherListAdapter extends RecyclerView.Adapter<TogetherListAdapter.ListviewHoler> {

    Context context;
    ArrayList<TogetherListModel> togetherListModelArrayList;

    public TogetherListAdapter(Context context, ArrayList<TogetherListModel> togetherListModel) {
        this.context = context;
        this.togetherListModelArrayList = togetherListModel;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private MyAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {

        this.mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public TogetherListAdapter.ListviewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_together_list, parent,false);
        return new ListviewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TogetherListAdapter.ListviewHoler holder, int position) {
        TogetherListModel togetherListModel = togetherListModelArrayList.get(position);
        holder.storeName.setText(togetherListModel.storeName);
        holder.peopleNum.setText(togetherListModel.peopleNum);
        holder.curStatus.setText(togetherListModel.curStatus);
        holder.place.setText(togetherListModel.place);
        holder.finishTime.setText(togetherListModel.finishTime);
        holder.curPeople.setText(togetherListModel.curPeople);
    }

    @Override
    public int getItemCount() {
        return togetherListModelArrayList.size();
    }

    public class ListviewHoler extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView peopleNum;
        TextView curStatus;
        TextView place;
        TextView finishTime;
        TextView curPeople;
        public ListviewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.together_list_storeName);
            peopleNum = itemView.findViewById(R.id.together_list_peopleNum);
            curStatus = itemView.findViewById(R.id.together_list_curStatus);
            place = itemView.findViewById(R.id.together_list_place);
            finishTime = itemView.findViewById(R.id.together_list_finishTime);
            curPeople = itemView.findViewById(R.id.curPeople);
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
