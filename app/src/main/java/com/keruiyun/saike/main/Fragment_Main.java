package com.keruiyun.saike.main;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.keruiyun.db.BeanPhone;
import com.keruiyun.saike.R;
import com.keruiyun.saike.datacollection.Data_Main_Air;
import com.keruiyun.saike.fragment.BaseDialogFragment;
import com.keruiyun.saike.fragment.DialogFragment_Air;
import com.keruiyun.saike.fragment.DialogFragment_Call;
import com.keruiyun.saike.fragment.DialogFragment_Control;
import com.keruiyun.saike.fragment.DialogFragment_Gas;
import com.keruiyun.saike.fragment.DialogFragment_Music;
import com.keruiyun.saike.fragment.DialogFragment_PhoneBill;
import com.keruiyun.saike.fragment.DialogFragment_Phone_Edit;
import com.keruiyun.saike.fragment.DialogFragment_Setting;
import com.keruiyun.saike.pop.PopVoice;
import com.keruiyun.saike.pop.PopWheel;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.data.Data_Air;
import com.keruiyun.saike.setting.data.Data_Smartstart;
import com.keruiyun.saike.uiview.AnesTimeUi;
import com.keruiyun.saike.uiview.BaseProgressBar;
import com.keruiyun.saike.uiview.OperationTimeUi;
import com.keruiyun.saike.uiview.SystemTimeUi;
import com.keruiyun.saike.uiview.TimerStartPauseListener;
import com.keruiyun.saike.util.DrawableUtil;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.music.soundpool.SoundPlayer;
import com.wifilistconnect.WifiSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/16.
 * @author 蓝美 王
 *
 * 赛科主页
 */

public class Fragment_Main extends BaseFragment implements
        BaseDialogFragment.OnDialogFragmentListener{
    private static Fragment_Main instance;

    @BindView(R.id.data_status)
    ImageView dataStatus;
    @BindView(R.id.operation_timer)
    OperationTimeUi operationTimer;
    @BindView(R.id.title_operationtimer)
    TextView titleOperationtimer;
    @BindView(R.id.en_title_operationtimer)
    TextView enTitleOperationtimer;
    @BindView(R.id.img_start_pause)
    ImageView imgStartPause;
    @BindView(R.id.txt_start_pause)
    TextView txtStartPause;
    @BindView(R.id.img_reset)
    ImageView imgReset;
    @BindView(R.id.txt_reset)
    TextView txtReset;
    @BindView(R.id.anes_timer)
    AnesTimeUi anesTimer;
    @BindView(R.id.title_anestimer)
    TextView titleAnestimer;
    @BindView(R.id.en_title_anestimer)
    TextView enTitleAnestimer;
    @BindView(R.id.img_anes_start_pause)
    ImageView imgAnesStartPause;
    @BindView(R.id.txt_anes_start_pause)
    TextView txtAnesStartPause;
    @BindView(R.id.img_anes_reset)
    ImageView imgAnesReset;
    @BindView(R.id.txt_anes_reset)
    TextView txtAnesReset;
    @BindView(R.id.img_anes_setting)
    ImageView imgAnesSetting;
    @BindView(R.id.txt_anes_setting)
    TextView txtAnesSetting;
    @BindView(R.id.ui_systime)
    SystemTimeUi uiSystime;
    @BindView(R.id.img_indicator_run)
    ImageView imgIndicatorRun;
    @BindView(R.id.txt_indicator_run)
    TextView txtIndicatorRun;

    FloatingView floatingView;

    private FragmentManager fragmentManager;

    ViewHolderBottom viewHolderBottom;

    ViewHolderAirInfo vhAirTemp, vhAirRH, vhAirPa;
    private Data_Main_Air dataMainAir;

    ViewHolderIndexControl vhControl, vhAirSystem, vhCall, vhMusic, vhGas;

    private  DialogFragment_Control dialogFragmentControl;
    private DialogFragment_Gas dialogFragmentGas;
    private DialogFragment_Music musicDialogFragment;


    private PopVoice popVoice;

    public static int volume;
    public static boolean isConnectModBus=true;
    private boolean musicBack;
    private boolean isGasAlarm,isAirAlarm,isOtherAlarm;
    Data_Air data_air;

    public static Fragment_Main getInstance(){
        if (instance==null){
            instance=new Fragment_Main();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getChildFragmentManager();
    }

    @Override
    public int loadContentView() {
        return R.layout.activity_mainindex;
    }

    @Override
    public void onStart() {
        super.onStart();
        registReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        floatingView.destory();
        getActivity().unregisterReceiver(netBlueReceiver);
        if (null != broadcastReceiver)
        {
            try
            {
                this.getActivity().unregisterReceiver(broadcastReceiver);
            }
            catch (Exception e)
            {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
    Typeface typeFaceAirValue;
    public void initView() {
        super.initView();

        data_air = new Data_Air();
        typeFaceAirValue = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/air_value.ttf");
        viewHolderBottom = new ViewHolderBottom(findViewById(R.id.layout_bottom_index));
        vhControl = new ViewHolderIndexControl(0,findViewById(R.id.layout_control));
        vhAirSystem = new ViewHolderIndexControl(1,findViewById(R.id.layout_air_system));
        vhCall = new ViewHolderIndexControl(2,findViewById(R.id.layout_call));
        vhMusic = new ViewHolderIndexControl(3,findViewById(R.id.layout_music));
        vhGas = new ViewHolderIndexControl(4,findViewById(R.id.layout_gas));
        vhAirTemp = new ViewHolderAirInfo(0,findViewById(R.id.layout_air_temperature));
        vhAirRH = new ViewHolderAirInfo(1,findViewById(R.id.layout_air_rh));
        vhAirPa = new ViewHolderAirInfo(2,findViewById(R.id.layout_air_pa));

        dataMainAir=new Data_Main_Air();

        operationTimer.setTimerStartPauseListener(new TimerStartPauseListener() {
            @Override
            public void onStartListener() {
                imgStartPause.setImageResource(R.drawable.sk_timer_puase);
                txtStartPause.setText(getResources().getString(R.string.pause));
            }

            @Override
            public void onPauseListener() {
                imgStartPause.setImageResource(R.drawable.sk_index_03);
                txtStartPause.setText(getResources().getString(R.string.start));
            }
        });
        anesTimer.setTimerStartPauseListener(new TimerStartPauseListener() {
            @Override
            public void onStartListener() {
                imgAnesStartPause.setImageResource(R.drawable.sk_timer_puase);
                txtAnesStartPause.setText(getResources().getString(R.string.pause));
            }

            @Override
            public void onPauseListener() {
                imgAnesStartPause.setImageResource(R.drawable.sk_index_03);
                txtAnesStartPause.setText(getResources().getString(R.string.start));
            }
        });

        anesTimer.setOnClickTimerListener(new BaseProgressBar.OnClickTimerListener() {
            @Override
            public void onClickTimer(BaseProgressBar baseProgressBar) {
                anesTimer.anesTimerDialog();

            }

            @Override
            public void onClickDate(BaseProgressBar baseProgressBar) {

            }
        });

        uiSystime.setOnClickTimerListener(new BaseProgressBar.OnClickTimerListener() {
            @Override
            public void onClickTimer(BaseProgressBar baseProgressBar) {
                uiSystime.systemTimerDialog();
            }

            @Override
            public void onClickDate(BaseProgressBar baseProgressBar) {

            }
        });

        floatingView=new FloatingView(mContext);
        floatingView.show();

        mDataHandler.sendEmptyMessageDelayed(1, 60000);
        registerBroadCast();

    }


    @Override
    public void onDialogFragmentDismissed() {
        setControlStatus(-1);
        dialogFragmentGas=null;
        musicDialogFragment=null;

    }

    @OnClick({R.id.img_start_pause, R.id.txt_start_pause,
            R.id.img_reset, R.id.txt_reset,
            R.id.img_anes_start_pause, R.id.txt_anes_start_pause,
            R.id.img_anes_reset, R.id.txt_anes_reset,
            R.id.img_anes_setting, R.id.txt_anes_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_start_pause:
            case R.id.txt_start_pause:
                if (operationTimer.isStart())
                    operationTimer.opTimerPause();
                else
                    operationTimer.opTimerStart();
                break;
            case R.id.img_reset:
            case R.id.txt_reset:
                operationTimer.opTimerReset();
                break;
            case R.id.img_anes_start_pause:
            case R.id.txt_anes_start_pause:
                if (anesTimer.isStart())
                    anesTimer.anesTimerPause();
                else
                    anesTimer.anesTimerStart();
                break;
            case R.id.img_anes_reset:
            case R.id.txt_anes_reset:
                anesTimer.opTimerReset();
                break;
            case R.id.img_anes_setting:
            case R.id.txt_anes_setting:
                anesTimer.anesTimerDialog();
                break;

        }
    }

    int[] arrIndexSrc=new int[]{R.drawable.sk_index_29
            ,R.drawable.sk_index_31
            ,R.drawable.sk_index_34
            ,R.drawable.sk_index_36
            ,R.drawable.sk_index_26};
    class ViewHolderIndexControl {
        View view;
        @BindView(R.id.img_control)
        TintImageView imgControl;
        @BindView(R.id.txt_control)
        TextView txtControl;

        ViewHolderIndexControl(int type,View view) {
            ButterKnife.bind(this, view);
            this.view=view;
//            refreshTint();
            imgControl.setBackgroundResource(R.drawable.bg_index_control);
            imgControl.setImageResource(arrIndexSrc[type]);
            imgControl.setImageTintList(R.color.white);
            String[] resources = getResources().getStringArray(R.array.arr_index_control);
            txtControl.setText(resources[type]);
            initListener(type);

        }

        void initListener(final int type){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setControlStatus(type);
                    switch (type){
                        case 0:
                            if (dialogFragmentControl==null)
                                dialogFragmentControl=new DialogFragment_Control();
                            dialogFragmentControl.setOnDialogFragmentListener(Fragment_Main.this);
                            dialogFragmentControl.show(fragmentManager,DialogFragment_Control.class.getName());
                            break;
                        case 1:
//                            DialogFragment_Air air=new DialogFragment_Air();
//                            air.show(fragmentManager,DialogFragment_Air.class.getName());
                            onFragmentListener.setCurrentItem(1);
                            break;
                        case 2:
                            DialogFragment_PhoneBill  phoneBill=new DialogFragment_PhoneBill(false);
                            phoneBill.setOnDialogFragmentListener(Fragment_Main.this);
                            phoneBill.show(fragmentManager,DialogFragment_PhoneBill.class.getName());

                            break;
                        case 3:
                            musicDialogFragment = new DialogFragment_Music();
                            musicDialogFragment.setOnDialogFragmentListener(Fragment_Main.this);
                            musicDialogFragment.show(fragmentManager,DialogFragment_Music.class.getName());

                            break;
                        case 4:
                            dialogFragmentGas=new DialogFragment_Gas();
                            dialogFragmentGas.setOnDialogFragmentListener(Fragment_Main.this);
                            dialogFragmentGas.show(fragmentManager,DialogFragment_Gas.class.getName());
                            break;
                    }
                }
            });
        }


    }

    void setControlStatus(int type){
        vhControl.imgControl.setSelected(false);
        vhAirSystem.imgControl.setSelected(false);
        vhCall.imgControl.setSelected(false);
        vhMusic.imgControl.setSelected(false);
        vhGas.imgControl.setSelected(false);
        switch (type){
            case 0:
                vhControl.imgControl.setSelected(true);
                break;
            case 1:
                vhAirSystem.imgControl.setSelected(true);
                break;
            case 2:
                vhCall.imgControl.setSelected(true);
                break;
            case 3:
                vhMusic.imgControl.setSelected(true);
                break;
            case 4:
                vhGas.imgControl.setSelected(true);
                break;
        }
    }

    class ViewHolderBottom {
        @BindView(R.id.img_fire_alarm)
        ImageView imgFireAlarm;
        @BindView(R.id.txt_fire_alarm)
        TextView txtFireAlarm;
        @BindView(R.id.img_it_power)
        ImageView imgItPower;
        @BindView(R.id.txt_it_power)
        TextView txtItPower;
        @BindView(R.id.img_back)
        TintImageView imgBack;
        @BindView(R.id.img_bluetooth)
        TintImageView imgBluetooth;
        @BindView(R.id.img_wifi)
        TintImageView imgWifi;
        @BindView(R.id.img_voice)
        TintImageView imgVoice;
        @BindView(R.id.img_setting)
        TintImageView imgSetting;

        ViewHolderBottom(View view) {
            ButterKnife.bind(this, view);
            imgBack.setImageTintList(R.color.theme_color_primary);
            imgBluetooth.setImageTintList(R.color.theme_color_primary);
            imgWifi.setImageTintList(R.color.theme_color_primary);
            imgVoice.setImageTintList(R.color.theme_color_primary);
            imgSetting.setImageTintList(R.color.theme_color_primary);
            listener();
        }
        void listener(){
            imgBack.setOnClickListener(onClickListener);
            imgBluetooth.setOnClickListener(onClickListener);
            imgWifi.setOnClickListener(onClickListener);
            imgVoice.setOnClickListener(onClickListener);
            imgSetting.setOnClickListener(onClickListener );
        }

        private View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_back:
//                        onFragmentListener.setCurrentItem(-1);

                        break;
                    case R.id.img_bluetooth:

                        break;
                    case R.id.img_wifi:

                        break;
                    case R.id.img_voice:
                        popVoice = new PopVoice(mContext);
                        popVoice.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                popVoice=null;
                            }
                        });
                        popVoice.showAsTopCenter(v);
                        break;
                    case R.id.img_setting:
                        DialogFragment_Setting dialogFragmentSetting = new DialogFragment_Setting();
                        dialogFragmentSetting.show(fragmentManager, DialogFragment_Setting.class.getName());
                        break;
                }
            }
        };
    }


    class ViewHolderAirInfo {
        @BindView(R.id.layout_setting)
        LinearLayout layoutSetting;
        @BindView(R.id.txt_air_info_name)
        TextView txtAirInfoName;
        @BindView(R.id.txt_air_info_setting)
        TextView txtAirInfoSetting;
        @BindView(R.id.txt_params_unit)
        TextView txtAirInfoSettingValue;
        @BindView(R.id.txt_air_info_value)
        TextView txtAirInfoValue;
        @BindView(R.id.txt_air_info_value_unit)
        TextView txtAirInfoValueUnit;

        String unit;
        int valueColor= Color.YELLOW;

        ViewHolderAirInfo(final int type, View view) {
            ButterKnife.bind(this, view);
            Resources res = getResources();

            txtAirInfoValue.setTypeface(typeFaceAirValue);
            txtAirInfoValueUnit.setTypeface(typeFaceAirValue);
            switch (type){
                case 0:
                    valueColor= Color.YELLOW;
                    unit=res.getString(R.string.unit_t);
                    txtAirInfoValue.setText("23.5");
                    break;
                case 1:
                    valueColor= Color.WHITE;
                    unit=res.getString(R.string.unit_rh);
                    txtAirInfoValue.setText("55.0");
                    break;
                case 2:
//                    TypedValue typedValue = new TypedValue();
//                    mContext.getTheme().resolveAttribute(R.attr.custom_attr_color_primary, typedValue, true);
                    valueColor= ContextCompat.getColor(mContext,R.color.theme_color_primary);
                    unit=res.getString(R.string.unit_pa);
                    txtAirInfoValue.setText("08.0");
                    break;
            }

            String[] resources = getResources().getStringArray(R.array.arr_index_air);
            txtAirInfoName.setText(resources[type]);
            txtAirInfoSettingValue.setText(unit);
            txtAirInfoValueUnit.setText(unit);
            txtAirInfoValue.setTextColor(valueColor);
            txtAirInfoValueUnit.setTextColor(valueColor);

            layoutSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String valueSetting=txtAirInfoSetting.getText().toString();
                    float value=0;
                    try {

                        value=Float.parseFloat(valueSetting.trim());

                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        Data_Smartstart data_smartstart=new Data_Smartstart();
                        switch (type){
                            case 0:
                                value=data_smartstart.getTxtValueTemp();
                                break;
                            case 1:
                                value=data_smartstart.getTxtValueRh();
                                break;
                            case 2:
                                value=data_smartstart.getTxtValuePa();
                                break;
                        }

                    }
                    PopWheel popWheel = new PopWheel(mContext,type,((int)value)+"");
                    popWheel.setOnItemSelectListener(new WheelPicker.OnItemSelectListener() {
                        @Override
                        public void onItemSelected(WheelPicker picker, Object data, int position) {

                        }

                        @Override
                        public void onItemClick(WheelPicker picker, Object data, int position) {
                            int value = Integer.parseInt(data.toString());
                            txtAirInfoSetting.setText(data+"");
                            switch (type){
                                case 0:
                                    dataMainAir.airTempSetting(value);
                                    break;
                                case 1:
                                    dataMainAir.airHumSetting(value);
                                    break;
                                case 2:
                                    dataMainAir.airPressureSetting(value);
                                    break;
                            }
                        }
                    });

                    popWheel.showAsRightCenter(v,-10,0);
                }
            });
        }

        void setCurValue(String value){
            txtAirInfoValue.setText(value);
        }
    }


    /**
     * 动态注册监听U盘，网线，WiFi，蓝牙的广播。
     */
    private void registReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        filter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        filter.addAction("android.net.ethernet.STATE_CHANGE");
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

        getActivity().registerReceiver(netBlueReceiver, filter);



    }
    BroadcastReceiver netBlueReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    checkBlueTooth();
                    break;
                case "android.net.ethernet.ETHERNET_STATE_CHANGED":
                case "android.net.ethernet.STATE_CHANGE":
                    checkEtherNet();
                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    checkWiFi();
                    break;
            }
        }
    };

    /**
     * 动态检测蓝牙的状态
     */
    private void checkBlueTooth() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
       boolean isBlueConnect=false;
        if (btAdapter == null)
            isBlueConnect=false;
        else
            isBlueConnect=btAdapter.isEnabled();

        viewHolderBottom.imgBluetooth.setImageTintList(isBlueConnect?R.color.theme_color_primary:R.color.white);

    }

    /**
     * 动态检测网线的状态
     */
    private void checkEtherNet() {
        ConnectivityManager mCm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnect=mCm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).isAvailable();

        viewHolderBottom.imgWifi.setImageTintList(isConnect?R.color.theme_color_primary:R.color.white);
    }

    /**
     * 检测WiFi的连接状态
     */
    private void checkWiFi() {
        boolean isConnect= WifiSupport.isOpenWifi(getContext());
        viewHolderBottom.imgWifi.setImageTintList(isConnect?R.color.theme_color_primary:R.color.white);

    }



    private void dataStatus() {
        // TODO Auto-generated method stub
        mDataHandler.removeCallbacksAndMessages(null);
        if (dataStatus.getVisibility() != View.GONE) {
            dataStatus.setVisibility(View.GONE);
            isConnectModBus=true;
        }
        mDataHandler.sendEmptyMessageDelayed(1, 60000);
    }
    private Handler mDataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    isConnectModBus=false;
                    dataStatus.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    /*气体状态*/
    boolean isStartGasAlarm;
    private void gasStatus() {
       boolean isGasAlarm=isAirAlarm|isOtherAlarm;
//        isGasAlarm=true;
       if (isGasAlarm){
           vhGas.imgControl.setImageTintList(R.color.red);
           if (!isStartGasAlarm){
               isStartGasAlarm=true;
               mErrHandler.sendEmptyMessage(1);
           }

       }else {
           isStartGasAlarm=false;
           vhGas.imgControl.setImageTintList(R.color.white);
           mErrHandler.sendEmptyMessage(-2);
           mErrHandler.removeCallbacksAndMessages(null);
       }


    }
    private Handler mErrHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1://气体报警
                    vhGas.imgControl.setImageTintList(R.color.red);
                    mErrHandler.sendEmptyMessageDelayed(2,1000);
                    break;
                case 2:
                    vhGas.imgControl.setImageTintList(R.color.area_bg);
                    mErrHandler.sendEmptyMessageDelayed(1,1000);
                    break;
                case -2:
                    vhAirSystem.imgControl.setImageTintList(R.color.white);
                    mErrHandler.removeCallbacksAndMessages(null);
                    break;
                case 3://空调报警
                    vhAirSystem.imgControl.setImageTintList(R.color.red);
                    mErrHandler.sendEmptyMessageDelayed(4,1000);
                    break;
                case 4:
                    vhAirSystem.imgControl.setImageTintList(R.color.area_bg);
                    mErrHandler.sendEmptyMessageDelayed(3,1000);
                    break;
                case -4:
                    vhAirSystem.imgControl.setImageTintList(R.color.white);
                    mErrHandler.removeCallbacksAndMessages(null);
                    break;
                default:
                    break;
            }
            return false;
        }
    });



    private void systemRunning(int running) {
        if (1 == running) {
            txtIndicatorRun.setText(mContext.getResources().getString(R.string.run));
            imgIndicatorRun.setImageResource(R.drawable.sk_index_19);
        } else {
            txtIndicatorRun.setText(mContext.getResources().getString(R.string.stop));
            imgIndicatorRun.setImageResource(R.drawable.sk_index_21);
        }
    }
    //故障报警
    private void alarm(int data)
    {
        if (1 == data)//故障
        {
            mErrHandler.sendEmptyMessage(3);
        }
        else
        {
            mErrHandler.sendEmptyMessage(-4);

        }
    }

    private void alarmFire(int data)
    {
        if (1 == data)
        {
            viewHolderBottom.txtFireAlarm.setText(getResources().getString(R.string.fire_alarm_err));
            viewHolderBottom.txtFireAlarm.setTextColor(Color.RED);
            viewHolderBottom.imgFireAlarm.setImageResource(R.drawable.sk_index_63);
        }
        else
        {

            viewHolderBottom.txtFireAlarm.setText(getResources().getString(R.string.fire_alarm));
            viewHolderBottom.txtFireAlarm.setTextColor(Color.WHITE);
            viewHolderBottom.imgFireAlarm.setImageResource(R.drawable.sk_index_65);
        }
    }

    private void alarmPower(int data)
    {
        if (1 == data)
        {

            viewHolderBottom.txtItPower.setText(getResources().getString(R.string.it_power_err));
            viewHolderBottom.txtItPower.setTextColor(Color.RED);
            viewHolderBottom.imgItPower.setImageResource(R.drawable.sk_index_63);
        }
        else
        {

            viewHolderBottom.txtItPower.setText(getResources().getString(R.string.it_power));
            viewHolderBottom.txtItPower.setTextColor(Color.WHITE);
            viewHolderBottom.imgItPower.setImageResource(R.drawable.sk_index_65);
        }
    }

    private BroadcastReceiver broadcastReceiver;

    private void registerBroadCast() {
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                dataStatus();
                int addr = arg1.getIntExtra("addr", -1);
//                LogCus.msg("addr:"+addr);
                if (SerialSaunaThread.ADDR_DUST_PARTICLE_01 == addr)
                {
                    long data1 = arg1.getLongExtra("data1", 0);
                    long data2 = arg1.getLongExtra("data2", 0);
//                    LogCus.msg("尘埃粒子:"+String.valueOf(data1)+":"+String.valueOf(data2));

                }
                else
                {
                    short data = arg1.getShortExtra("data", (short) -1);
                    switch (addr) {
                        case SerialSaunaThread.ADDR_STATUS:
                            // 机组状态-系统运行
                            int systemRunning = data & 0x01;
                            systemRunning(systemRunning);
                            // 机组状态-值班运行
                            int waitRunning = (data >> 1) & 0x01;

                            // 机组状态-系统故障
                            int alarmSystem = (data >> 2) & 0x01;

                            // 机组状态-初效报警
                            int alarm1 = (data >> 3) & 0x01;

                            // 机组状态-中效报警
                            int alarm2 = (data >> 4) & 0x01;

                            // 机组状态-高效报警
                            int alarm3 = (data >> 5) & 0x01;

                            // 机组状态-缺风报警
                            int alarmwind = (data >> 6) & 0x01;

                            // 机组状态-高温报警
                            int alarmTemp = (data >> 7) & 0x01;

                            // 机组状态-是否有报警
                            int isAlarm=0x00;
                            isAlarm=isAlarm|alarmSystem|alarm1|alarm2|alarm3|alarmwind|alarmTemp;
                            alarm(isAlarm);

                            //消防状态
                            int alarmFire = (data >> 12) & 0x01;
                            alarmFire(alarmFire);

                            //IT电源状态
                            int alarmPower = (data >> 13) & 0x01;
                            alarmPower(alarmPower);
                            break;
                        case SerialSaunaThread.ADDR_AIR_TEMPERATURE:
                            String rep = String.format("%.1f", data / 10.0f);
                            vhAirTemp.txtAirInfoValue.setText(rep);

//                                Log.e("回风温度：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_AIR_HUMIDITY:
                             rep = String.format("%.1f", data / 10.0f);
                            vhAirRH.txtAirInfoValue.setText(rep);
//                                Log.e("回风湿度：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_ROOM_PRESSURE:
                             rep = String.format("%.1f", data / 10.0f);
                            vhAirPa.txtAirInfoValue.setText(rep);
//                                Log.e("回风压差：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_SET_TEMPERATURE:
                            dataMainAir.tempSetting = data;
                             rep = String.format("%.1f", data * 1.0f);
                            vhAirTemp.txtAirInfoSetting.setText(rep);

//                            Log.e("温度设定：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_SET_HUMIDITY:
                            dataMainAir.humSetting = data;
                             rep = String.format("%.1f", data * 1.0f);
                            vhAirRH.txtAirInfoSetting.setText(rep);
//                            Log.e("湿度设定：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_SET_PRESSURE:
                            dataMainAir.pressSetting = data;
                             rep = String.format("%.1f", data * 1.0f);
                            vhAirPa.txtAirInfoSetting.setText(rep);
//                            Log.e("压差设定：", String.valueOf(data));
                            break;
                        case SerialSaunaThread.ADDR_POWER_KEY:
                        case SerialSaunaThread.ADDR_DUTY_KEY:
                        case SerialSaunaThread.ADDR_CONTROL_KEY:
                            if (dialogFragmentControl!=null){
                                dialogFragmentControl.updateSerialData(addr,data);
                            }

                            break;
                        case SerialSaunaThread.ADDR_AIR_ALARM:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            isAirAlarm=false;
                            // 氧气
                            int isO2High = data & 0x01;
                            isAirAlarm=isAirAlarm||(isO2High==1);
                            int isO2Low = (data >> 1) & 0x01;
                            isAirAlarm=isAirAlarm||(isO2Low==1);

                            // 氮气
                            int isNitrogenHigh = (data >> 2) & 0x01;
                            isAirAlarm=isAirAlarm||(isNitrogenHigh==1);
                            int isNitrogenLow = (data >> 3) & 0x01;
                            isAirAlarm=isAirAlarm||(isNitrogenLow==1);

                            // 笑气
                            int isLaughHigh = (data >> 4) & 0x01;
                            isAirAlarm=isAirAlarm||(isLaughHigh==1);
                            int isLaughLow = (data >> 5) & 0x01;
                            isAirAlarm=isAirAlarm||(isLaughLow==1);

                            // 氩气
                            int isArgonHigh = (data >> 6) & 0x01;
                            isAirAlarm=isAirAlarm||(isArgonHigh==1);
                            int isArgonLow = (data >> 7) & 0x01;
                            isAirAlarm=isAirAlarm||(isArgonLow==1);

                            // 压缩空气
                            int isCompressO2High = (data >> 8) & 0x01;
                            isAirAlarm=isAirAlarm||(isCompressO2High==1);
                            int isCompressO2Low = (data >> 9) & 0x01;
                            isAirAlarm=isAirAlarm||(isCompressO2Low==1);
                            gasStatus();
                            break;
                        case SerialSaunaThread.ADDR_OTHER_ALARM:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            isOtherAlarm=false;
                            // 二氧化碳
                            int isCarbonHigh = data & 0x01;
                            isOtherAlarm=isOtherAlarm||(isCarbonHigh==1);
                            int isCarbonLow = (data >> 1) & 0x01;
                            isOtherAlarm=isOtherAlarm||(isCarbonLow==1);

                            // 负压吸引
                            int isNegHigh = (data >> 2) & 0x01;
                            isOtherAlarm=isOtherAlarm||(isNegHigh==1);
                            int isNegLow = (data >> 3) & 0x01;
                            isOtherAlarm=isOtherAlarm||(isNegLow==1);
                            gasStatus();
                            break;
                        case SerialSaunaThread.ADDR_AIR_PRESS_01:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_02:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_03:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_04:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_05:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_06:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;

                        case SerialSaunaThread.ADDR_AIR_PRESS_07:
                            if (dialogFragmentGas!=null)
                                dialogFragmentGas.updateSerialData(addr,data);
                            break;
                        case SerialSaunaThread.ADDR_VOLUMN_KEY:
//                            LogCus.msg("声音："+ String.valueOf(data));
                            volume=data;
                            if (popVoice!=null)
                                popVoice.updateVolume(data);
                            if (musicDialogFragment!=null)
                                musicDialogFragment.updateSerialData(addr,data);
                            break;
                        case SerialSaunaThread.ADDR_MUSIC_KEY:
//                            LogCus.msg("背景音乐 MUSIC:data:"+data);
                            musicBack=data==1;
                            if (musicDialogFragment!=null)
                                musicDialogFragment.updateSerialData(addr,data);


                            break;
                        case SerialSaunaThread.ADDR_CALL_KEY:
                            if (data == 0) {
                                // handUpChange();
                            }
                            break;
                        case SerialSaunaThread.ADDR_LIGHT_OFF_SET:
                           long sleepTime = data*1000;
                            data_air.setTxtValueLightDelayClosing(sleepTime);
                            break;
                        case SerialSaunaThread.ADDR_LIGHT_OFF_TINT://照明即将关闭！！！请注意！！
                            /*if (0 == data)
                            {
                                marqueeView.setVisibility(View.INVISIBLE);
                                marqueeView.stopFlipping();
                            }
                            else
                            {
                                marqueeView.setVisibility(View.VISIBLE);
                                marqueeView.startFlipping();
                            }*/
                            break;



                        default:
                            break;
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.order");
        this.getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }
}
