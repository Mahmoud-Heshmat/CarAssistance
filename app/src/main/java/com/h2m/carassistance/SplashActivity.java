package com.h2m.carassistance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Calendar;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView logoImage;
    Context context;

    SampleAlarmReceiver alarm1 = new SampleAlarmReceiver();
    SampleAlarmReceiver alarm2 = new SampleAlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        Log.d("responseUsername", sharedPreferences.get_user_name(context));

        logoImage = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_alpha);
        logoImage.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (sharedPreferences.get_user_name(context).equals("0")){
                    startActivity(new Intent(context, LoginActivity.class));
                    setAlarm();
                    finish();
                }else{
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void setAlarm(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        alarm1.setAlarm(this,cal,1);
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 19);
        cal1.set(Calendar.MINUTE, 30);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        alarm2.setAlarm(this,cal1,2);
    }
}
