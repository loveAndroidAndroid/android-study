package com.rmyh.textviewdemo;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusedTextView extends TextView {

    public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusedTextView(Context context) {
        super(context);
    }

    // 检测有没有焦点 返回true 让TextView认为有焦点
    @Override
    public boolean isFocused() {
        return true;
    }

    // 焦点的改变
    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        // 注意参1 改成true 就算焦点改变 也不会停止跑马灯,因为在此处返回true，text默认都有焦点
        super.onFocusChanged(true, direction, previouslyFocusedRect);
    }

}
