package com.example.laba_2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    List<Item> items;
    ImageHolder imageHolder;
    Click click;
    public ListAdapter(Click click) {
        super();
        this.click = click;
        this.items = ItemHandler.getInstance().getItems();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        holder.helptext.setText(items.get(position).getHelptext());
        holder.name.setText(items.get(position).getName());
        holder.graphics.setImageBitmap(imageHolder.getImage(items.get(position).getGraphic()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView graphics;
        TextView name;
        TextView helptext;
        LinearLayout layout;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            graphics = itemView.findViewById(R.id.graphics);
            name = itemView.findViewById(R.id.name);
            helptext = itemView.findViewById(R.id.helptext);
            layout = itemView.findViewById(R.id.item_layout);
        }
    }

    public void setImageHolder(ImageHolder imageHolder) {
        this.imageHolder = imageHolder;
    }
}
