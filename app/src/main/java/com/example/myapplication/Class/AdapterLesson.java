package com.example.myapplication.Class;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;
import com.example.myapplication.View.detailLesson;

import java.util.List;

public class AdapterLesson extends  RecyclerView.Adapter<AdapterLesson.MyViewHolder> {
    private List<Lesson> listLesson;
    private Context context;
    public   lessonSelection lessonSelection;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlesson,parent,false);
        return new MyViewHolder(view);
    }

    public AdapterLesson(List<Lesson> listLesson, Context context, lessonSelection lessonSelection) {
        this.listLesson = listLesson;
        this.context = context;
        this.lessonSelection=lessonSelection;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Lesson lesson = listLesson.get(position);
        lessonSelection = new lessonSelection();
        holder.txtTime.setText(lesson.getTime());
        holder.txtIdLesson.setText(lesson.getId());
        holder.textClassName.setText(lesson.getName());
        holder.txtClassLocation.setText(lesson.getLocation());
        holder.cardViewLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detailLesson.class);
                lessonSelection.setId(lesson.getId());
                lessonSelection.setName(lesson.getName());
                lessonSelection.setTime(lesson.getTime());
                lessonSelection.setCredit(lesson.getCredit());
                lessonSelection.setLocation(lesson.getLocation());
                Log.d("lessonSelection",lessonSelection.toString());
                intent.putExtra("lessonSelection",lessonSelection);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLesson.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardViewLesson;
        private ImageView iconLocation;
        private ImageView iconTime;
        private ImageView iconIDclass;
        private ImageView iconLogo;

        private TextView textClassName ;
        private TextView txtClassLocation ;
        private TextView txtTime ;
        private TextView txtIdLesson ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewLesson =itemView.findViewById(R.id.cardViewLesson);
            iconLocation = itemView.findViewById(R.id.iconLocation);
            iconTime = itemView.findViewById(R.id.iconTime);
            iconIDclass = itemView.findViewById(R.id.iconIDclass);
            textClassName = itemView.findViewById(R.id.textClassName);
            txtClassLocation = itemView.findViewById(R.id.txtClassLocation);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtIdLesson = itemView.findViewById(R.id.txtIdLesson);
            iconLogo = itemView.findViewById(R.id.iconLogo);

        }
    }
}
