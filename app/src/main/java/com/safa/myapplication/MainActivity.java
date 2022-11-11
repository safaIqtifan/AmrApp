package com.safa.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.safa.myapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page = 1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MainAdapter(MainActivity.this, dataArrayList);
        binding.layoutRv.setLayoutManager(new LinearLayoutManager(this));
        binding.layoutRv.setAdapter(adapter);

        getData(page, limit);

        binding.scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    binding.loadingLY.setVisibility(View.VISIBLE);
                    getData(page,limit);
                }
            }
        });

    }

    private void getData(int page, int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<String> call = mainInterface.STRING_CALL(page,limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    binding.loadingLY.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseResult(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseResult(JSONArray jsonArray) {

        for(int i=0; i<jsonArray.length(); i++ ){

            try {
                JSONObject object = jsonArray.getJSONObject(i);
                MainData data = new MainData();
                data.setImage(object.getString("image"));
                data.setTitle(object.getString("title"));

                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MainAdapter(MainActivity.this, dataArrayList);
            binding.layoutRv.setAdapter(adapter);
        }

    }
}