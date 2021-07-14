package jp.ac.titech.itpro.sdl.camptool;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView timeView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title_view);
        timeView = (TextView) itemView.findViewById(R.id.time_view);
    }
}
