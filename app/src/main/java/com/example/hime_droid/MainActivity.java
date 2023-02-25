package com.example.hime_droid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.hime_droid.helpers.Constant;
import com.example.hime_droid.helpers.GetHttpRequest;
import com.example.hime_droid.helpers.PostHttpRequest;
import com.example.hime_droid.helpers.Upload;
import com.example.hime_droid.models.ItemModel;
import com.example.hime_droid.models.ListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hime_droid.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public String API_UPLOAD = new Constant().API_DOMAIN + "/v1/upload";
    public String API_GETBAIHOC = new Constant().API_DOMAIN +  "/v1/getbaihoc";
    public String API_GETSTORY = new Constant().API_DOMAIN + "/v1/getcauchuyen";
    public String API_SEARCH = new Constant().API_DOMAIN + "/v1/search";

    public ProgressBar pb;

    ListAdapter adapter;
    ArrayList<ItemModel> listItem = new ArrayList<ItemModel>();

    String currentPhotoPath;
    File photoFileGlobal;
    String baihocGlobal;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get bai hoc
        Log.d("hello", "start app");
        try {
            String baihocs = new GetHttpRequest().execute(API_GETBAIHOC).get();
            Log.d("baihocs ne: ", baihocs);
            baihocGlobal = baihocs;

            JSONArray jArray = new JSONArray(baihocs);
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

        } catch (ExecutionException e) {
            Log.d("exception", "ExecutionException roiiiiiiiiiiiiiiii");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("exception", "InterruptedException roiiiiiiiiiiiiiiii");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.suggestion_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton btnCamera = (FloatingActionButton) findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);

        Button btnBaiHoc = findViewById(R.id.btnBaiHoc);
        Button btnThuVien = findViewById(R.id.btnThuVien);

        btnBaiHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                listActivity.putExtra("STORIES", baihocGlobal);
                startActivity(listActivity);
                pb.setVisibility(View.GONE);
            }
        });

        btnThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                String stories = null;
                try {
                    stories = new GetHttpRequest().execute(API_GETSTORY).get();
                    Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                    listActivity.putExtra("STORIES", stories);
                    startActivity(listActivity);
                    pb.setVisibility(View.GONE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("query ne", query);

        try {
            String listStory = new PostHttpRequest().execute(query).get();
            Log.d("listStory ne", listStory);
            Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
            listActivity.putExtra("STORIES", listStory);
            startActivity(listActivity);
            pb.setVisibility(View.GONE);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    public void startActivityFromMainThread(String stories) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                listActivity.putExtra("STORIES", stories);
                startActivity(listActivity);
                pb.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoFileGlobal != null) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String listStory = new Upload().execute(photoFileGlobal).get();
                            startActivityFromMainThread(listStory);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();
            } else {
                Log.d("huyhuhuhu", "dont know why");
            }
        }
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.hime_droid.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                photoFileGlobal = photoFile;

                pb.setVisibility(View.VISIBLE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}