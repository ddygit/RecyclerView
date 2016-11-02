package com.example.administrator.recylerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.recylerviewdemo.adapter.mainAdapter;
import com.example.administrator.recylerviewdemo.biz.Xhttp;
import com.example.administrator.recylerviewdemo.entity.Netease;
import com.example.administrator.recylerviewdemo.views.RecycleViewDivider;

import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener ,mainAdapter.OnItemClickListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    String url = "http://c.m.163.com/nc/article/list/T1348647909107/0-20.html";
    mainAdapter myad;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;
Handler handler;
    private String tid="T1348647909107";
    private RecyclerView.OnScrollListener lis=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!srlMain.isRefreshing()) {
                int lastItemPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == myad.getItemCount() - 1) {
                    //加载数据
                    myad.setCurrentState(mainAdapter.FOOTER_PULLING);

                    //获取新数据，url
                    Xhttp.getList(url, tid, new Xhttp.OnSuccessListener() {
                        @Override
                        public void sendList(List<Netease> neteaseNews) {
                            myad.addDataList(neteaseNews);
                            myad.notifyDataSetChanged();
                            if (neteaseNews.size() == 0) {
                                myad.setCurrentState(mainAdapter.FOOTER_PULL_NO_DATA);
                            } else {
                                myad.setCurrentState(mainAdapter.FOOTER_PULL_FINISHED);
                            }
                        }
                    });
                }
        }
    }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handler=new Handler();
        srlMain.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(lis);
        Xhttp.getList(url,tid, listener);

    }
private LinearLayoutManager layoutManager;
    private Xhttp.OnSuccessListener listener = new Xhttp.OnSuccessListener() {
        @Override
        public void sendList(List<Netease> neteases) {
            //适配器工作
            myad = new mainAdapter(neteases);
            myad.setOnItemClickListener(MainActivity.this);
            recyclerView.setAdapter(myad);
            layoutManager=new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            //                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false));
            //            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.addItemDecoration(new RecycleViewDivider(MainActivity.this, LinearLayoutManager.HORIZONTAL));
        }
    };

    @Override
    public void onRefresh() {
        Runnable r=new TimerTask() {
            @Override
            public void run() {
                Netease n = myad.getNeteaseList().get(1);
                myad.addData(1,n);
                myad.notifyItemInserted(1);
                srlMain.setRefreshing(false);
            }
        };

        handler.postDelayed(r,2000);
    }
    public void onClick(int position) {

        //点击事件：
        //   页面跳转传值：
        Intent intent = new Intent(this, NewContentActivity.class);
        //新闻的网址：
        String docId = myad.getNeteaseList().get(position).docid;
        intent.putExtra("docId", docId);
        startActivity(intent);
    }
}
