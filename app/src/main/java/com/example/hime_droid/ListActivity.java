package com.example.hime_droid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hime_droid.models.ItemModel;
import com.example.hime_droid.models.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    String stories = "";
    ListAdapter adapter;
    ArrayList<ItemModel> listItem = new ArrayList<ItemModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            stories = null;
            Log.d("list ne", "co cai nit y");
        } else {
            stories = extras.getString("STORIES");
            Log.d("list ne", stories);
        }

        // get bai hoc
        Log.d("hello", "start app");
        try {
            JSONArray jArray = new JSONArray(stories);
            Log.d("jArray ne: ", String.valueOf(jArray.length()));

            for (int i = 0; i < jArray.length(); ++i) {
                Log.d("item thu", String.valueOf(i));
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    listItem.add(new ItemModel(
                            oneObject.getInt("id"),
                            oneObject.getString("title"),
                            oneObject.getString("shorttext"),
                            oneObject.getString("html")
                    ));
                } catch (JSONException e) {
                    // Oops
                }
            }

            Log.d("chuan bi casting", "hihi");
            adapter = new ListAdapter(listItem);
            Log.d("cast duoc roi", "hihi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stories_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}