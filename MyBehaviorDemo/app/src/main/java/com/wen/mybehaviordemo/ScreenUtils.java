package com.wen.mybehaviordemo;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author jiahongfei jiahongfeinew@163.com
 * @version V1.0.0
 * @Title: ScreenUtils.java
 * @Package com.android.support.framework.utils
 * @ClassName: ScreenUtils
 * @Description: 和屏幕相关的(屏幕宽高等）
 * @date Nov 5, 2014 2:22:11 PM
 */
public class ScreenUtils {

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return 数组第一个元素宽，第二个元素高
     */
    public static int[] getScreenBounds(Context context) {

        int[] screenBounds = new int[2];

        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        screenBounds[0] = displayMetrics.widthPixels;
        screenBounds[1] = displayMetrics.heightPixels;
        return screenBounds;
    }
}
