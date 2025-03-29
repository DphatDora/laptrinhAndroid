package com.example.baitapasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextParent;
    public MyAsyncTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Toast.makeText(contextParent, "on pre execute", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // không vẽ giao diện trong đây
        for (int i = 0; i < 100; i++) {
            SystemClock.sleep(200);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground
        super.onProgressUpdate(values);
        // Thông qua context cha để lấy được control trong main activity
        ProgressBar progressBar = (ProgressBar) contextParent.findViewById(R.id.prbDemo);
        // vì publicProgress chỉ truyền 1 đối số nên mảng values chỉ có 1 phần tử
        int number = values[0];
        progressBar.setProgress(number); // cập nhật giá trị cho progress bar
        TextView textView = contextParent.findViewById(R.id.txtStatus);
        textView.setText(number + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(contextParent, "Đã hoàn thành", Toast.LENGTH_LONG).show();
    }
}
