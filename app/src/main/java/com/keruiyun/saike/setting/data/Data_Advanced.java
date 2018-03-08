package com.keruiyun.saike.setting.data;

import com.keruiyun.saike.util.SharePreferenceUtil;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Data_Advanced {


    public static int getStandbyTimePositon() {

        return SharePreferenceUtil.getInt("standbyTimePositon",0);
    }

    public static void setStandbyTimePositon(int standbyTimePositon) {
        SharePreferenceUtil.putInt("standbyTimePositon",standbyTimePositon);
    }
}
