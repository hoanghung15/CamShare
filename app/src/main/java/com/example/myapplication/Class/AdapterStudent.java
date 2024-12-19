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

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.MyViewHolder> {
    private List<Student> listStudent;

    public AdapterStudent(List<Student> studentList) {
        this.listStudent = studentList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewstudent,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student =listStudent.get(position);
        holder.txtName.setText(student.getName());
        holder.txtMSV.setText(student.getId());
    }

    @Override
    public int getItemCount() {
        return listStudent.size();
    }

    class MyViewHolder  extends RecyclerView.ViewHolder{
        private CardView cardViewStudent;
        private TextView txtName,txtMSV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewStudent = itemView.findViewById(R.id.cardViewStudent);
            txtName = itemView.findViewById(R.id.txtName);
            txtMSV = itemView.findViewById(R.id.txtMSV);
        }
    }
}
