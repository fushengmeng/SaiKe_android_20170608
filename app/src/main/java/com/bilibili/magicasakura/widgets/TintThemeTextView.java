package com.bilibili.magicasakura.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2018/3/15.
 */

@SuppressLint("AppCompatCustomView")
public class TintThemeTextView extends TextView implements Tintable {
    public TintThemeTextView(Context context) {
        super(context);
    }

    public TintThemeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TintThemeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void tint() {
        int theme= ThemeUtils.replaceColorById(getContext(), R.color.theme_color_primary);
        setTextColor(theme);
    }
}
