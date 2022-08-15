package com.flatcode.simplemultiapps.StopWatch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityStopWatchBinding;

public class StopWatchActivity extends AppCompatActivity {

    private ActivityStopWatchBinding binding;
    Context context = StopWatchActivity.this;

    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityStopWatchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.stop_watch));

        handler = new Handler();

        binding.btStart.setOnClickListener(v -> {
            if (!isResume) {
                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                binding.chronometer.start();
                isResume = true;
                binding.btStop.setVisibility(View.GONE);
                binding.btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            } else {
                tBuff += tMilliSec;
                handler.removeCallbacks(runnable);
                binding.chronometer.stop();
                isResume = false;
                binding.btStop.setVisibility(View.VISIBLE);
                binding.btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            }
        });

        binding.btStop.setOnClickListener(v -> {
            if (!isResume) {
                binding.btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                tMilliSec = 0L;
                tStart = 0L;
                tBuff = 0L;
                tUpdate = 0L;
                sec = 0;
                min = 0;
                milliSec = 0;
                binding.lastTimeDate.setText(binding.chronometer.getText().toString());
                binding.chronometer.setText("00:00:00");
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            binding.chronometer.setText(String.format("%02d", min) + ":"
                    + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
        }
    };
}