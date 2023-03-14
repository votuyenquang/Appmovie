package vku.vtq.moviesstreamingappclient;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import vku.vtq.moviesstreamingappclient.MainActivity;
import vku.vtq.moviesstreamingappclient.R;

public class NenLoading extends AppCompatActivity {
    TextView tenphienban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nen_loading);
        tenphienban = (TextView) findViewById(R.id.tenphienban);

        try {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NenLoading.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
