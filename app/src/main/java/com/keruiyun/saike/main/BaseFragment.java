package com.keruiyun.saike.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/16.
 */

public abstract class BaseFragment extends Fragment  {
    public Activity mContext;
    View view;
    Unbinder unbinder;
    public OnFragmentListener onFragmentListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener){
            onFragmentListener= (OnFragmentListener) context;
        }else {
            throw new ClassCastException(context.getClass().getName()+" no implements OnFragmentListener ");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(loadContentView(), container, false);
        unbinder = ButterKnife.bind(this, view);
        // 将myTouchListener注册到分发列表

        initView();
        return view;
    }


    public abstract int loadContentView();

    public void initView(){};



    public <T extends View> T findViewById(int id){
        return (T) view.findViewById(id);
    }

    @Override
    public void onDestroyView() {


        super.onDestroyView();
        unbinder.unbind();
    }


}
