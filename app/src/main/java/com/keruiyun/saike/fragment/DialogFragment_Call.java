package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintLinearLayout;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.util.OnTouchListenerAddSub;
import com.keruiyun.saike.util.DrawableUtil;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PhoneUtil;
import com.music.musicplayer.utility.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 联系电话
 */

public class DialogFragment_Call extends BaseDialogFragment {
    @BindView(R.id.txt_input)
    TextView txtInput;
    @BindView(R.id.img_input_del)
    ImageView imgInputDel;
    @BindView(R.id.layout_input)
    LinearLayout layoutInput;
    @BindView(R.id.one)
    TintLinearLayout one;
    @BindView(R.id.img_one)
    TintImageView oneImg;
    @BindView(R.id.two)
    TintLinearLayout two;
    @BindView(R.id.three)
    TintLinearLayout three;
    @BindView(R.id.four)
    TintLinearLayout four;
    @BindView(R.id.five)
    TintLinearLayout five;
    @BindView(R.id.six)
    TintLinearLayout six;
    @BindView(R.id.seven)
    TintLinearLayout seven;
    @BindView(R.id.eight)
    TintLinearLayout eight;
    @BindView(R.id.nine)
    TintLinearLayout nine;
    @BindView(R.id.zero)
    TintLinearLayout zero;
    @BindView(R.id.xing)
    Button xing;
    @BindView(R.id.jing)
    Button jing;
    @BindView(R.id.specially_call)
    TintTextView speciallyCall;
    @BindView(R.id.call)
    ImageButton call;
    @BindView(R.id.video_call)
    TintTextView videoCall;
    Unbinder unbinder;


    private StringBuffer callNumBuf;
    private boolean isHandUp=true;

    private String number;
    private boolean rightAwayCall;

    public DialogFragment_Call() {
    }

    @SuppressLint("ValidFragment")
    public DialogFragment_Call(String number) {
        this.number = number;
        rightAwayCall=true;
    }

    @Override
    public int loadContentView() {
        return R.layout.fragment_call;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        callNumBuf = new StringBuffer();
        if (number!=null)
            callNumBuf.append(number);
        LogCus.msg("专呼电话："+callNumBuf.toString());
        txtInput.setText(callNumBuf.toString()+"");
//        View[] views = new View[]{one, two, three, four, five, six, seven, eight, nine, xing, zero, jing, call};
//
//        for (View item : views) {
//            item.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawable(item, 8, false, true, false));
//        }
//        speciallyCall.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawable(speciallyCall,8,false, true, true));
//        videoCall.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawable(videoCall,8,false, true, true));
        oneImg.setImageTintList(R.color.white);
        one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    oneImg.setImageTintList(R.color.theme_color_primary);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    oneImg.setImageTintList(R.color.white);
                }
                return false;
            }
        });

        if (rightAwayCall){
            callPhone();
        }
    }

    @OnClick({R.id.img_input_del,
            R.id.one, R.id.two, R.id.three, R.id.four,
            R.id.five, R.id.six, R.id.seven, R.id.eight,
            R.id.nine, R.id.xing, R.id.zero, R.id.jing,
            R.id.call, R.id.specially_call, R.id.video_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_input_del:
                int lenght = callNumBuf.length();
                if (lenght > 0) {
                    callNumBuf.deleteCharAt(lenght - 1);
                    txtInput.setText(callNumBuf.toString().trim());
                }
                break;
            case R.id.one:
                callNumBuf.append("1");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.two:
                callNumBuf.append("2");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.three:
                callNumBuf.append("3");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.four:
                callNumBuf.append("4");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.five:
                callNumBuf.append("5");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.six:
                callNumBuf.append("6");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.seven:
                callNumBuf.append("7");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.eight:
                callNumBuf.append("8");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.nine:
                callNumBuf.append("9");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.xing:
                callNumBuf.append("*");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.zero:
                callNumBuf.append("0");
                txtInput.setText(callNumBuf.toString().trim());
                break;
            case R.id.jing:
                callNumBuf.append("#");
                txtInput.setText(callNumBuf.toString().trim());
                break;

            case R.id.call:
                callPhone();
                break;
            case R.id.specially_call:
                handUp();
                dismiss();
                break;
            case R.id.video_call:
                handUp();
                new DialogFragment_PhoneBill(true)
                        .show(((BaseActivity) mContext).getSupportFragmentManager(),
                                DialogFragment_PhoneBill.class.getName());
                break;

        }
    }

    private void callPhone(){
        String num=txtInput.getText().toString();
        if (num.length() <= 0&&!isHandUp){
            return;
        }
        if (isHandUp){//通话状态
            quickCall(num);
        }else {
            handUp();
        }
    }

    private void handUp() {
        isHandUp=true;
        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,0);
        txtInput.setText("");
        callNumBuf=new StringBuffer();
        call.setImageResource(R.drawable.sk_lxdh_21);
    }

    private int musicBack=-1;
    @Override
    public void updateSerialData(int addr, short data) {
        super.updateSerialData(addr, data);
        /*switch (addr) {
            case SerialSaunaThread.ADDR_MUSIC_KEY:
                isHandUp=data==0;
                if (data==musicBack){
                    break;
                }else if (call!=null){
                    call.setImageResource(isHandUp?R.drawable.sk_lxdh_15:R.drawable.sk_lxdh_21);
                }

                break;
        }*/
    }

    /*专呼电话*/
    public  void quickCall(String number)
    {
        isHandUp=false;
        call.setImageResource(R.drawable.sk_lxdh_15);

        /* handUp*/
        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,0);
       /* dial*/
        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,1);

        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_PAUSE);
        mContext.sendBroadcast(intent);
        int num=0;

        for (int i = 0; i < number.length(); i++)
        {
            if ('0' == number.charAt(i))
            {
                num=0;
            }
            else if ('1' == number.charAt(i))
            {
                num=1;
            }
            else if ('2' == number.charAt(i))
            {
                num=2;
            }
            else if ('3' == number.charAt(i))
            {
                num=3;
            }
            else if ('4' == number.charAt(i))
            {
                num=4;
            }
            else if ('5' == number.charAt(i))
            {
                num=5;
            }
            else if ('6' == number.charAt(i))
            {
                num=6;
            }
            else if ('7' == number.charAt(i))
            {
                num=7;
            }
            else if ('8' == number.charAt(i))
            {
                num=8;
            }
            else if ('9' == number.charAt(i))
            {
                num=9;
            }
            else if ('*' == number.charAt(i))
            {
                num=10;
            }
            else if ('#' == number.charAt(i))
            {
                num=11;
            }

            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_PHONE_KEY, num);
        }
    }


}
