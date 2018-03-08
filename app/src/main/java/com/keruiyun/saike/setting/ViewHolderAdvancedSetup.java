package com.keruiyun.saike.setting;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeHelper;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.DialogFragment_Sure_Quit;

import com.keruiyun.saike.setting.dialog.DialogFragment_TimeZone;
import com.keruiyun.saike.setting.dialog.DialogFragment_standbyTime;
import com.keruiyun.saike.setting.dialog.DialogFragment_sure;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----高级设置
 */

public class ViewHolderAdvancedSetup extends BaseViewHolder {

    @BindView(R.id.setting_stand_by_time)
    TextView settingStandByTime;
    @BindView(R.id.layout_stand_by_time)
    LinearLayout layoutStandByTime;
    @BindView(R.id.setting_timezone)
    TextView settingTimezone;
    @BindView(R.id.layout_timezone)
    LinearLayout layoutTimezone;
    @BindView(R.id.layout_factory_reset)
    LinearLayout layoutFactoryReset;
    @BindView(R.id.layout_system_reset)
    LinearLayout layoutSystemReset;
    @BindView(R.id.layout_system_quit)
    LinearLayout layoutSystemQuit;
    @BindView(R.id.txt_theme)
    TextView txtTheme;
    @BindView(R.id.img_theme_black)
    TintImageView imgThemeBlack;
    @BindView(R.id.img_theme_cyan)
    TintImageView imgThemeCyan;
    @BindView(R.id.img_theme_green)
    TintImageView imgThemeGreen;
    @BindView(R.id.img_theme_pink)
    TintImageView imgThemePink;
    @BindView(R.id.img_theme_yellow)
    TintImageView imgThemeYellow;

    private int mCurrentTheme;
    OnSettingListener onSettingListener;

    public ViewHolderAdvancedSetup(Context context, ViewGroup viewParent,OnSettingListener onSettingListener) {
        super(context, viewParent,onSettingListener);
        this.onSettingListener=onSettingListener;
    }

    @Override
    public boolean isAuthValid() {
        return false;
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_advanced;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        mCurrentTheme = ThemeHelper.getTheme(context);
    }

    private void setImageButtons(int currentTheme) {
        imgThemeBlack.setSelected(currentTheme == ThemeHelper.THEME_BLACK);
        imgThemeCyan.setSelected(currentTheme == ThemeHelper.THEME_CYAN);
        imgThemeGreen.setSelected(currentTheme == ThemeHelper.THEME_GREEN);
        imgThemePink.setSelected(currentTheme == ThemeHelper.THEME_PINK);
        imgThemeYellow.setSelected(currentTheme == ThemeHelper.THEME_YELLOW);
//        mCards[5].setSelected(currentTheme == ThemeHelper.CARD_THUNDER);
//        mCards[6].setSelected(currentTheme == ThemeHelper.CARD_SAND);
//        mCards[7].setSelected(currentTheme == ThemeHelper.CARD_FIREY);
    }

    @OnClick({R.id.layout_stand_by_time, R.id.layout_timezone,
            R.id.layout_factory_reset, R.id.layout_system_reset,  R.id.layout_system_quit,
            R.id.txt_theme,
            R.id.img_theme_black, R.id.img_theme_cyan, R.id.img_theme_green, R.id.img_theme_pink, R.id.img_theme_yellow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_stand_by_time:
                new DialogFragment_standbyTime().show(
                        ((BaseActivity)context).getSupportFragmentManager(),
                        DialogFragment_standbyTime.class.getName()
                );
                break;
            case R.id.layout_timezone:
                new DialogFragment_TimeZone()
                        .show(((BaseActivity)context).getSupportFragmentManager()
                                ,DialogFragment_TimeZone.class.getName());
                break;
            case R.id.layout_factory_reset:
                new DialogFragment_sure(1)
                        .show(((BaseActivity)context).getSupportFragmentManager()
                                ,DialogFragment_sure.class.getName());
                break;
            case R.id.layout_system_reset:
                new DialogFragment_sure(2)
                        .show(((BaseActivity)context).getSupportFragmentManager()
                                ,DialogFragment_sure.class.getName());
                break;
            case R.id.layout_system_quit:
                /*new DialogFragment_Sure_Quit()
                        .show(((BaseActivity)context).getSupportFragmentManager()
                            ,DialogFragment_Sure_Quit.class.getName());*/
                new DialogFragment_sure(3)
                        .show(((BaseActivity)context).getSupportFragmentManager()
                                ,DialogFragment_sure.class.getName());
                break;
            case R.id.txt_theme:
                settingVaild();
                break;
            case R.id.img_theme_black:
                mCurrentTheme = ThemeHelper.THEME_BLACK;
                okTheme();

                break;
            case R.id.img_theme_cyan:
                mCurrentTheme = ThemeHelper.THEME_CYAN;
                okTheme();

                break;
            case R.id.img_theme_green:
                mCurrentTheme = ThemeHelper.THEME_GREEN;
                okTheme();
                break;
            case R.id.img_theme_pink:
                mCurrentTheme = ThemeHelper.THEME_PINK;
                okTheme();
                break;
            case R.id.img_theme_yellow:
                mCurrentTheme = ThemeHelper.THEME_YELLOW;
                okTheme();
                break;
        }
    }

    private void okTheme(){
        setImageButtons(mCurrentTheme);
//        ThemeHelper.setTheme(context, mCurrentTheme);

        if (context instanceof  ClickListener){
            ClickListener mClickListener= (ClickListener) context;
            mClickListener.onConfirm(mCurrentTheme);
        }
    }

    /**
     * 连接五次，进行有效期设置
     * */
    // 数组长度代表点击次数
    long[] mHits = new long[5];
    public void settingVaild(){
        // 数组依次先前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();// 开机后运行时间
        if (mHits[0] >= (mHits[mHits.length - 1] - 5000)) {
            onSettingListener.onSettingVaild(1);
        }
    }


    public interface ClickListener {
        void onConfirm(int currentTheme);
    }
}
