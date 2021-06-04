package com.example.togetherdelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;

    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            this.TextView_nickname = (TextView) v.findViewById(R.id.TextView_nickname);
            this.TextView_msg = (TextView) v.findViewById(R.id.TextView_msg);
            this.rootView = (View) v;

        }

    }
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {
        this.mDataset = myDataset;
        this.myNickName = myNickName;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())                   // 여기가 view중요
                .inflate((R.layout.list_chat_msg), parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatData chat = mDataset.get(position);


        holder.TextView_nickname.setText(chat.getNick());
        holder.TextView_msg.setText(chat.getMessage()); //DTO

        if (chat.getNick().equals(this.myNickName)) {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);         // minSdkVersion 17 해줘야해!
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        else {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

    }
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public ChatData getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }
    // 넣는것 이전메세지를 더하는 로직을 만들어 줘야함 , 갱신용
    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);  //0,1,2 = 는 사이즈가 3
    }
}
