package com.keruiyun.saike.setting.data;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.SharePreferenceUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/11.
 */

public class Data_Air {

    public static  final int VALUE_MAX_T=50;
    public static  final int VALUE_MIN_T=0;

    public static  final int VALUE_MAX_RH=99;
    public static  final int VALUE_MIN_RH=0;

    public static  final int VALUE_MAX_PA=100;
    public static  final int VALUE_MIN_PA=-100;



    private String TAG="params_air";

    public String[] getArrAirValue(){
        return new String[]{"主站","从站"};
    }
    public String[] getArrIpLine(){
        String[] ipLine=new String[99];
        for (int i=1;i<=99;i++){
            ipLine[i-1]=i+"";
        }
        return ipLine;
    }

    /*空调主从站*/
    public int getTxtAirValue() {
        return SharePreferenceUtil.getInt(TAG+"txtAirValue",0);
    }
    /*空调主从站*/
    public void setTxtAirValue(int txtAirValue) {
        SharePreferenceUtil.putInt(TAG+"txtAirValue",txtAirValue);
    }

    public int getTxtIpaddr() {
        return SharePreferenceUtil.getInt(TAG+"txtIpaddr",0);
    }

    public void setTxtIpaddr(int txtIpaddr) {
        SharePreferenceUtil.putInt(TAG+"txtIpaddr",txtIpaddr);
    }

    /*气体主从站*/
    public int getTxtGasValue() {
        return SharePreferenceUtil.getInt(TAG+"txtGasValue",0);
    }

    public void setTxtGasValue(int txtGasValue) {
        SharePreferenceUtil.putInt(TAG+"txtGasValue",txtGasValue);
    }

    /*空调通讯*/
    public boolean isBoxAirCommunication() {
        return SharePreferenceUtil.getBoolean(TAG+"boxAirCommunication",false);
    }

    public void setBoxAirCommunication(boolean boxAirCommunication) {
        SharePreferenceUtil.putBoolean(TAG+"boxAirCommunication",boxAirCommunication);
    }

    /*空调硬接线*/
    public boolean isBoxAirHardWired() {
        return SharePreferenceUtil.getBoolean(TAG+"boxAirHardWired",false);
    }

    public void setBoxAirHardWired(boolean boxAirHardWired) {
        SharePreferenceUtil.getBoolean(TAG+"boxAirHardWired",boxAirHardWired);
    }


    public int getTxtValueTempMax() {
        return SharePreferenceUtil.getInt(TAG+"txtValueTempMax",VALUE_MAX_T);
    }

    public void setTxtValueTempMax(int txtValueTempMax) {
        SharePreferenceUtil.putInt(TAG+"txtValueTempMax",txtValueTempMax);
    }

    public int getTxtValueTempMin() {
        return SharePreferenceUtil.getInt(TAG+"txtValueTempMin",VALUE_MIN_T);
    }

    public void setTxtValueTempMin(int txtValueTempMin) {
        SharePreferenceUtil.putInt(TAG+"txtValueTempMin",txtValueTempMin);
    }

    public int getTxtValuePaMax() {
        return SharePreferenceUtil.getInt(TAG+"txtValuePaMax",VALUE_MAX_PA);
    }

    public void setTxtValuePaMax(int txtValuePaMax) {
        SharePreferenceUtil.putInt(TAG+"txtValuePaMax",txtValuePaMax);
    }

    public int getTxtValuePaMin() {
        return SharePreferenceUtil.getInt(TAG+"txtValuePaMin",VALUE_MIN_PA);
    }

    public void setTxtValuePaMin(int txtValuePaMin) {
        SharePreferenceUtil.putInt(TAG+"txtValuePaMin",txtValuePaMin);
    }

    public int getTxtValueRhMax() {
        return SharePreferenceUtil.getInt(TAG+"txtValueRhMax",VALUE_MAX_RH);
    }

    public void setTxtValueRhMax(int txtValueRhMax) {
        SharePreferenceUtil.putInt(TAG+"txtValueRhMax",txtValueRhMax);
    }

    public int getTxtValueRhMin() {
        return SharePreferenceUtil.getInt(TAG+"txtValueRhMin",VALUE_MIN_RH);
    }

    public void setTxtValueRhMin(int txtValueRhMin) {
        SharePreferenceUtil.putInt(TAG+"txtValueRhMin",txtValueRhMin);
    }

    public long getTxtValueLightDelayClosing() {
        return SharePreferenceUtil.getLong(TAG+"txtValueLightDelayClosing",0);
    }

    public void setTxtValueLightDelayClosing(long txtValueLightDelayClosing) {
        SharePreferenceUtil.putLong(TAG+"txtValueLightDelayClosing",txtValueLightDelayClosing);
    }

    public long getTxtValueDelayOffTimer() {
        return SharePreferenceUtil.getLong(TAG+"txtValueDelayOffTimer",0);
    }

    public void setTxtValueDelayOffTimer(long txtValueDelayOffTimer) {
        SharePreferenceUtil.putLong(TAG+"txtValueDelayOffTimer",txtValueDelayOffTimer);
    }
}
