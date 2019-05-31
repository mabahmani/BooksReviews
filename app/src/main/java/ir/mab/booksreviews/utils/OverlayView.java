package ir.mab.booksreviews.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class OverlayView extends View {

    Paint paint;
    Canvas canvas;
    Context context;

    int currentViewHeight = 0;
    int currentViewWidth = 0;

    RectF overlayRect;

    public OverlayView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {

        this.context = context;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        paint = new Paint();
        paint.setShader(new LinearGradient(
                0,0,0,height,
                Color.parseColor("#AAAD1457"),
                Color.parseColor("#AAF9A825"),
                Shader.TileMode.CLAMP
        ));

        canvas = new Canvas();
        overlayRect = new RectF(0, 0, 0, 0);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        float xLeft,xRight,yTop,yDown;
        xLeft = (currentViewWidth/2.0f) - (currentViewWidth/2.0f) * 0.8f;
        xRight = (currentViewWidth/2.0f) + (currentViewWidth/2.0f) * 0.8f;
        yTop = (currentViewHeight/2.0f) - (currentViewHeight/2.0f) * 0.25f;
        yDown = (currentViewHeight/2.0f) + (currentViewHeight/2.0f) * 0.25f;
        overlayRect.set(xLeft,yTop,xRight,yDown);
        canvas.clipRect(overlayRect, Region.Op.DIFFERENCE);
        canvas.drawPaint(paint);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        currentViewWidth = xNew;
        currentViewHeight = yNew;
    }
}
