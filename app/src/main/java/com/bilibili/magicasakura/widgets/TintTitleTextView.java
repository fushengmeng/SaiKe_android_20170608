package com.bilibili.magicasakura.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2018/3/15.
 */

@SuppressLint("AppCompatCustomView")
public class TintTitleTextView extends TextView implements Tintable {
    public TintTitleTextView(Context context) {
        super(context);
    }

    public TintTitleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TintTitleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void tint() {
        int color=ThemeUtils.replaceColorById(getContext(), R.color.theme_color_primary);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        setBackgroundColor(Color.argb((int) (0.3*255),red,green,blue));

    }
}
