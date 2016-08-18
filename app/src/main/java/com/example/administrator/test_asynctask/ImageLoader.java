package com.example.administrator.test_asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
public class ImageLoader extends AppCompatActivity {
    private MyAsyncTask mTask;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String URL
            ="https://gss1.bdstatic.com/5eN1dDebRNRTm2_p8IuM_a/res/r/image/2016-08-16/3a2c7a2b2edb77eadd10a0d0ea4a2e4d.png";
//    private Context mContext;

    private void init() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

//        mContext=this;

        init();
        //设置传递进去的参数
        mTask =new MyAsyncTask();
        mTask.execute(URL);
    }

    public class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{
        //初始化操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        //最终结果，操作UI
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //从可变长数组params[]中取出参数值，
            String url = params[0];

            Bitmap bitmap = null;
            URLConnection coon;
            InputStream is;
            try {
                coon = new URL(url).openConnection();
                is = coon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                //慢点加载
                    Thread.sleep(3000);
                //通过decodeStream解析输入流
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap ;
        }
    }


}
