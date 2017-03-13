package com.mcw.demo.ui.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/8
 */
public class SideBar extends View {


    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;


    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };

    private int choose = -1;
    private Paint paint = new Paint();
    private TextView textView;
    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得当前字母
        int height = getHeight();
        int width = getWidth();
        int charHeight = height/b.length;//每个字母高度

        for(int i  = 0 ; i < b.length ;i++){
            paint.setColor(Color.parseColor("#ff33c8ef"));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(20);
            //选中
            if(i == choose){

                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }

            float xPos = width/2 - paint.measureText(b[i])/2;
            float yPos = charHeight*i + charHeight;
            canvas.drawText(b[i],xPos,yPos,paint);
            paint.reset();//重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final  int oldChoose = choose;
        final int c = (int)(y/getHeight()*b.length);
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if(textView != null){
                    textView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                //setBackgroundResource();
                if(oldChoose != c){
                    if(c >= 0 && c < b.length){
                        if(listener!=null){

                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if(textView!=null){
                            textView.setText(b[c]);
                            textView.setVisibility(View.VISIBLE);
                        }
                        choose = c ;
                        invalidate();
                    }

                }
                break;

        }
        return true;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    /**
     * 对外接口
     * @param onTouchingLetterChangedListener
     */
    public void  setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){

        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
    /**
     * 接口
     */
    public interface  OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);

    }
}
