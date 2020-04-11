package com.example.laba_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.Holder> {
    List<Item> items = ItemHandler.getInstance().getItems();
    ImageHolder imageHolder = ImageHolder.getInstance();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.helptext.setText(items.get(position).getHelptext());
        holder.img.setImageBitmap(imageHolder.getImage(items.get(position).getGraphic()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public  class  Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView helptext;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_pager);
            name = itemView.findViewById(R.id.name_pager);
            helptext = itemView.findViewById(R.id.help_pager);
        }
    }
}
