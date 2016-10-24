package com.example.administrator.recylerviewdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;

    public MyBaseAdapter(List<T> list) {
        super();
        this.list = list;
    }
    public MyBaseAdapter() {
        super();
        list = new ArrayList<T>();
    }

    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list2) {
        this.list = list2;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list==null?0:list.size();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //添加数据
    public  void addData(T t){
        list.add(t);
    }
    //添加集合
    public  void addAllData(List<T> list2){
        list.addAll(list2);
    }
    //	删除数据
    public void removeData(T t){
        list.remove(t);
    }
    //	删除集合
    public void removeAllData(List<T> list2){
        list.removeAll(list2);
    }
    //	清空
    public void clear(){
        list.clear();
    }
    public  View getView(int position, View convertView, ViewGroup parent){
        BaseHolder holder=null;
        if (convertView==null){
            holder=getHolder(parent.getContext());
            //            convertView=holder.itemView;
        }else{
            holder= (BaseHolder) convertView.getTag();
        }

        holder.setData(getItem(position));
        return holder.itemView;
    }

    protected abstract BaseHolder getHolder(Context context);

    public abstract class BaseHolder{
        protected View itemView;
        public BaseHolder(View itemView2){
            itemView=itemView2;
            itemView.setTag(this);
            ButterKnife.bind(this, itemView2);
        }

        public abstract void setData(T item);
    }

}


