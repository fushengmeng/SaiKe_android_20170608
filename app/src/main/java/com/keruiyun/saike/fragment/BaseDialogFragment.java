package com.keruiyun.saike.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.WrapperListAdapter;

import com.bilibili.magicasakura.utils.TintManager;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.Tintable;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class BaseDialogFragment extends DialogFragment implements OnDialogFragmentListener {
    public Context mContext;
    OnDialogFragmentListener onDialogFragmentListener;
    public ImageView albumArt;
    public TintImageView tintImageView;
    private View view;
    Unbinder unbinder;
    FrameLayout containerMain,containerTop,containerBottom;

    public boolean isDialogShow=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
        mContext=getActivity();
        if (mContext instanceof BaseActivity){
            ((BaseActivity)mContext).setThemeListener(this);
        }
    }

    public void setOnDialogFragmentListener(OnDialogFragmentListener onDialogFragmentListener) {
        this.onDialogFragmentListener = onDialogFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 去掉留白的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.layout_base_dialog, container, false);
        albumArt=findById(frameLayout,R.id.albumArt);
        tintImageView= (TintImageView) frameLayout.findViewById(R.id.tintimg);
        int bgId=loadTintImage();
        if (bgId!=0)
            tintImageView.setBackgroundResource(bgId);
        if (isTintList())
            tintImageView.setBackgroundTintList(R.color.theme_color_primary);


        int bottomLayout=loadContentBottomView();
        if (bottomLayout!=0){
            containerBottom = (FrameLayout) frameLayout.findViewById(R.id.layout_bottom);
            View viewBottom = inflater.inflate(bottomLayout, containerBottom,true);
            initBottomView(viewBottom);
        }
        int topLayout=loadContentTopView();

        if (topLayout!=0){
            containerTop = (FrameLayout) frameLayout.findViewById(R.id.layout_top);
            View viewTop= inflater.inflate(topLayout, containerTop,true);
            initTopView(viewTop);
        }

        containerMain = (FrameLayout) frameLayout.findViewById(R.id.layout_container);
        view = inflater.inflate(loadContentView(), containerMain,true);

       /* view.post(new Runnable() {
            @Override
            public void run() {
                LogCus.msg("标题宽度："+view.getWidth());


            }
        });*/
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        isDialogShow=true;
        return frameLayout;
    }

    public boolean isTintList(){
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
//        setDimAmount();
        int[] widthHeight = setWidthHeight();
        if (widthHeight[0]>0&&widthHeight[1]>0){
            getDialog().getWindow().setLayout(widthHeight[0],widthHeight[1]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isDialogShow=true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isDialogShow=false;
    }

    public int[] setWidthHeight(){
        return new int[2];
    }
    private void setDimAmount(){
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.9f;
        window.setAttributes(windowParams);
    }

    @Override
    public void onActivityResultListener(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int loadTintImage(){
        return R.drawable.sk_bg_dialog;
    }

    public abstract int loadContentView();

    public  int loadContentBottomView(){
        return 0;
    };

    public  int loadContentTopView(){
        return 0;
    };


    public void initView( View view){};

    public void initBottomView(View bottomView){};

    public void initTopView(View topView){};

    public <T extends View> T findById(int id){
        return (T)view.findViewById(id);
    }

    public <T extends View> T findById(View view,int id){
        return (T)view.findViewById(id);
    }

    @Override
    public void onDialogFragmentRefreshThemeListener() {
        TintManager.clearTintCache();
        tintImageView.destroyDrawingCache();
        tintImageView.tint();
        refreshTheme(view);
    }

    private void refreshTheme(View view){
        TintManager.clearTintCache();
        if (view == null) return;

        view.destroyDrawingCache();
        if (view instanceof Tintable) {
            ((Tintable) view).tint();
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    refreshTheme(((ViewGroup) view).getChildAt(i));
                }
            }
        } else {
            if (view instanceof AbsListView) {
                ListAdapter adapter = ((AbsListView) view).getAdapter();
                while (adapter instanceof WrapperListAdapter) {
                    adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
                }
                if (adapter instanceof BaseAdapter) {
                    ((BaseAdapter) adapter).notifyDataSetChanged();
                }
            }

            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    refreshTheme(((ViewGroup) view).getChildAt(i));
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (onDialogFragmentListener!=null)
            onDialogFragmentListener.onDialogFragmentDismissed();
        unbinder.unbind();
        if (mContext instanceof BaseActivity){
            ((BaseActivity)mContext).removeThemeListener(this);
        }
    }

    public interface OnDialogFragmentListener{
        public void onDialogFragmentDismissed();
    }

    public void updateSerialData(int addr,short data){
        if (!isDialogShow)
            return;
    }

}
