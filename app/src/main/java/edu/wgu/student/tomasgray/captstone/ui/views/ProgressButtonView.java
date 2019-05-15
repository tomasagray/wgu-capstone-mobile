package edu.wgu.student.tomasgray.captstone.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import edu.wgu.student.tomasgray.captstone.R;

public class ProgressButtonView extends AppCompatButton
{
    private static final String LOG_TAG = "ProgButtonView";


    private float percentage = 0.4f;
    private Paint progressBarPaint;
    private Rect progressBar;

    public ProgressButtonView(Context context) {
        super(context);
    }

    public ProgressButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.drawRect(progressBar, progressBarPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i(LOG_TAG, "Dimensions: w("+w+"), h("+h+"), oldw("+oldw+"+), oldh("+oldh+")");
        // Resize progress bar
        if(progressBar != null)
            // TODO: Fix this
            progressBar.set(computeProgressBarDimensions(60));
    }

    private void init()
    {
//        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        // Setup paint bucket
        progressBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // Set color
        int color = ContextCompat.getColor(getContext(), R.color.colorSecondary);
        progressBarPaint.setColor(color);
        progressBarPaint.setStyle(Paint.Style.FILL);
        // Default (zeroed) progress bar
        // TODO: Fix this
        progressBar = computeProgressBarDimensions(30);
    }

    private Rect computeProgressBarDimensions(int percent)
    {
        // Top of progress bar, relative to containing View,
        // minus padding
        int top = getPaddingTop();
        // Height of progress bar, accounting for padding (half)
        int height = getHeight() - getPaddingTop()*2;
        // Left of progress bar, relative to containing View,
        // minus padding
        int left = getPaddingLeft();
        // Percent of the progess bar that is filled
        int progressWidth = (int)(getWidth() * (percent * 0.01f));

        return new Rect(left, top, progressWidth, height);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

}