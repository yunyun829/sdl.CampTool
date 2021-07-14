package jp.ac.titech.itpro.sdl.camptool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<DiaryEntry> list;

    MyViewAdapter(List<DiaryEntry> l){
        list = l;
    }

    public interface onClickEvent{
        void onClickAction(int position);
    }

    private onClickEvent event;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        MyViewHolder vh = new MyViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                event.onClickAction(position);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleView.setText(list.get(position).getTitle());
        holder.timeView.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setEvent(onClickEvent event) {
        this.event = event;
    }
}
