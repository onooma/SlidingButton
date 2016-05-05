package taxi.tap30.slidingbutton.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import taxi.tap30.slidingbutton.SlidingButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingButton slidingButton = (SlidingButton) findViewById(R.id.slidingButton);
        slidingButton.setOnSlideListener(new SlidingButton.OnSlideListener() {
            @Override
            public void OnSlideStart() {
                Log.e(TAG,"OnSlideStart");
            }

            @Override
            public void OnSlide(float value) {
                Log.e(TAG,"OnSlide");
            }

            @Override
            public void OnSlideCancel() {
                Log.e(TAG,"OnSlideCancel");
            }

            @Override
            public void OnSlideDone() {
                Log.e(TAG,"OnSlideDone");
            }
        });
    }
}
