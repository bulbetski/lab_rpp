package com.example.laba_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {
    //asdfghjkl;
    ImageHolder holder;
    ListFragment recyclerFragment;
    ViewPagerFragment pagerHolderFragment;
    final static String TAG_1 = "RECYCLER_FRAGMENT";
    final static String TAG_2 = "VIEW_PAGER_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Click click = new Click() {
            @Override
            public void click(int position) {
                pagerHolderFragment = new ViewPagerFragment(position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, pagerHolderFragment, TAG_2);
                transaction.commit();
            }
        };
        recyclerFragment = new ListFragment(click);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, recyclerFragment, TAG_1);
        transaction.commit();
        List<Item> items = ItemHandler.getInstance().getItems();
        holder = ImageHolder.createInstance(items, recyclerFragment);
        String[] keys = holder.images.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key :  keys) {
            HttpHandler httpHandler = new HttpHandler();
            httpHandler.execute(key);
        }
    }
    public class HttpHandler extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String key = strings[0];
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + key)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                holder.images.put(key, bitmap);
                System.out.println(key);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            recyclerFragment.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_2) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, recyclerFragment, TAG_1)
                    .commit();
        } else  {
            finish();
        }
    }
}
