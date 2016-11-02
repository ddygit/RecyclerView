package com.example.administrator.recylerviewdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recylerviewdemo.R;
import com.example.administrator.recylerviewdemo.entity.Netease;
import com.example.administrator.recylerviewdemo.utils.XImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class ViewPAdapter extends PagerAdapter {
    public ViewPAdapter(List<Netease.Ads> adsList) {
        this.adsList = adsList;
    }

    List<Netease.Ads> adsList;
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v=View.inflate(container.getContext(), R.layout.layout_item_one_head,null);
        ImageView iv= (ImageView) v.findViewById(R.id.img_head);
        TextView tv= (TextView) v.findViewById(R.id.tv_title);
        XImageUtil.display(iv,adsList.get(position%adsList.size()).getImgsrc());
        tv.setText(adsList.get(position%adsList.size()).getTitle());
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
