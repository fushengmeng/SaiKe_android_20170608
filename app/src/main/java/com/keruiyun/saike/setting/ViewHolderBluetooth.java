package com.keruiyun.saike.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.setting.bluetooth.FriendInfo;
import com.keruiyun.saike.setting.bluetooth.GroupFriendAdapter;
import com.keruiyun.saike.setting.bluetooth.GroupInfo;
import com.keruiyun.saike.uiview.SwitchButton;
import com.keruiyun.saike.util.LogCus;
import com.util.DateTime;
import com.util.ToastUtil;
import com.vise.basebluetooth.callback.IPairCallback;
import com.vise.basebluetooth.callback.IScanCallback;
import com.vise.basebluetooth.receiver.PairBroadcastReceiver;
import com.vise.basebluetooth.receiver.ScanBroadcastReceiver;
import com.vise.basebluetooth.utils.BluetoothUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/1/9.
 * 设置----蓝牙
 */

public class ViewHolderBluetooth extends BaseViewHolder {
    @BindView(R.id.scan_progress)
    ProgressBar mScanPb;
    @BindView(R.id.switch_bluetooth)
    SwitchButton switchBluetooth;
    @BindView(R.id.bluetooth_name)
    TextView bluetooth_name;
    @BindView(R.id.grouplist_bluetooth)
    ExpandableListView grouplistBluetooth;
    private Handler mHandler;
    private GroupInfo groupPairInfo;
    private GroupInfo groupNoPairInfo;
    private GroupFriendAdapter mGroupFriendAdapter;
    private List<GroupInfo> mGroupFriendListData ;
    private ProgressDialog mProgressDialog;
    private ScanBroadcastReceiver mScanBroadcastReceiver;
    private PairBroadcastReceiver mPairBroadcastReceiver;
    private Map<String, BluetoothDevice> mDevicePairMap ;
    private Map<String, BluetoothDevice> mDeviceMap ;


    private final IScanCallback<BluetoothDevice> scanCallback = new IScanCallback<BluetoothDevice>() {
        @Override
        public void discoverDevice(BluetoothDevice bluetoothDevice) {
            if (!mDeviceMap.containsKey(bluetoothDevice.getAddress())) {
                if (!mDevicePairMap.containsKey(bluetoothDevice.getAddress())){
                    mDeviceMap.put(bluetoothDevice.getAddress(), bluetoothDevice);
                    groupNoPairInfo.getFriendList().add(bluetoothDeviceToFriendInfo(bluetoothDevice,false));
                    mGroupFriendAdapter.notifyDataSetChanged();
                }

            }
        }

        @Override
        public void scanTimeout() {
            mScanPb.setVisibility(View.GONE);
            ToastUtil.showToast(context.getResources().getString(R.string.bluetooth_scan_timeout));
        }

        @Override
        public void scanFinish(List<BluetoothDevice> bluetoothDevices) {
            mScanPb.setVisibility(View.GONE);
            ToastUtil.showToast(context.getResources().getString(R.string.bluetooth_scan_finish));

        }
    };

    private final IPairCallback pairCallback = new IPairCallback() {
        @Override
        public void unBonded() {
            LogCus.msg("unBonded");
        }

        @Override
        public void bonding() {
            LogCus.msg("bonding");
        }

        @Override
        public void bonded() {
            mProgressDialog.dismiss();
            LogCus.msg("bonded");
            ToastUtil.showToast(context.getResources().getString(R.string.bluetooth_pair_success));

        }

        @Override
        public void bondFail() {
            mProgressDialog.hide();
            LogCus.msg("bondFail");
            ToastUtil.showToast(context.getResources().getString(R.string.bluetooth_pair_fail));
        }
    };

    public ViewHolderBluetooth(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
        //        startScan();
    }


    @Override
    public int loadContentView() {
        return R.layout.layout_setting_bluetooth;
    }

    @Override
    public void initView(final Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);

        mProgressDialog = new ProgressDialog(context);
        mDevicePairMap = new HashMap<>();
        mDeviceMap = new HashMap<>();
        mGroupFriendListData = new ArrayList<>();
        mGroupFriendAdapter = new GroupFriendAdapter(context, mGroupFriendListData);
        grouplistBluetooth.setAdapter(mGroupFriendAdapter);
        findDevice();
        grouplistBluetooth.expandGroup(0);
        switchBluetooth.setChecked(BluetoothUtil.isBleEnable(context));
        switchBluetooth.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
               boolean isBle= BluetoothUtil.isBleEnable(context);
                LogCus.msg("蓝牙："+isBle+":switchBluetooth:"+isChecked);
                if (isChecked){
                    if (!isBle)
                        BluetoothUtil.enableBluetooth((BaseActivity)context,2);
                    startScan();
                }else {
                    if (isBle)
                        BluetoothUtil.disableBluetooth(context);
                }
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler=new Handler();
        beginDiscover();
    }

    @Override
    public void onActivityResultViewHolder(int requestCode, int resultCode, Intent data) {
        super.onActivityResultViewHolder(requestCode, resultCode, data);
        LogCus.msg("ViewHolderBluetooth：onActivityResult:requestCode:"+requestCode+":resultCode："+resultCode);
        if(requestCode == 1){
//            if(resultCode == Activity.RESULT_OK){
            if (BluetoothUtil.isBleEnable(context)){
                BluetoothAdapter.getDefaultAdapter().startDiscovery();
                LogCus.msg("Start Scan");
                mScanPb.setVisibility(View.VISIBLE);
            } else{
//                finish();
            }
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
        if ( BluetoothAdapter.getDefaultAdapter()!=null)
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        if(mScanBroadcastReceiver != null){
            ((BaseActivity)context).unregisterReceiver(mScanBroadcastReceiver);
            mScanBroadcastReceiver = null;
        }
        if(mPairBroadcastReceiver != null){
            ((BaseActivity)context).unregisterReceiver(mPairBroadcastReceiver);
            mPairBroadcastReceiver = null;
        }
    }

    private void findDevice(){
        groupPairInfo = new GroupInfo();
        groupPairInfo.setGroupName(context.getResources().getString(R.string.device_pair));
        groupPairInfo.setFriendList(new ArrayList<FriendInfo>());

        if (BluetoothAdapter.getDefaultAdapter()!=null){
            bluetooth_name.setText(BluetoothAdapter.getDefaultAdapter().getName()+"");
            // 获得已经保存的配对设备
            Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            if (pairedDevices.size() > 0) {
                mGroupFriendListData.clear();

                for (BluetoothDevice device : pairedDevices) {
                    mDevicePairMap.put(device.getAddress(),device);
                    groupPairInfo.getFriendList().add(bluetoothDeviceToFriendInfo(device,true));

                }

                groupPairInfo.setOnlineNumber(0);
            }
        }



        mGroupFriendListData.add(groupPairInfo);

        groupNoPairInfo = new GroupInfo();
        groupNoPairInfo.setGroupName(context.getResources().getString(R.string.device_nopair));
        groupNoPairInfo.setFriendList(new ArrayList<FriendInfo>());
        mGroupFriendListData.add(groupNoPairInfo);

        mGroupFriendAdapter.setGroupInfoList(mGroupFriendListData);
    }

    private FriendInfo bluetoothDeviceToFriendInfo(BluetoothDevice device,boolean isPair){
        FriendInfo friendInfo = new FriendInfo();
        friendInfo.setIdentificationName(device.getName());
        friendInfo.setDeviceAddress(device.getAddress());
        friendInfo.setFriendNickName(device.getName());
        friendInfo.setOnline(false);
        friendInfo.setPair(isPair);
        friendInfo.setJoinTime(DateTime.getStringByFormat(new Date(), DateTime.DEFYMDHMS));
        friendInfo.setBluetoothDevice(device);
        return friendInfo;
    }

    /*为配对设备点击事件*/
    protected void initEvent(int position) {
        BluetoothDevice device=mDeviceMap.get(groupNoPairInfo.getFriendList().get(position).getDeviceAddress());
        if(device.getBondState() != BluetoothDevice.BOND_BONDED){
            startPair();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond();
            } else{
                //利用反射方法调用BluetoothDevice.createBond(BluetoothDevice remoteDevice);
                Method createBondMethod = null;
                try {
                    createBondMethod =BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            mProgressDialog.setMessage(context.getResources().getString(R.string.bluetooth_pair_loading));
            mProgressDialog.show();
        } else{
            ToastUtil.showToast(context.getResources().getString(R.string.device_pair));
        }
    }

    private void beginDiscover(){
        if(mScanBroadcastReceiver == null){
            mScanBroadcastReceiver = new ScanBroadcastReceiver(scanCallback);
        }
        //注册蓝牙扫描监听器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        ((BaseActivity)context).registerReceiver(mScanBroadcastReceiver, intentFilter);
//        BluetoothUtil.enableBluetooth((Activity) context, 1);
        startScan();
    }

    private void startScan(){
        if (BluetoothUtil.isBleEnable(context)){
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   boolean isScan =  BluetoothAdapter.getDefaultAdapter().startDiscovery();
                    LogCus.msg("蓝牙开始扫描:"+isScan);
                }
            },3000);
        }
    }


    private void startPair(){
        if(mPairBroadcastReceiver == null){
            mPairBroadcastReceiver = new PairBroadcastReceiver(pairCallback);
        }
        //注册蓝牙配对监听器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        ((BaseActivity)context).registerReceiver(mPairBroadcastReceiver, intentFilter);
    }
}
