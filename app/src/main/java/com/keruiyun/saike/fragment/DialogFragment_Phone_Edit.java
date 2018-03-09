package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.db.BeanPhone;
import com.keruiyun.db.T_M_PhoneCall;
import com.keruiyun.db.T_M_PhoneVideo;
import com.keruiyun.saike.R;
import com.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 气体状态
 */

@SuppressLint("ValidFragment")
public class DialogFragment_Phone_Edit extends BaseDialogFragment {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.input_call_name)
    EditText inputCallName;
    @BindView(R.id.input_call_ip)
    EditText inputCallIp;
    @BindView(R.id.txt_cancle)
    TintTextView txtCancle;
    @BindView(R.id.txt_sure)
    TintTextView txtSure;
    @BindView(R.id.txt_delete)
    TintTextView txtDelete;

    T_M_PhoneVideo t_m_phoneVideo;
    T_M_PhoneCall t_m_phoneCall;

    private BeanPhone beanPhone;
    private int position;//更新位置
    boolean isVideo;

    @SuppressLint("ValidFragment")
    public DialogFragment_Phone_Edit( boolean isVideo,int type, int position,BeanPhone beanPhone) {
        this.isVideo=isVideo;
        this.beanPhone = beanPhone;
        this.position = position;
        this.type = type;
    }

    /**
     * 0---add;
     * 1----update;
     * 2----delete
     * */
    private int type;



    @Override
    public int loadContentView() {
        return R.layout.layout_phone_add;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        if (isVideo)
            t_m_phoneVideo=new T_M_PhoneVideo(mContext);
        else
            t_m_phoneCall=new T_M_PhoneCall(mContext);
        if (type!=0){
            inputCallName.setText(beanPhone.getName()+"");
            inputCallIp.setText(beanPhone.getIp()+"");
        }
        if (type==1){
            txtTitle.setText(mContext.getResources().getString(R.string.edit));
        }

        if (type==2) {
            inputCallName.setKeyListener(null);
            inputCallIp.setKeyListener(null);
            txtTitle.setText(mContext.getResources().getString(R.string.delete));
            txtSure.setText(mContext.getResources().getString(R.string.delete));
        }
    }

    @Override
    public void onDestroy() {
        if (t_m_phoneVideo!=null)
            t_m_phoneVideo.close();
        if (t_m_phoneCall!=null)
            t_m_phoneCall.close();
        super.onDestroy();
    }

    @OnClick({R.id.txt_cancle, R.id.txt_sure, R.id.txt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_cancle:
                dismiss();
                break;
            case R.id.txt_sure:
                editCall();
                break;
            case R.id.txt_delete:
                type=2;
                editCall();
                break;
        }
    }

    private void editCall(){
        if (type==2){
           boolean result ;
           if (isVideo)
               result = t_m_phoneVideo.delete(beanPhone.getId());
           else
               result = t_m_phoneCall.delete(beanPhone.getId());
            String msg;
           if (result){
               msg=mContext.getResources().getString(R.string.msg_delete_success);
               if (onAddResultListener!=null)
                   onAddResultListener.onAddResult(type,position,beanPhone);
               dismiss();
           }else {
               msg=mContext.getResources().getString(R.string.msg_delete_fail);
           }
           ToastUtil.showToast(msg);
           return;
        }
        if (beanPhone==null)
            beanPhone=new BeanPhone();
        String name=inputCallName.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast(mContext.getResources().getString(R.string.msg_name_null));
            return;
        }else {
            beanPhone.setName(name);
        }
        String ip=inputCallIp.getText().toString().trim();
        if (TextUtils.isEmpty(ip)){
            ToastUtil.showToast(mContext.getResources().getString(R.string.msg_ip_null));
            return;
        }else {
            beanPhone.setIp(ip);
        }

        if (type==1){
            boolean result;
            if (isVideo)
                result = t_m_phoneVideo.update(beanPhone.getId(), beanPhone);
            else
                result = t_m_phoneCall.update(beanPhone.getId(), beanPhone);

            String msg;
            if (result){
                msg=mContext.getResources().getString(R.string.msg_update_success);
                if (onAddResultListener!=null)
                    onAddResultListener.onAddResult(type,position,beanPhone);
                dismiss();
            }else {
                msg=mContext.getResources().getString(R.string.msg_update_fail);
            }
            ToastUtil.showToast(msg);
        }else {
            BeanPhone result ;
            if (isVideo)
                result = t_m_phoneVideo.insertUser(name, ip);
            else
                result = t_m_phoneCall.insertUser(name, ip);
            if (result!=null){
                String msg=mContext.getResources().getString(R.string.msg_add_success);
                ToastUtil.showToast(msg);
                if (onAddResultListener!=null)
                    onAddResultListener.onAddResult(type,position,result);
                dismiss();
            }else {
                String msg=mContext.getResources().getString(R.string.msg_add_fail);
                ToastUtil.showToast(msg);
            }
        }



    }

    OnAddResultListener onAddResultListener;

    public DialogFragment_Phone_Edit setOnAddResultListener(OnAddResultListener onAddResultListener) {
        this.onAddResultListener = onAddResultListener;
        return this;
    }

    public interface OnAddResultListener{
        public void onAddResult(int type,int position, BeanPhone result);
    }
}
