package com.ccloudapp.fit403.ui.menu_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.BaseItemAdapter;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.di.context.ActivityContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AVJEET on 03-09-2017.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<BaseItemAdapter> data;

    private final LayoutInflater mLayoutInflater;
    private WorkoutViewHolder viewHolder;

    @Inject
    public WorkoutAdapter(@ActivityContext Context context){
        mContext=context;
        data=new ArrayList<>();
        mLayoutInflater=LayoutInflater.from(context);
    }

    public void setData(List<Workout> list){
        for (int i=0;i<list.size();i++){
            Workout workout = list.get(i);
            data.add(workout);
            notifyItemInserted(data.size() - 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= mLayoutInflater.inflate(R.layout.workout_item,parent,false);
        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Workout workout = (Workout) getItem(position);
        viewHolder= (WorkoutViewHolder) holder;
        viewHolder.dateTextView.setText(workout.date);
        viewHolder.exerciseCatTextView.setText(workout.exercise_category);
        viewHolder.exerciseNameTextView.setText(workout.exercise_name);
        Glide.with(mContext).load(workout.image_url).into(viewHolder.exerciseImageView);
    }

    public BaseItemAdapter getItem(int position) {
        return data.get(position);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.exercise_name)
        TextView exerciseNameTextView;

        @BindView(R.id.exercise_category)
        TextView exerciseCatTextView;

        @BindView(R.id.exercise_img)
        ImageView exerciseImageView;

        @BindView(R.id.date)
        TextView dateTextView;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
