package com.example.timy.loriproject.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.timy.loriproject.R;
import com.example.timy.loriproject.adapters.vo.TestVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dmitr on 21.11.2017.
 */

public class AdapterListEvent extends RecyclerView.Adapter<AdapterListEvent.EventListAdapter> {

    List<TestVo> list;

    public AdapterListEvent(List<TestVo> list) {
        this.list = list;
    }

    @Override
    public EventListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        EventListAdapter eventListAdapter = new EventListAdapter(v);
        return eventListAdapter;
    }

    @Override
    public void onBindViewHolder(EventListAdapter holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class EventListAdapter extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.name)
        TextView name;


        public EventListAdapter(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class AdapterClickListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private AdapterListEvent.ClickListener clickListener;

        public AdapterClickListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
               @Override
                public boolean onSingleTapUp(MotionEvent e){
                   return true;
               }

               @Override
                public void onLongPress(MotionEvent e){
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener !=null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
               }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child,rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
