/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import edu.wgu.student.tomasgray.capstone.R;

public class ProgressButtonView extends AppCompatButton
{
    private static final String LOG_TAG = "ProgButtonView";


    private int percentage;
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
        progressBar = computeProgressBarDimensions(percentage);
        canvas.drawRect(progressBar, progressBarPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i(LOG_TAG, "Dimensions: w("+w+"), h("+h+"), oldw("+oldw+"+), oldh("+oldh+")");
        // Resize progress bar
        if(progressBar != null){
            Log.i(LOG_TAG, "Setting progress bar to: " + percentage);
            progressBar.set(computeProgressBarDimensions(percentage));
        }
    }

    private void init()
    {
        // Setup paint bucket
        progressBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // Set color
        int color = ContextCompat.getColor(getContext(), R.color.colorSecondary);
        progressBarPaint.setColor(color);
        progressBarPaint.setStyle(Paint.Style.FILL);
    }

    private Rect computeProgressBarDimensions(int percent)
    {
        Log.i(LOG_TAG, "Computing progress Rect for percent: " + percent);
        // Top of progress bar, relative to containing View,
        // minus padding
        int top = getPaddingTop()/2;
        // Height of progress bar, accounting for padding (half)
        int height = getHeight() - getPaddingTop()/2;
        // Left of progress bar, relative to containing View,
        // minus padding
        int left = getPaddingLeft()/2;
        // Percent of the progess bar that is filled
        int progressWidth = (int)(getWidth() * (percent * 0.01f));
        return new Rect(left, top, progressWidth, height);
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        // Force redraw
        this.invalidate();
    }

}