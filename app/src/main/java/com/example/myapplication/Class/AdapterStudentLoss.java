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

public class AdapterStudentLoss extends RecyclerView.Adapter<AdapterStudentLoss.MyViewHolder> {
    private List<StudentLoss> lstStLoss;

    public AdapterStudentLoss(List<StudentLoss> lstStLoss) {
        this.lstStLoss = lstStLoss;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewstudentloss,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentLoss studentLoss =lstStLoss.get(position);
        holder.txtID.setText(studentLoss.getId());
        holder.txtCnt.setText("Số buổi nghỉ: "+studentLoss.getCount());
    }

    @Override
    public int getItemCount() {
        return lstStLoss.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardViewStudentLoss;
        private TextView txtID,txtCnt;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            cardViewStudentLoss = itemView.findViewById(R.id.cardViewStudentLoss);
            txtID =itemView.findViewById(R.id.txtID);
            txtCnt = itemView.findViewById(R.id.txtCnt);
        }
    }
}
