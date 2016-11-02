package com.example.administrator.recylerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewContentActivity extends AppCompatActivity {
    @BindView(R.id.textView1)
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_content);
        ButterKnife.bind(this);
        mTextView.setText(getIntent().getStringExtra("docId"));
    }




}
