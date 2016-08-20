package com.example.administrator.test_asynctask;

import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MProgressBar extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private MyAsyncTask mTask;

    public void init(){
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);

        init();

        mTask = new MyAsyncTask();
        mTask.execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTask!=null &&
                mTask.getStatus()==AsyncTask.Status.RUNNING){
            //cancel方法只是将对应的AsyncTask标记为cancel状态，并不是真正的取消线程的执行。java中也不允许粗暴的停止一个未完成的线程
            mTask.cancel(true);
        }
    }

    class MyAsyncTask extends AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                //用isCancelled来判断
                if (isCancelled()) {
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()) {
                return;
            }
            //获取进度更新值
            mProgressBar.setProgress(values[0]);
        }
    }

}
