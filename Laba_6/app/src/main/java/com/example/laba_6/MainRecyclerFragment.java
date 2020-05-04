package com.example.laba_6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerFragment extends Fragment {
    Click click;
    public MainRecyclerFragment(Click click) {
        super();
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_fragment_main, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.main_recycler);
        final MainRecyclerAdapter adapter = new MainRecyclerAdapter(click);
        DataChangedListener listener = new DataChangedListener() {
            @Override
            public void notifyDataChanged() {
                adapter.notifyDataSetChanged();
            }
        };
        ItemService.getInstance().addDataChangedListener(listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }
}
