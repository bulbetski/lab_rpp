package com.example.laba_2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListFragment extends Fragment {
    public ListAdapter adapter;
    ImageHolder holder;
    Click click;
    public ListFragment(Click click) {
        this.click = click;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter(click);
        List<Item> items = ItemHandler.getInstance().getItems();
        holder = ImageHolder.createInstance(items, this);
        adapter.setImageHolder(holder);
        recyclerView.setAdapter(adapter);
        return v;
    }
}

interface Click {
    void click(int position);
}
