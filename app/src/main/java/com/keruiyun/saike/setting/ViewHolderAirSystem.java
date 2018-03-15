package com.keruiyun.saike.setting;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.pop.PopList;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.data.Data_Air;
import com.keruiyun.saike.setting.util.OnTouchAdd;
import com.keruiyun.saike.setting.util.OnTouchListenerAddSub;
import com.keruiyun.saike.setting.util.OnTouchSub;
import com.keruiyun.saike.util.LogCus;
import com.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----空调系统
 */

public class ViewHolderAirSystem extends BaseViewHolder {

    @BindView(R.id.layout_select_air)
    LinearLayout layoutSelectAir;
    @BindView(R.id.layout_select_ipline)
    LinearLayout layoutSelectIpline;
    @BindView(R.id.layout_select_gas)
    LinearLayout layoutSelectGas;
    @BindView(R.id.txt_air_value)
    TextView txtAirValue;
    @BindView(R.id.img_select_air_value)
    ImageView imgSelectAirValue;
    @BindView(R.id.txt_ipaddr)
    TextView txtIpaddr;
    @BindView(R.id.img_select_ipaddr)
    ImageView imgSelectIpaddr;
    @BindView(R.id.txt_gas_value)
    TextView txtGasValue;
    @BindView(R.id.img_select_gas_value)
    ImageView imgSelectGasValue;
    @BindView(R.id.rg_air)
    RadioGroup rgAir;
    @BindView(R.id.box_air_communication)
    RadioButton boxAirCommunication;
    @BindView(R.id.box_air_hard_wired)
    RadioButton boxAirHardWired;
    @BindView(R.id.b_temp_max_sub)
    ImageView bTempMaxSub;
    @BindView(R.id.txt_value_temp_max)
    TextView txtValueTempMax;
    @BindView(R.id.b_temp_max_add)
    ImageView bTempMaxAdd;
    @BindView(R.id.b_pa_max_sub)
    ImageView bPaMaxSub;
    @BindView(R.id.txt_value_pa_max)
    TextView txtValuePaMax;
    @BindView(R.id.b_pa_max_add)
    ImageView bPaMaxAdd;
    @BindView(R.id.b_temp_min_sub)
    ImageView bTempMinSub;
    @BindView(R.id.txt_value_temp_min)
    TextView txtValueTempMin;
    @BindView(R.id.b_temp_min_add)
    ImageView bTempMinAdd;
    @BindView(R.id.b_pa_min_sub)
    ImageView bPaMinSub;
    @BindView(R.id.txt_value_pa_min)
    TextView txtValuePaMin;
    @BindView(R.id.b_pa_min_add)
    ImageView bPaMinAdd;
    @BindView(R.id.b_rh_max_sub)
    ImageView bRhMaxSub;
    @BindView(R.id.txt_value_rh_max)
    TextView txtValueRhMax;
    @BindView(R.id.b_rh_max_add)
    ImageView bRhMaxAdd;
    @BindView(R.id.b_light_delay_closing_sub)
    ImageView bLightDelayClosingSub;
    @BindView(R.id.txt_value_light_delay_closing)
    TextView txtValueLightDelayClosing;
    @BindView(R.id.b_light_delay_closing_add)
    ImageView bLightDelayClosingAdd;
    @BindView(R.id.b_rh_min_sub)
    ImageView bRhMinSub;
    @BindView(R.id.txt_value_rh_min)
    TextView txtValueRhMin;
    @BindView(R.id.b_rh_min_add)
    ImageView bRhMinAdd;
    @BindView(R.id.b_delay_off_timer_sub)
    ImageView bDelayOffTimerSub;
    @BindView(R.id.txt_value_delay_off_timer)
    TextView txtValueDelayOffTimer;
    @BindView(R.id.b_delay_off_timer_add)
    ImageView bDelayOffTimerAdd;

    Data_Air data_air;


    public ViewHolderAirSystem(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_airsystem;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        data_air = new Data_Air();

        txtAirValue.setText(data_air.getArrAirValue()[data_air.getTxtAirValue()] + "");

        txtIpaddr.setText(data_air.getArrIpLine()[data_air.getTxtIpaddr()] + "");

        txtGasValue.setText(data_air.getArrAirValue()[data_air.getTxtGasValue()] + "");

        boxAirCommunication.setChecked(data_air.isBoxAirCommunication());

        boxAirHardWired.setChecked(data_air.isBoxAirHardWired());

        if (boxAirCommunication.isChecked()&&boxAirHardWired.isChecked()){
            boxAirCommunication.setChecked(false);
            boxAirHardWired.setChecked(false);
            data_air.setBoxAirCommunication(false);
            data_air.setBoxAirHardWired(false);
        }

        txtValueTempMax.setText(data_air.getTxtValueTempMax() + "");

        txtValuePaMax.setText(data_air.getTxtValuePaMax() + "");

        txtValueTempMin.setText(data_air.getTxtValueTempMin() + "");

        txtValuePaMin.setText(data_air.getTxtValuePaMin() + "");

        txtValueRhMax.setText(data_air.getTxtValueRhMax() + "");

        txtValueLightDelayClosing.setText(data_air.getTxtValueLightDelayClosing() / 1000 + "");

        txtValueRhMin.setText(data_air.getTxtValueRhMin() + "");

        txtValueDelayOffTimer.setText(data_air.getTxtValueDelayOffTimer() / 1000 + "");


        rgAir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.box_air_communication:
                        data_air.setBoxAirCommunication(true);

                        break;
                    case R.id.box_air_hard_wired:
                        data_air.setBoxAirHardWired(true);
                        break;
                }
            }
        });

        bTempMaxSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub(){
            @Override
            public void onSub() {
                tempMaxSub();

            }
        }));
        bTempMaxAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                tempMaxAdd();

            }
        }));

        bTempMinSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                tempMinSub();

            }
        }));
        bTempMinAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                tempMinAdd();

            }
        }));

        bRhMaxSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                rhMaxSub();

            }
        }));
        bRhMaxAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                rhMaxAdd();

            }
        }));


        bRhMinSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                rhMinSub();

            }
        }));
        bRhMinAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                rhMinAdd();

            }
        }));


        bPaMaxSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                paMaxSub();

            }
        }));
        bPaMaxAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                paMaxAdd();

            }
        }));


        bPaMinSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                paMinSub();

            }
        }));
        bPaMinAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                paMinAdd();

            }
        }));


        bDelayOffTimerSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                delayOffTimerSub();

            }
        }));
        bDelayOffTimerAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                delayOffTimerAdd();

            }
        }));


        bLightDelayClosingSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                lightDelayClosingSub();

            }
        }));
        bLightDelayClosingAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                lightDelayClosingAdd();

            }
        }));


    }

    private void lightDelayClosingAdd() {
        long delay_light_add = data_air.getTxtValueLightDelayClosing();
        delay_light_add += 1000;
        if (delay_light_add>=250*1000){
            delay_light_add=250*1000;
            ToastUtil.showToast(context.getResources().getString(R.string.max_delay_light_add));
        }
        data_air.setTxtValueLightDelayClosing(delay_light_add);
        txtValueLightDelayClosing.setText(delay_light_add / 1000 + "");
        SerialSaunaThread.writeCmdQueue(1,
                SerialSaunaThread.ADDR_LIGHT_OFF_SET, (int) (delay_light_add/1000));
    }

    private void lightDelayClosingSub() {
        long delay_light_sub = data_air.getTxtValueLightDelayClosing();
        if (delay_light_sub > 0) {
            delay_light_sub -= 1000;
            data_air.setTxtValueLightDelayClosing(delay_light_sub);
            txtValueLightDelayClosing.setText(delay_light_sub / 1000 + "");
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_LIGHT_OFF_SET, (int) (delay_light_sub/1000));
        }
    }

    private void delayOffTimerAdd() {
        long delay_off_add = data_air.getTxtValueDelayOffTimer();
        delay_off_add += 1000;
        data_air.setTxtValueDelayOffTimer(delay_off_add);
        txtValueDelayOffTimer.setText(delay_off_add / 1000 + "");
    }

    private void delayOffTimerSub() {
        long delay_off_sub = data_air.getTxtValueDelayOffTimer();
        if (delay_off_sub > 0) {
            delay_off_sub -= 1000;
            data_air.setTxtValueDelayOffTimer(delay_off_sub);
            txtValueDelayOffTimer.setText(delay_off_sub / 1000 + "");
        }
    }

    private void paMinAdd() {
        int paMin_add = data_air.getTxtValuePaMin();
        if (paMin_add < data_air.getTxtValuePaMax()) {
            paMin_add++;
            data_air.setTxtValuePaMin(paMin_add);
            txtValuePaMin.setText(paMin_add + "");
        }
    }

    private void paMinSub() {
        int paMin_sub = data_air.getTxtValuePaMin();
        if (paMin_sub > Data_Air.VALUE_MIN_PA) {
            paMin_sub--;
            data_air.setTxtValuePaMin(paMin_sub);
            txtValuePaMin.setText(paMin_sub + "");
        }
    }

    private void paMaxAdd() {
        int paMax_add = data_air.getTxtValuePaMax();
        if (paMax_add>=Data_Air.VALUE_MAX_PA)
            return;
        paMax_add++;
        data_air.setTxtValuePaMax(paMax_add);
        txtValuePaMax.setText(paMax_add + "");
    }

    private void paMaxSub() {
        int paMax_sub = data_air.getTxtValuePaMax();
        if (paMax_sub > data_air.getTxtValuePaMin()) {
            paMax_sub--;
            data_air.setTxtValuePaMax(paMax_sub);
            txtValuePaMax.setText(paMax_sub + "");
        }
    }

    private void rhMinAdd() {
        int rhMin_add = data_air.getTxtValueRhMin();
        if (rhMin_add < data_air.getTxtValueRhMax()) {
            rhMin_add++;
            data_air.setTxtValueRhMin(rhMin_add);
            txtValueRhMin.setText(rhMin_add + "");
        }
    }

    private void rhMinSub() {
        int rhMin_sub = data_air.getTxtValueRhMin();
        if (rhMin_sub > Data_Air.VALUE_MIN_RH) {
            rhMin_sub--;
            data_air.setTxtValueRhMin(rhMin_sub);
            txtValueRhMin.setText(rhMin_sub + "");
        }
    }

    private void rhMaxAdd() {
        int rhMax_add = data_air.getTxtValueRhMax();
        if (rhMax_add>=Data_Air.VALUE_MAX_RH)
            return;
        rhMax_add++;
        data_air.setTxtValueRhMax(rhMax_add);
        txtValueRhMax.setText(rhMax_add + "");
    }

    private void rhMaxSub() {
        int rhMax_sub = data_air.getTxtValueRhMax();
        if (rhMax_sub > data_air.getTxtValueRhMin()) {
            rhMax_sub--;
            data_air.setTxtValueRhMax(rhMax_sub);
            txtValueRhMax.setText(rhMax_sub + "");
        }
    }

    private void tempMinAdd() {
        int tempMin_add = data_air.getTxtValueTempMin();
        if (tempMin_add < data_air.getTxtValueTempMax()) {
            tempMin_add++;
            data_air.setTxtValueTempMin(tempMin_add);
            txtValueTempMin.setText(tempMin_add + "");
        }
    }

    private void tempMinSub() {
        int tempMin_sub = data_air.getTxtValueTempMin();
        if (tempMin_sub > Data_Air.VALUE_MIN_T) {
            tempMin_sub--;
            data_air.setTxtValueTempMin(tempMin_sub);
            txtValueTempMin.setText(tempMin_sub + "");
        }
    }

    private void tempMaxAdd() {
        int tempMax_add = data_air.getTxtValueTempMax();
        if (tempMax_add>=Data_Air.VALUE_MAX_T)
            return;
        tempMax_add++;
        data_air.setTxtValueTempMax(tempMax_add);
        txtValueTempMax.setText(tempMax_add + "");
    }

    private void tempMaxSub() {
        int tempMax_sub = data_air.getTxtValueTempMax();
        if (tempMax_sub > data_air.getTxtValueTempMin()) {
            tempMax_sub--;
            data_air.setTxtValueTempMax(tempMax_sub);
            txtValueTempMax.setText(tempMax_sub + "");
        }
    }


    @OnClick({R.id.img_select_air_value, R.id.img_select_ipaddr, R.id.img_select_gas_value,
            R.id.layout_select_air, R.id.layout_select_ipline, R.id.layout_select_gas,
            R.id.b_temp_max_sub, R.id.b_temp_max_add,
            R.id.b_pa_max_sub, R.id.b_pa_max_add,
            R.id.b_pa_min_sub, R.id.b_pa_min_add,
            R.id.b_temp_min_sub, R.id.b_temp_min_add,
            R.id.b_rh_max_sub, R.id.b_rh_max_add,
            R.id.b_light_delay_closing_sub, R.id.b_light_delay_closing_add,
            R.id.b_rh_min_sub, R.id.b_rh_min_add,
            R.id.b_delay_off_timer_sub, R.id.b_delay_off_timer_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select_air:
            case R.id.img_select_air_value:
                new PopList(context, data_air.getArrAirValue()).setOnPopListItemClick(new PopList.OnPopListItemClick() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position, String item) {
                        data_air.setTxtAirValue(position);
                        SerialSaunaThread.writeCmdQueue(1,
                                SerialSaunaThread.ADDR_HOST_SLAVE, position==1?0:1);
                        txtAirValue.setText(item);
                        popupWindow.dismiss();
                    }
                }).showAsDropDown(layoutSelectAir);
                break;
            case R.id.layout_select_ipline:
            case R.id.img_select_ipaddr:
                new PopList(context, data_air.getArrIpLine()).setOnPopListItemClick(new PopList.OnPopListItemClick() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position, String item) {
                        data_air.setTxtIpaddr(position);
                        if (position<99&&position>=0){
                            SerialSaunaThread.writeCmdQueue(1,
                                    SerialSaunaThread.ADDR_SLAVE_ADDRESS, position+1);
                        }
                        txtIpaddr.setText(item);
                        popupWindow.dismiss();
                    }
                }).showAsDropDown(layoutSelectIpline);
                break;
            case R.id.layout_select_gas:
            case R.id.img_select_gas_value:
                new PopList(context, data_air.getArrAirValue()).setOnPopListItemClick(new PopList.OnPopListItemClick() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position, String item) {
                        data_air.setTxtGasValue(position);
                        txtGasValue.setText(item);
                        popupWindow.dismiss();
                    }
                }).showAsDropDown(layoutSelectGas);
                break;
//            case R.id.b_temp_max_sub:
//                tempMaxSub();
//                break;
//            case R.id.b_temp_max_add:
//                tempMaxAdd();
//                break;
//            case R.id.b_temp_min_sub:
//                tempMinSub();
//                break;
//            case R.id.b_temp_min_add:
//                tempMinAdd();
//                break;
//            case R.id.b_rh_max_sub:
//                rhMaxSub();
//                break;
//            case R.id.b_rh_max_add:
//                rhMaxAdd();
//                break;
//            case R.id.b_rh_min_sub:
//                rhMinSub();
//
//                break;
//            case R.id.b_rh_min_add:
//                rhMinAdd();
//
//                break;
//            case R.id.b_pa_max_sub:
//                paMaxSub();
//
//                break;
//            case R.id.b_pa_max_add:
//                paMaxAdd();
//
//                break;
//            case R.id.b_pa_min_sub:
//                paMinSub();
//
//                break;
//            case R.id.b_pa_min_add:
//                paMinAdd();
//
//                break;
//
//            case R.id.b_light_delay_closing_sub:
//                lightDelayClosingSub();
//
//                break;
//            case R.id.b_light_delay_closing_add:
//                lightDelayClosingAdd();
//                break;
//
//            case R.id.b_delay_off_timer_sub:
//                delayOffTimerSub();
//
//                break;
//            case R.id.b_delay_off_timer_add:
//                delayOffTimerAdd();
//                break;
        }
    }
}
