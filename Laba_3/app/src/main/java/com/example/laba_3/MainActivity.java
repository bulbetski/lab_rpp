package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    List<Student> items;
    List<Student> fill;
    final Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                db.studentDao().clear();
                fill = new ArrayList<>();
                for (int i = 0; i < 5; i++)
                    fill.add(new Student(Integer.toString(random.nextInt()), Calendar.getInstance().getTime().toString()));
                db.studentDao().insertAll(fill);
                items = db.studentDao().getAll();
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                adapter = new RecyclerAdapter(items);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
        };
        Thread newThread= new Thread(r);
        newThread.start();


        final Button add = findViewById(R.id.add_item);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Student newStudent = new Student(Integer.toString(random.nextInt()), Calendar.getInstance().getTime().toString());
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        long id = db.studentDao().insert(newStudent);
                        newStudent.setUid(id);
                    }
                });
                items.add(newStudent);
                adapter.notifyDataSetChanged();
            }
        });

        final Button rename = findViewById(R.id.rename);
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(items.size()-1).setFullName("Иван Иванов");
                adapter.notifyDataSetChanged();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Student student = db.studentDao().selectById(items.get(items.size()-1).getUid());
                        student.setFullName("Иван Иванов");
                        db.studentDao().update(student);
                    }
                });
            }
        });
    }
}
