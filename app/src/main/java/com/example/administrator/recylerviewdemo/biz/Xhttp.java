package com.example.administrator.recylerviewdemo.biz;

import android.util.Log;

import com.example.administrator.recylerviewdemo.entity.Netease;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class Xhttp {
    public static void getList(String uri, final String tid, final OnSuccessListener listener) {
        RequestParams entity = new RequestParams(uri);


        Callback.CommonCallback<String> callBack = new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                List<Netease> neteases = new ArrayList<>();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray jsonArray = jsonObj.getJSONArray(tid);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Netease netease = gson.fromJson(jsonArray.get(i).toString(), Netease.class);
                        neteases.add(netease);
                    }
                    if (listener != null) {
                        listener.sendList(neteases);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Xhttp","OnSuccess");
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("Xhttp","onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("Xhttp","onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d("Xhttp","onFinished");
            }
        };
        x.http().get(entity, callBack);
    }

    public interface OnSuccessListener {
        void sendList(List<Netease> neteases);

    }
}
