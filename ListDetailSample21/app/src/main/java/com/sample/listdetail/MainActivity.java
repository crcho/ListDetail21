package com.sample.listdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Mockoon 설치 후, /user/list api를 추가하고, Test Data는 아래와 같이 입력 하였습니다.
[
  {
    "color":"4521796",
    "title":"titleA",
    "name":"skk"
  },
  {
    "color":"12627746",
    "title":"titleB",
    "name":"TESTNAME"
  }
]
 */

public class MainActivity extends AppCompatActivity implements ItemClickHandler {

//    RetrofitService service;
    RecyclerView listView;
    List<UserData> userDataList;
    final String url = "http://211.204.131.108:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView)findViewById(R.id.userlist);
        //createRetrofitObject();
    }

    private void createRetrofitObject() {
        //        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        service = retrofit.create(RetrofitService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //postByRetrofit2();
        postByOkHttp3();

    }

    private void postByRetrofit2() {
//        Call<List<UserData>> userList = service.getUserList();
//        userList.enqueue(new Callback<List<UserData>>() { //async
//            @Override
//            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
//                userDataList = response.body();
//                if (userDataList == null) {
//                    return;
//                }
//                CustomAdapter adapter = new CustomAdapter(userDataList, MainActivity.this);
//                listView.setAdapter(adapter);
//                listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UserData>> call, Throwable t) {
//                t.printStackTrace();
//                Log.wtf("MainActivity", "????????");
//            }
//
//        });
    }

    private void postByOkHttp3() {
        OkHttpClient client = new OkHttpClient();
        //JSONObject postCallObjSample = new JSONObject();
        //RequestBody body = RequestBody.create(MediaType.parse("application/json"), postCallObjSample.toString());
        Request.Builder builder = new Request.Builder().url(url + "/user/list").get(); //get으로 부르려면 끝에 .get() post로 부르려면 끝에 .post(body);
        Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.wtf("MainActivity", "????????");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String responseString = response.body().string();
                    Log.d("SK", responseString);
                    JSONArray jsonArray = new JSONArray(responseString);
                    userDataList = new ArrayList<>();
                    for (int inx = 0; inx < jsonArray.length(); inx++) {
                        UserData data = new UserData();
                        JSONObject jsonObject = jsonArray.getJSONObject(inx);
                        data.name = jsonObject.getString("name");
                        data.color = jsonObject.getString("color");
                        data.title = jsonObject.getString("title");
                        userDataList.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                if (userDataList.isEmpty()) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CustomAdapter adapter = new CustomAdapter(userDataList, MainActivity.this);
                        listView.setAdapter(adapter);
                        listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }


    //Adapter에서 넘어온다. RecyclerView는 onItemClickListener가 없다.
    @Override
    public void itemClickEvent(int position, String value) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("name", userDataList.get(position).name);
        startActivity(intent);
    }

}