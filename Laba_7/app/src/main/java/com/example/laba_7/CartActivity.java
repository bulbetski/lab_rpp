package com.example.laba_7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new CartRecyclerAdapter());
        Button button = findViewById(R.id.buy_btn_cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemService.getInstance().performPurchase(getApplicationContext());
                finish();
            }
        });
    }
}
