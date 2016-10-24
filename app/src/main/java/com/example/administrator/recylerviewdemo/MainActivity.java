package com.example.administrator.recylerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.recylerviewdemo.adapter.mainAdapter;
import com.example.administrator.recylerviewdemo.biz.Xhttp;
import com.example.administrator.recylerviewdemo.entity.Netease;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    String url="http://c.m.163.com/nc/article/list/T1348647909107/0-20.html";
    mainAdapter myad;
    private Xhttp.OnSuccessListener listener=new Xhttp.OnSuccessListener() {
        @Override
        public void sendList(List<Netease> neteases) {
            //适配器工作
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myad=new mainAdapter();
        Xhttp.getList(url, "T1348647909107", listener);

    }
}
