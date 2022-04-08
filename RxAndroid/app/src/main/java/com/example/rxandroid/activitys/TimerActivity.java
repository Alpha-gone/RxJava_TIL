package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityTimerBinding;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private ActivityTimerBinding activity_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_timer = DataBindingUtil.setContentView(this, R.layout.activity_timer);

        initCountDownTimer();
        initHandler();

        activity_timer.btnTimer.setOnClickListener(view -> {
            stop();
            timerStart();
        });

        activity_timer.btnCountDownTimer.setOnClickListener(view -> {
            stop();
            countDownTimerStart();
        });

        activity_timer.btnHandler.setOnClickListener(view -> {
            stop();
            handlerStart();
        });
    }

    private int DELAY = 0;
    private int PERIOD = 1000;
    private int count;

    private Timer timer;

    private void timerStart(){
        count = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity_timer.tvView.setText(String.valueOf(count++));
            }
        }, DELAY, PERIOD);
    }

    private void timerStop(){
        if(timer != null){
            timer.cancel();
        }
    }

    private final static int MILLISINFUTURE = 11 * 1000;
    private final static int COUNT_DOWN_INTERVAL = 1000;

    private CountDownTimer countDownTimer;

    private void initCountDownTimer(){
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long l) {
                activity_timer.tvView.setText(String.valueOf(count--));
            }

            @Override
            public void onFinish() {
                activity_timer.tvView.setText("finish");
            }
        };
    }

    private void countDownTimerStart(){
        count = 10;
        countDownTimer.start();
    }

    private void countDownTimerStop(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    private Handler handler;

    private Runnable runnabletimer = new Runnable() {
        @Override
        public void run() {
            activity_timer.tvView.setText(String.valueOf(count++));
            handler.postDelayed(this, 1000);
        }
    };

    private void initHandler(){
        handler = new Handler();
    }

    private void handlerStart(){
        count = 0;
        handler.postDelayed(runnabletimer, 1000);
    }

    private void handlerStop(){
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void stop(){
        timerStop();
        countDownTimerStop();
        handlerStop();
    }
}