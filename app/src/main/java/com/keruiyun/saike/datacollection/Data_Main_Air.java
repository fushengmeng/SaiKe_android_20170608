package com.keruiyun.saike.datacollection;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.keruiyun.saike.main.Fragment_Main;
import com.keruiyun.saike.serialservice.SerialSaunaThread;

/**
 * Created by Administrator on 2018/1/22.
 */

public class Data_Main_Air {

    public int tempSetting, humSetting, pressSetting;



    // 空调温度设置
    public void airTempSetting(int temp) {
        if (tempSetting !=temp) {
            tempSetting=temp;
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_TEMPERATURE, tempSetting );

        }
    }

    // 空调湿度增加
    public void airHumSetting(int hum) {
        if (humSetting !=hum) {
            humSetting=hum;
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_HUMIDITY, hum);


        }
    }

    // 气压增加
    public void airPressureSetting(int pressure) {
        if (pressSetting !=pressure) {
            pressSetting=pressure;
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_PRESSURE, pressSetting);

        }
    }


}
