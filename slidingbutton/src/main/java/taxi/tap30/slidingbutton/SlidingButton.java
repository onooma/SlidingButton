package taxi.tap30.slidingbutton;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by onooma on 2/4/16.
 */
public class SlidingButton extends RelativeLayout
        implements View.OnTouchListener{
    private static final String TAG = "SlidingButton";
    private StateValue stateValue;

    private float slideStart;
    private float max;
    private Typeface typeface;

    private View rootView;
    private TextView textView;
    private ImageView circleImageView;

    private OnSlideListener onSlideListener;

    private enum State {IDLE, LOADING, DONE, DISABLE}

    private State state = State.IDLE;

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        if(textView != null)
            textView.setTypeface(typeface);
    }

    public void clear() {
        state = State.IDLE;
        rootView.setX(0f);
        textView.setText(stateValue.getText());
        textView.setAlpha(1f);
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }

    public SlidingButton(Context context) {
        super(context);
        init(context);
    }

    public SlidingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sliding_button, this, true);
        rootView = findViewById(R.id.rootView);
        textView = (TextView) findViewById(R.id.textView);
        circleImageView = (ImageView) findViewById(R.id.circleImageView);

        textView.setTypeface(typeface);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(onSlideListener == null || state != State.IDLE )
            return true;
        float diff;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                slideStart = event.getX();
                max = rootView.getWidth() - circleImageView.getWidth() - circleImageView.getPaddingRight();
                updateStart();
                break;
            case MotionEvent.ACTION_MOVE:
                diff = event.getX() - slideStart;
                if(diff > 0){
                    float fraction = diff / max;
                    if(fraction >= 1f) {
                        updateDone();
                        return false;
                    }
                    update(fraction);
                }
                break;
            case MotionEvent.ACTION_UP:
                diff = event.getX() - slideStart;
                if(diff >= max){
                    updateDone();
                }else{
                    updateCancel();
                }
                break;
        }
        return true;
    }

    private void updateStart(){
        if(onSlideListener != null)
            onSlideListener.OnSlideStart();
    }

    private void updateDone(){
        circleImageView.setX(max);
        textView.setAlpha(1f);
        if(onSlideListener != null)
            onSlideListener.OnSlideDone();
        loading();
    }

    private void updateCancel(){
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(circleImageView, "translationX", circleImageView.getX(), 0f);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(textView, "alpha", textView.getAlpha(), 1f);

        animatorSet.playTogether(positionAnimator, alphaAnimator);
        animatorSet.setDuration(250);
        animatorSet.start();
        if(onSlideListener != null)
            onSlideListener.OnSlideCancel();
    }

    private void update(float fraction){
        circleImageView.setX(fraction * max);
        if(fraction <= 0.5f){
            textView.setAlpha(1f - fraction * 2f);
        }else{
            textView.setAlpha(0f);
        }
        if(onSlideListener != null)
            onSlideListener.OnSlide(fraction);
    }

    private void loading(){
        state = State.LOADING;
    }

    public interface OnSlideListener {
        void OnSlideStart();
        void OnSlide(float value);
        void OnSlideCancel();
        void OnSlideDone();
    }

}
