package com.bilibili.magicasakura.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2018/3/15.
 */

@SuppressLint("AppCompatCustomView")
public class TintSelectTextView extends TextView implements Tintable {
    public TintSelectTextView(Context context) {
        super(context);
    }

    public TintSelectTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TintSelectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        tint();
    }

    @Override
    public void tint() {
        if (isSelected()){
            int theme= ThemeUtils.replaceColorById(getContext(), R.color.theme_color_primary);
            setTextColor(theme);
        }else {
            setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        }

    }
}
