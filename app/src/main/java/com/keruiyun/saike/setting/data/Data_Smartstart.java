package com.keruiyun.saike.setting.data;


import com.keruiyun.saike.R;
import com.keruiyun.saike.util.SharePreferenceUtil;


/**
 * Created by Administrator on 2018/1/11.
 */

public class Data_Smartstart {

    public boolean isSwitchLighting1() {
        return SharePreferenceUtil.getBoolean("switchLighting1",false);
    }

    public void setSwitchLighting1(boolean switchLighting1) {
        SharePreferenceUtil.putBoolean("switchLighting1",switchLighting1);
    }

    public boolean isSwitchLighting2() {
        return SharePreferenceUtil.getBoolean("switchLighting2",false);
    }

    public void setSwitchLighting2(boolean switchLighting2) {

        SharePreferenceUtil.putBoolean("switchLighting2",switchLighting2);
    }

    /*观片灯*/
    public boolean isSwitchFilmViewer() {
        return SharePreferenceUtil.getBoolean("switchFilmViewer",false);
    }

    public void setSwitchFilmViewer(boolean switchFilmViewer) {
        SharePreferenceUtil.putBoolean("switchFilmViewer",switchFilmViewer);
    }

    /*手术中*/
    public boolean isSwitchIntraoperative() {
        return SharePreferenceUtil.getBoolean("switchIntraoperative",false);
    }

    public void setSwitchIntraoperative(boolean switchIntraoperative) {

        SharePreferenceUtil.putBoolean("switchIntraoperative",switchIntraoperative);
    }

    /*无影灯*/
    public boolean isSwitchShadowlessLamp() {
        return SharePreferenceUtil.getBoolean("switchShadowlessLamp",false);
    }

    public void setSwitchShadowlessLamp(boolean switchShadowlessLamp) {

        SharePreferenceUtil.putBoolean("switchShadowlessLamp",switchShadowlessLamp);
    }

    /*排风机*/
    public boolean isSwitchExhaustFan() {
        return SharePreferenceUtil.getBoolean("switchExhaustFan",false);
    }

    public void setSwitchExhaustFan(boolean switchExhaustFan) {

        SharePreferenceUtil.putBoolean("switchExhaustFan",switchExhaustFan);
    }

    /*书写台*/
    public boolean isSwitchWritingCounter() {
        return SharePreferenceUtil.getBoolean("switchWritingCounter",false);
    }

    public void setSwitchWritingCounter(boolean switchWritingCounter) {

        SharePreferenceUtil.putBoolean("switchWritingCounter",switchWritingCounter);
    }

    /*空调启停*/
    public boolean isSwitchAirStartStop() {
        return SharePreferenceUtil.getBoolean("switchAirStartStop",false);    }

    public void setSwitchAirStartStop(boolean switchAirStartStop) {

        SharePreferenceUtil.putBoolean("switchAirStartStop",switchAirStartStop);
    }

    /*负压运行*/
    public boolean isSwitchVacuumRun() {
        return SharePreferenceUtil.getBoolean("switchVacuumRun",false);
    }

    public void setSwitchVacuumRun(boolean switchVacuumRun) {

        SharePreferenceUtil.putBoolean("switchVacuumRun",switchVacuumRun);
    }

    public int getTxtValueTemp() {
        return SharePreferenceUtil.getInt("txtValueTemp",24);
    }

    public void setTxtValueTemp(int txtValueTemp) {

        SharePreferenceUtil.putInt("txtValueTemp",txtValueTemp);
    }

    public String getTxtValueTimerOn() {
        return SharePreferenceUtil.getString("txtValueTimerOn","");
    }

    public void setTxtValueTimerOn(String txtValueTimerOn) {
        SharePreferenceUtil.putString("txtValueTimerOn",txtValueTimerOn);
    }

    public int getTxtValueRh() {
        return SharePreferenceUtil.getInt("txtValueRh",55);
    }

    public void setTxtValueRh(int txtValueRh) {

        SharePreferenceUtil.putInt("txtValueRh",txtValueRh);
    }

    public String getTxtValueTimerOff() {
        return SharePreferenceUtil.getString("txtValueTimerOff","");
    }

    public void setTxtValueTimerOff(String txtValueTimerOff) {

        SharePreferenceUtil.putString("txtValueTimerOff",txtValueTimerOff);
    }

    public int getTxtValuePa() {
        return SharePreferenceUtil.getInt("txtValuePa",8);
    }

    public void setTxtValuePa(int txtValuePa) {

        SharePreferenceUtil.putInt("txtValuePa",txtValuePa);
    }
}
