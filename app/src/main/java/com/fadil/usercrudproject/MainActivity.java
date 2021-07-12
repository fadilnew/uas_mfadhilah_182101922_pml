package com.fadil.usercrudproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fadil.usercrudproject.adapter.UserAdapter;
import com.fadil.usercrudproject.model.GetUser;
import com.fadil.usercrudproject.model.User;
import com.fadil.usercrudproject.rest.ApiClient;
import com.fadil.usercrudproject.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnInsert;
    ApiInterface mApiInterface;
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static  MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InsertActivity.class));
            }
        });
        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma = this;
        refresh();
    }

    public void refresh(){
        Call<GetUser> userCall = mApiInterface.getUser();
        userCall.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                List<User> userList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data user"+ String.valueOf(userList.size()));
                mAdapter = new UserAdapter(userList);
                mRecylerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Log.e("Retroffit Get",t.toString());
            }
        });
    }
}