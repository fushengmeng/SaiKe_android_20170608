package com.keruiyun.saike.setting.data;

import android.widget.CheckBox;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.SharePreferenceUtil;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.DateTime;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/11.
 * adminLev 1---设置->高级设置——主题颜色（五次点击）：制造商
 *          2---设置->关于（五次点击）：工程师
 */

public class Data_validnutil {




    /**
     * @return  是否启用有效期设置
     * */
    public boolean isEnable() {
        boolean admin_1=SharePreferenceUtil.getBoolean("ValidEnable1",true);

        boolean admin_2=SharePreferenceUtil.getBoolean("ValidEnable2",false);

//        LogCus.msg("有效期启用:制造商:"+admin_1+":工程师:"+admin_2);
        return admin_1||admin_2;
    }
    /**
     * @return 有效期是否到期
     * */
    public boolean isValidStop(){
        boolean isStop=false;
        boolean admin_1=SharePreferenceUtil.getBoolean("ValidEnable1",true);

        boolean admin_2=SharePreferenceUtil.getBoolean("ValidEnable2",false);
        if (admin_1&&admin_2){

            long endTime1 = getValidEndTime(1);
            long endTime2 = getValidEndTime(2);
            long endTime=endTime2;
            if (endTime1!=-1&&endTime2!=-1){
                if (endTime1<endTime2)
                    endTime=endTime1;

            }else if (endTime1==-1&&endTime2!=-1){
                endTime=endTime2;
            }else if (endTime2==-1&&endTime1!=-1){
                endTime=endTime1;
            }
            if (endTime1==-1&&endTime2==-1){
                isStop=false;
            }else{
                long curTime = System.currentTimeMillis();
//                LogCus.msg("有效期至："+DateTime.getStringByFormat(endTime)+"当前时间："+DateTime.getStringByFormat(curTime));
                isStop=endTime<=curTime;
            }

        }

        return isStop;
    }

    /**
     *
     *@param enable true---开启有效期，false 禁用有效期
     * */
    public void setEnable(int adminLev,boolean enable) {
        SharePreferenceUtil.putBoolean("ValidEnable"+adminLev,enable);
        if (enable){
            SharePreferenceUtil.putLong("ValidEnableTimeStart"+adminLev,System.currentTimeMillis());
        }
        long startTime=SharePreferenceUtil.getLong("ValidEnableTimeStart"+adminLev,System.currentTimeMillis());
//        LogCus.msg(adminLev+" ：enable："+enable+"开始时间："+DateTime.getStringByFormat(startTime));
    }




    /**默认长期有效
     * @return
     * 0-->3个月
     * 1-->6个月
     * 2-->12个月
     * 3-->18个月
     * 4-->24个月
     * 5-->长期有效
     * 6-->自定义
     * */
    public int isBoxValidPosition(int adminLev) {
        return SharePreferenceUtil.getInt("BoxValidPosition"+adminLev,5);
    }

    public long getValidEndTime(int adminLev) {
        /*秒*/
        long startTime=SharePreferenceUtil.getLong("ValidEnableTimeStart"+adminLev,System.currentTimeMillis());
        long endTime=0;

        int position=isBoxValidPosition(adminLev);
        switch (position){
            case 0:
                endTime=endtime(3,startTime);

                break;
            case 1:
                endTime=endtime(6,startTime);
                break;
            case 2:
                endTime=endtime(12,startTime);
                break;
            case 3:
                endTime=endtime(18,startTime);
                break;
            case 4:
                endTime=endtime(24,startTime);
                break;
            case 5:
                endTime=-1;
                break;
            case 6:
                String timeStr=getWheelYear(adminLev)+"-"+getWheelMonth(adminLev)+"-"+getWheelDay(adminLev);
                String curTime = DateTime.getStringByFormat(new Date(System.currentTimeMillis()), DateTime.DEFYMDHMS);
                timeStr=timeStr+curTime.substring(10);
                long ts = DateTime.getLongByFormat(timeStr);
                endTime=ts;
                break;

        }
//        LogCus.msg(adminLev+":position:"+position+" ：有效期至："+DateTime.getStringByFormat(endTime)+"开始时间："+DateTime.getStringByFormat(startTime));
        return endTime;
    }

    public  long endtime(int countMonth,long startTime){
        long oneMonthD=30*24*60*60;
        BigDecimal b1 = new BigDecimal(oneMonthD).multiply(new BigDecimal(1000));
        LogCus.msg("oneMonthD:"+b1.toString());
        return b1.multiply(new BigDecimal(countMonth)).add(new BigDecimal(startTime)).longValue();
    }
    public void setBoxValidPosition(int adminLev,int position) {
        SharePreferenceUtil.putInt("BoxValidPosition"+adminLev,position);
    }

    public String getWheelYear(int adminLev) {
        return SharePreferenceUtil.getString("wheelYear"+adminLev,"0000");
    }

    public void setWheelYear(int adminLev,String wheelYear) {
        SharePreferenceUtil.putString("wheelYear"+adminLev,wheelYear);
    }

    public String getWheelMonth(int adminLev) {
        return SharePreferenceUtil.getString("wheelMonth"+adminLev,"01");
    }

    public void setWheelMonth(int adminLev,String wheelMonth) {
        SharePreferenceUtil.putString("wheelMonth"+adminLev,wheelMonth);
    }

    public String getWheelDay(int adminLev) {
        return SharePreferenceUtil.getString("wheelDay"+adminLev,"01");
    }

    public void setWheelDay(int adminLev,String wheelDay) {
        SharePreferenceUtil.putString("wheelDay"+adminLev,wheelDay);
    }
}
