package com.keruiyun.saike.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.saike.R;
import com.keruiyun.saike.controls.MyListView;
import com.keruiyun.saike.setting.BaseViewHolder;
import com.keruiyun.saike.setting.OnSettingListener;
import com.keruiyun.saike.setting.ViewHolderAbout;
import com.keruiyun.saike.setting.ViewHolderAdvancedSetup;
import com.keruiyun.saike.setting.ViewHolderAirSystem;
import com.keruiyun.saike.setting.ViewHolderBluetooth;
import com.keruiyun.saike.setting.ViewHolderLanguage;
import com.keruiyun.saike.setting.ViewHolderSmartstart;
import com.keruiyun.saike.setting.ViewHolderUser;
import com.keruiyun.saike.setting.ViewHolderValiduntil;
import com.keruiyun.saike.setting.ViewHolderWifi;
import com.keruiyun.saike.setting.data.Data_validnutil;
import com.keruiyun.saike.timerserver.ServiceFiveTimer;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;
import com.util.SystemBrightManager;
import com.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.keruiyun.saike.timerserver.ServiceFiveTimer.TIME_logout;

/**
 * 系统设置
 */

public class DialogFragment_Setting extends BaseDialogFragment implements OnSettingListener {



    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.layout_right)
    FrameLayout layoutRight;
    @BindView(R.id.view_right_valid)
    View vRightValid;
    @BindView(R.id.img_close)
    ImageView imgClose;

    TintImageView iconIntensitySub;
    SeekBar seekIntensity;
    TintImageView iconIntensityAdd;

    List<ItemSetting> listData;
    SettingAdapter settingAdapter;
    private boolean isAutoSystemIntensity = false;
    private int defalutValue = 75;
    BaseViewHolder[] viewHolderSettings=new BaseViewHolder[8];

    private int curPosition;
    private boolean isLogin;

    @Override
    public int loadContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    public int loadContentBottomView() {
        return R.layout.item_intensity;
    }

    @Override
    public void onActivityResultListener(int requestCode, int resultCode, Intent data) {
        super.onActivityResultListener(requestCode, resultCode, data);
        for (BaseViewHolder item:viewHolderSettings){
            if (item!=null)
                item.onActivityResultViewHolder(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDestroy() {

        for (BaseViewHolder item:viewHolderSettings){
            if (item!=null)
                item.onDestory();
        }
        super.onDestroy();
        unRegisterFiveRevceiver();

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        isLogin=PreferencesUtil.getInstance(mContext).getBooleanValue("User", "login",false);
        if (isLogin){
            long login_time=  PreferencesUtil.getInstance(mContext).getLongValue("User", "login_time",0)/1000;
            long cur=System.currentTimeMillis()/1000;
            if ((cur-login_time)>TIME_logout*60){
                isLogin=false;
                PreferencesUtil.getInstance(mContext).setBooleanValue("User", "login",false);
            }
        }
        LogCus.msg("DialogFragment_Setting账户isLogin："+isLogin);
        initData();
        if (isLogin) {
            curPosition=0;
            viewHolderSettings[0] = new ViewHolderSmartstart(mContext, layoutRight, DialogFragment_Setting.this);
        }else {
            curPosition=4;
            viewHolderSettings[4] = new ViewHolderUser(mContext, layoutRight, DialogFragment_Setting.this);
        }
        isAutoSystemIntensity = SystemBrightManager.isAutoBrightness(getActivity());
        defalutValue = SystemBrightManager.getBrightness(getActivity());
        seekIntensity.setProgress(defalutValue);
//        iconIntensitySub.setImageTintList(R.color.theme_color_primary);
//        iconIntensityAdd.setImageTintList(R.color.theme_color_primary);
        seekIntensity.setOnSeekBarChangeListener(seekBarChange);
        settingAdapter = new SettingAdapter();
        listview.setAdapter(settingAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition=position;
                settingAdapter.refreshCheck();

                if (viewHolderSettings[position]!=null) {
                    viewHolderSettings[position].refresh();
                }else {
                    switch (position) {
//                        case 1:
//                            viewHolderSettings[position] = new ViewHolderValiduntil(mContext, layoutRight);
//                            break;
                        case 0://智能启动
                            viewHolderSettings[position] = new ViewHolderSmartstart(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 1:
                            viewHolderSettings[position] = new ViewHolderAirSystem(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 2:
                            viewHolderSettings[position] = new ViewHolderWifi(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 3:
                            viewHolderSettings[position] = new ViewHolderBluetooth(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 4:
                            viewHolderSettings[position] = new ViewHolderUser(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 5:
                            viewHolderSettings[position] = new ViewHolderLanguage(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 6:
                            viewHolderSettings[position] = new ViewHolderAdvancedSetup(mContext, layoutRight,DialogFragment_Setting.this);
                            break;
                        case 7:
                            viewHolderSettings[position] = new ViewHolderAbout(mContext, layoutRight,DialogFragment_Setting.this);
                            break;

                    }

                }

                if (position==7){
                    settingVaild();
                }
            }
        });
        registerFiveRevceiver();
    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        iconIntensitySub= (TintImageView) bottomView.findViewById(R.id.icon_intensity_sub);
        seekIntensity= (SeekBar) bottomView.findViewById(R.id.seek_intensity);
        iconIntensityAdd= (TintImageView) bottomView.findViewById(R.id.icon_intensity_add);

        iconIntensitySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekIntensity.setProgress(defalutValue - 1);
            }
        });
        iconIntensityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekIntensity.setProgress(defalutValue + 1);
            }
        });
    }

    private void initData() {
        int[] icon = new int[]{
               /* R.drawable.sk_xtsz_05,*/ R.drawable.sk_xtsz_10, R.drawable.sk_xtsz_17, R.drawable.sk_xtsz_26,
                R.drawable.sk_xtsz_32, R.drawable.sk_xtsz_37, R.drawable.sk_xtsz_42, R.drawable.sk_xtsz_47,
                R.drawable.sk_xtsz_53};
        String[] names = getResources().getStringArray(R.array.arr_setting);
        listData = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            listData.add(new ItemSetting(icon[i], names[i]));
        }

    }




    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;

        }
    }

    private SeekBar.OnSeekBarChangeListener seekBarChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            setScreenLight(progress);
        }
    };

    public void setScreenLight(int progress) {
        if (defalutValue == progress)
            return;
        if (progress < 1) {
            progress = 1;
        } else if (progress > 255) {
            progress = 255;
        }
        if (isAutoSystemIntensity) {
            SystemBrightManager.stopAutoBrightness(getActivity());
        }
        SystemBrightManager.setBrightness(getActivity(), progress);
        defalutValue = progress;
    }

    ViewHolderValiduntil validHolder;

    @Override
    public void onLogin(String user) {
        PreferencesUtil.getInstance(mContext).setStringValue("User", "login_user",user);
        PreferencesUtil.getInstance(mContext).setLongValue("User", "login_user_time",System.currentTimeMillis());
        PreferencesUtil.getInstance(mContext).setBooleanValue("User", "login",true);
        isLogin=true;
        settingAdapter.notifyDataSetChanged();
        onAuthVaild(false);
        setAlarmLogin();
        if (viewHolderSettings[4]!=null){
            ViewHolderUser viewHolderUsers = (ViewHolderUser) viewHolderSettings[4];
            viewHolderUsers.refresh();
        }
    }

    @Override
    public void onLogout() {
        PreferencesUtil.getInstance(mContext).setStringValue("User", "login_user","");
        PreferencesUtil.getInstance(mContext).setBooleanValue("User", "login",false);
        isLogin=false;
        if (viewHolderSettings[4]!=null){
            ViewHolderUser viewHolderUsers = (ViewHolderUser) viewHolderSettings[4];
            viewHolderUsers.refresh();
        }
        settingAdapter.notifyDataSetChanged();
        onAuthVaild(false);
    }

    @Override
    public void toModifyPsw() {
        if (viewHolderSettings[4]!=null){
            ViewHolderUser viewHolderUsers = (ViewHolderUser) viewHolderSettings[4];
            viewHolderUsers.refreshRegisterView(R.layout.item_user_modify);
        }
    }

    @Override
    public void onModifyPsw(String user) {
        if (viewHolderSettings[4]!=null){
            ViewHolderUser viewHolderUsers = (ViewHolderUser) viewHolderSettings[4];
            viewHolderUsers.refreshRegisterView(R.layout.item_user_info);
        }
    }

    @Override
    public void onSettingVaild(int adminLev) {
        if (validHolder==null){
            validHolder = new ViewHolderValiduntil(mContext, layoutRight,DialogFragment_Setting.this);
        }else {
            validHolder.refresh();
        }
        validHolder.setAdminLev(adminLev);
    }

    @Override
    public boolean onAuthVaild(boolean isAuth) {
        int visibility=View.GONE;
        boolean isValidStop=false;
        if (isAuth){
            if (isLogin){
                Data_validnutil dataValidnutil = new Data_validnutil();
                if (dataValidnutil.isEnable()){
                    isValidStop=dataValidnutil.isValidStop();
                    if (isValidStop){
                        visibility=View.VISIBLE;
                        vRightValid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showToast(getResources().getString(R.string.no_validstop));
                            }
                        });
                    }
                }
            }else {
                visibility=View.VISIBLE;
                isValidStop=true;
                vRightValid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast(getResources().getString(R.string.no_login));
                    }
                });
            }
        }


        vRightValid.setVisibility(visibility);
        return isValidStop;
    }

    private class ItemSetting {
        int icon;
        String name;

        public ItemSetting(int icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }

    private class SettingAdapter extends BaseAdapter {
        LayoutInflater inflater;
        int select,noselect;
        public SettingAdapter() {
            inflater = LayoutInflater.from(mContext);
            select=  ContextCompat.getColor(mContext,R.color.theme_color_primary);
            noselect=  ContextCompat.getColor(mContext,R.color.white);
        }

        public void refreshCheck(){
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public ItemSetting getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_setting, parent, false);
                viewHolder.iconView = (TintImageView) convertView.findViewById(R.id.icon);
                viewHolder.txtName = (TintTextView) convertView.findViewById(R.id.txt_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ItemSetting item = getItem(position);
            viewHolder.iconView.setImageResource(item.icon);
            viewHolder.iconView.setImageTintList(curPosition==position?R.color.theme_color_primary:R.color.white);
            if (isLogin){
                if (curPosition==position){
                    viewHolder.txtName.setTextColor(ThemeUtils.replaceColorById(mContext,R.color.theme_color_primary));
                }else {
                    viewHolder.txtName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                }

            }else {
                if (curPosition==position)
                    viewHolder.txtName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                else
                    viewHolder.txtName.setTextColor(ContextCompat.getColor(mContext,R.color.img_no_select));
            }

            viewHolder.txtName.setText(item.name + "");
            return convertView;
        }

        private class ViewHolder {
            private TintImageView iconView;
            private TintTextView txtName;
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
            onSettingVaild(2);
        }
    }

    private void registerFiveRevceiver(){

        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceFiveTimer.ACTION_FiveTimerLogout);
        mContext.registerReceiver(fiveLogoutReceiver, filter);// 注册
    }

    private void unRegisterFiveRevceiver(){
        mContext.unregisterReceiver(fiveLogoutReceiver);
    }

    private BroadcastReceiver fiveLogoutReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ServiceFiveTimer.ACTION_FiveTimerLogout)){
                LogCus.msg("登录到期:");
                isLogin=false;
                settingAdapter.notifyDataSetChanged();
                onAuthVaild(false);
                onLogout();
            }
        }
    };

    /*登录计时闹钟*/
    public  void setAlarmLogin(){
        Intent intent=new Intent();
        intent.setAction(ServiceFiveTimer.ACTION_FiveTimer);

        PendingIntent pi= PendingIntent.getBroadcast(mContext,3, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        //设置一个PendingIntent对象，发送广播
        AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        //获取AlarmManager对象
        long cur=System.currentTimeMillis();
        PreferencesUtil.getInstance(mContext).setLongValue("User", "login_time",cur);
        BigDecimal t=new BigDecimal(cur);
        /*五分钟后的时间*/
        BigDecimal result=t.add(new BigDecimal(TIME_logout*60*1000l));
        LogCus.msg("---登录到期时间："+":"+result.longValue());
        am.set(AlarmManager.RTC_WAKEUP, result.longValue() , pi);
//        if(Build.VERSION.SDK_INT < 19){
//            am.set(AlarmManager.RTC_WAKEUP, result.longValue() , pi);
//        }else{
//            am.setExact(AlarmManager.RTC_WAKEUP, result.longValue() , pi);
//        }


    }

}
