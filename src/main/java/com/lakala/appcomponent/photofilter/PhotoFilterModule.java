package com.lakala.appcomponent.photofilter;

import android.app.Activity;
import android.content.Context;

import com.lakala.appcomponent.photofilter.engine.impl.Glide4Engine;
import com.lakala.appcomponent.photofilter.listener.OnGetPathListListener;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PhotoFilterModule extends WXModule implements IPhotoFilter {

    @JSMethod
    @Override
    public boolean photoFilter(String params, final JSCallback callback) {
        Context context = mWXSDKInstance.getContext();

        int count = 1;

        try {
            JSONObject jsonObject = new JSONObject(params);
            if (jsonObject.has("maxSelectCount")) {
                count = jsonObject.getInt("maxSelectCount");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (context instanceof Activity) {
            PhotoFilterManager.from((Activity) context)
                    .choose(MimeType.ofImage())//显示类型
                    .countable(false) //选中是否显示数字
                    .capture(true) //相机
                    .maxSelectable(count) //最大选择多少张
                    .spanCount(4) //相册一行显示几张
                    .imageEngine(new Glide4Engine())    //使用Glide4作为图片加载引擎
                    .setFilter(true)//开启滤镜
                    .setOnGetPathListListener(new OnGetPathListListener() {
                        @Override
                        public void OnGetPathList(List<String> pathList) {
                            callback.invoke(pathList);
                        }
                    })
                    .go();

            return true;
        } else {
            return false;
        }
    }
}
