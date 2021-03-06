package com.example.timy.loriproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.timy.loriproject.R;
import com.example.timy.loriproject.activity.AddActivity;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Tag;
import com.example.timy.loriproject.restApi.domain.TimeEntry;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterListEvent extends RecyclerView.Adapter<AdapterListEvent.EventListAdapter> {

    private List<TimeEntry> timeEntries;
    private static SharedPreferences sharedPreferences;
    private Realm realm;
    private Context context;

    public AdapterListEvent(List<TimeEntry> timeEntries, SharedPreferences sharedPreferences, Context context) {
        this.timeEntries = timeEntries;
        AdapterListEvent.sharedPreferences = sharedPreferences;
        realm = Realm.getDefaultInstance();
        this.context = context;
    }

    @Override
    public EventListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new EventListAdapter(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(EventListAdapter holder, int position) {
        TimeEntry vo = timeEntries.get(position);
        holder.taskName.setText(vo.getTaskName());
        holder.date.setText("Дата: " + vo.getDate());
        holder.taskName.setText("Задача: " + vo.getTaskName());
        holder.projectName.setText("Проект: " + vo.getTask().getProject().getName());
        if (vo.getTags() != null && !vo.getTags().isEmpty()) {
            List<Tag> tags = vo.getTags();
            StringBuilder tagStr = new StringBuilder();
            for (Tag tag : tags) {
                tagStr.append("#").append(tag.getName());
            }
            holder.activity.setText(tagStr);
        } else {
            holder.activity.setText("");
        }

        int val = Integer.parseInt(vo.getTimeInMinutes());
        int hours = val / 60;
        int minutes = val % 60;
        String s = String.format("%02d:%02d", hours, minutes);

        holder.time.setText("Потраченно : " + s);
        holder.description.setText(vo.getDescription());

        String tokken = sharedPreferences.getString("tokken", null);

        holder.removeBtn.setOnClickListener(v -> LoriApiClass.getApi().deleteTimeEntry(timeEntries.get(position).getId(), "Bearer " + tokken)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        String id = timeEntries.get(position).getId();

                        realm.executeTransactionAsync(realm -> realm.where(TimeEntry.class)
                                .equalTo("id", id)
                                .findAll()
                                .deleteAllFromRealm());
                        timeEntries.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, timeEntries.size());
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Snackbar.make(holder.cardView, "Нет связи с сервером!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Log.d("error", t.getMessage());
                    }
                }));

        holder.updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddActivity.class);
            intent.putExtra("timeEntry", vo.getId());
            intent.putExtra("timeEntryDate", vo.getDate());
            intent.putExtra("timeEntryTimeInMinutes", vo.getTimeInMinutes());
            intent.putExtra("timeEntryDesc", vo.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return timeEntries.size();
    }

    public static class EventListAdapter extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.time_entry_date)
        TextView date;

        @BindView(R.id.time_entry_project_name)
        TextView projectName;

        @BindView(R.id.time_entry_task_name)
        TextView taskName;

        @BindView(R.id.time_entry_time)
        TextView time;

        @BindView(R.id.time_entry_remove_button)
        ImageButton removeBtn;

        @BindView(R.id.time_entry_update_button)
        ImageButton updateBtn;

        @BindView(R.id.time_entry_descr)
        TextView description;

        @BindView(R.id.time_entry_activity)
        TextView activity;


        public EventListAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
