package com.example.myapplication.Class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class AdapterDraftCheckin  extends RecyclerView.Adapter<AdapterDraftCheckin.MyViewHolder>{
    private List<String>lstDraft;



    public AdapterDraftCheckin(List<String> lstDraft) {
        this.lstDraft = lstDraft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddraftcheckin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String tmp = lstDraft.get(position);
        holder.txtTimeStamp.setText(tmp);
    }

    @Override
    public int getItemCount() {
        return lstDraft.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardDraftCheckin;;
        private TextView txtTimeStamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardDraftCheckin = itemView.findViewById(R.id.cardDraftCheckin);
            txtTimeStamp = itemView.findViewById(R.id.txtTimeStamp);
        }
    }
}
