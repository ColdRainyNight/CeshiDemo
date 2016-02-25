package com.ceshidemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NetCallBack<Data> {

    // private String s;
    private List<Data.DataBean> list = new ArrayList<>();
    private XRecyclerView rcl;
    private MyAdapter adapter;
    private int page = 1;
    private String ulrs = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=" + page;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            Gson gson = new Gson();
            Data bean = gson.fromJson(result, Data.class);
            list.addAll(bean.getData());
            adapter.notifyDataSetChanged();
        }
    };
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //捕获异常
        //System.out.println(s.equals("any string"));
        rcl = (XRecyclerView) findViewById(R.id.rcl);
        //判断网络是否可用
        if (NetUtils.isNetworkAvailable(this) == true) {
            Toast.makeText(this, "网络可用", Toast.LENGTH_SHORT).show();
        } else {
            NetUtils.toSystemSetting(this);
        }

        initData();
        adapter = new MyAdapter(list, this);
        rcl.setAdapter(adapter);

        linearLayoutManager = new LinearLayoutManager(this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rcl.setLayoutManager(linearLayoutManager);
        //自定义分割线
        rcl.addItemDecoration(new ItemDecoration(MainActivity.this));

        //条目点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });


        rcl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新数据在这
                rcl.refreshComplete();
                page = 1;
                list.clear();
                initData();
                // adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                //负载更这里数据
                rcl.loadMoreComplete();
                page++;
                initData();
                //adapter.notifyDataSetChanged();
            }
        });

        //打印Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("debug");
        Logger.e("error");
        Logger.w("warning");
        Logger.v("verbose");
        Logger.i("information");
        Logger.wtf("wtf!!!!");
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("custom")
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }

    private void initData() {
        OkHttpClient okc = new OkHttpClient();
        Request req = new Request.Builder()
                .url(ulrs)
                .build();
        okc.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = response.body().string();
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void successNet(Data data) {
        Log.i("TAG", "successNet: " + data.getData().toString());
        list.addAll(data.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void errorNet(String errorMsg, int errorCode) {
        Log.e("TAG", "===============" + "123");
    }
}
