package com.keruiyun.saike.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.keruiyun.saike.AppData;
import com.keruiyun.saike.PeerListAdapter;
import com.keruiyun.saike.R;
import com.keruiyun.saike.main.AirParamsListAdapter;
import com.keruiyun.saike.model.PeerModel;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.util.Consts;
import com.keruiyun.saike.util.DrawableUtil;
import com.keruiyun.saike.util.TypedValueUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 空调系统
 */

@Deprecated
public class DialogFragment_Air extends BaseDialogFragment {
    private ArrayList<View> arrowList = new ArrayList<View>();

    private int currentArrow = -1, currentArrow1 = -1, currentArrow3 = -1,
            currentArrow4 = -1, currentArrow5 = -1, currentArrow6 = -1;
    private BroadcastReceiver broadcastReceiver, broadcastReceiver2;

    private ImageView ivFan,ivBack;
    private TextView tvTemp, tvTemp1, tvHum, tvHum1, tvPress;
    private LinearLayout layoutAirParams;
    private ListView listViewAirParams;
    private AirParamsListAdapter airParamsListAdapter;

    private ArrayList<View> arrowList1 = new ArrayList<View>();
    private ArrayList<View> arrowList3 = new ArrayList<View>();
    private ArrayList<View> arrowList4 = new ArrayList<View>();
    private ArrayList<View> arrowList5 = new ArrayList<View>();
    private ArrayList<View> arrowList6 = new ArrayList<View>();
    private Animation operatingAnim;
    private int isOpSwitchOpen = 0, folloAddr, sleepTime = 0;
    private ImageView ivA11, ivA31, ivA41, ivA51;
    private int host = 1;
    private PeerListAdapter _adapter;
    private ListView _listView;


    //public ZoomView zoomView;
    private ViewHolderAirBottom[] airBottom=new ViewHolderAirBottom[3];
    private ViewHolderAirRight[] airRight=new ViewHolderAirRight[3];

    @Override
    public int loadContentView() {
        return R.layout.layout_air;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        //zoomView = (ZoomView)view;

        initViewUi(view);
        initData();
        registerBroadCast();
        setListener();

        mHandler.sendEmptyMessageDelayed(100, 500);
        mHandler.sendEmptyMessageDelayed(111, 500);
        mHandler.sendEmptyMessageDelayed(113, 500);
        mHandler.sendEmptyMessageDelayed(114, 500);
        mHandler.sendEmptyMessageDelayed(115, 500);
        mHandler.sendEmptyMessageDelayed(116, 500);
    }


    // 初始化View
    private void initViewUi(View view) {
        airBottom[0]=new ViewHolderAirBottom(0,findById(R.id.layout_air_launch));
        airBottom[1]=new ViewHolderAirBottom(1,findById(R.id.layout_air__pa));
        airBottom[2]=new ViewHolderAirBottom(2,findById(R.id.layout_air_duty));
        airRight[0]=new ViewHolderAirRight(0,findById(R.id.layout_air_runparams));
        airRight[1]=new ViewHolderAirRight(1,findById(R.id.layout_air_runstatus));
        airRight[2]=new ViewHolderAirRight(2,findById(R.id.layout_air_alarm));
        ivBack=findById(R.id.img_back);
        layoutAirParams=findById(R.id.layout_air_params);
        listViewAirParams=findById(R.id.listview_air_params);

        View v1 = (View) view.findViewById(R.id.air_ll_10);
        View v3 = (View) view.findViewById(R.id.air_ll_11);
        View v4 = (View) view.findViewById(R.id.air_ll_13);
        View v7 = (View) view.findViewById(R.id.air_ll_14);

        arrowList.add(v1);
        arrowList.add(v3);
        arrowList.add(v4);
        arrowList.add(v7);


        ivFan = (ImageView) view.findViewById(R.id.ImageView14);

        tvTemp = (TextView) view.findViewById(R.id.textView1);
        tvTemp1 = (TextView) view.findViewById(R.id.textView3);
        tvHum = (TextView) view.findViewById(R.id.textView4);
        tvHum1 = (TextView) view.findViewById(R.id.textView5);


        View v10 = (View) view.findViewById(R.id.iv1);
        View v30 = (View) view.findViewById(R.id.iv3);
        arrowList1.add(v10);
        arrowList1.add(v30);

        View v40 = (View) view.findViewById(R.id.iv4);
        View v50 = (View) view.findViewById(R.id.iv5);
        View v60 = (View) view.findViewById(R.id.iv6);
        View v70 = (View) view.findViewById(R.id.iv7);
        arrowList3.add(v40);
        arrowList3.add(v50);
        arrowList3.add(v60);
        arrowList3.add(v70);

        View v11 = (View) view.findViewById(R.id.iv11);
        View v13 = (View) view.findViewById(R.id.iv13);
        View v14 = (View) view.findViewById(R.id.iv14);
        arrowList4.add(v11);
        arrowList4.add(v13);
        arrowList4.add(v14);

        View v31 = (View) view.findViewById(R.id.iv31);
        View v33 = (View) view.findViewById(R.id.iv33);
        View v34 = (View) view.findViewById(R.id.iv34);
        arrowList5.add(v31);
        arrowList5.add(v33);
        arrowList5.add(v34);

        View v41 = (View) view.findViewById(R.id.iv41);
        View v43 = (View) view.findViewById(R.id.iv43);
        View v44 = (View) view.findViewById(R.id.iv44);
        arrowList6.add(v41);
        arrowList6.add(v43);
        arrowList6.add(v44);

        tvPress = (TextView) view.findViewById(R.id.textView6);

        ivA11 = (ImageView) view.findViewById(R.id.air_iv_alarm_11);
        ivA31 = (ImageView) view.findViewById(R.id.air_iv_alarm_31);
        ivA41 = (ImageView) view.findViewById(R.id.air_iv_alarm_41);
        ivA51 = (ImageView) view.findViewById(R.id.air_iv_alarm_51);


        _listView = (ListView) view.findViewById(R.id.peerListView);
    }

    private void initData() {
        operatingAnim = AnimationUtils.loadAnimation(this.getActivity(), R.anim.fan);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        ivFan.startAnimation(operatingAnim);
        airParamsListAdapter=new AirParamsListAdapter(mContext,0);
        listViewAirParams.setAdapter(airParamsListAdapter);

        _adapter = new PeerListAdapter(this.getActivity());
        _listView.setAdapter(_adapter);
    }

    private void setListener() {
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        _listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                try {
                    final PeerModel peer = AppData.getPeerArray().get(arg2);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(DialogFragment_Air.this.getActivity());
                    dialog.setTitle("是否与" + peer.name + "通话？");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            showChatFragment(peer);
                        }
                    });
                    dialog.setNegativeButton("取消", null);
                    dialog.create().show();
                } catch (Exception ex) {
                }
            }
        });
    }

    private void airLaunch(){
        if (isOpSwitchOpen == 0) {
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 1);
            isOpSwitchOpen = 1;
            airBottom[0].setIsSelect(true);
        } else if (isOpSwitchOpen == 1) {
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 0);
            isOpSwitchOpen = 0;
            airBottom[0].setIsSelect(false);
        }
    }

    protected void displayStatusBar() {
        if (!Consts.IS_STATUS_BAR) {
            Intent intent = new Intent("com.android.action.display_statusbar");
            this.getActivity().sendBroadcast(intent);
            Intent intent2 = new Intent("com.android.action.display_navigationbar");
            this.getActivity().sendBroadcast(intent2);
            Consts.IS_STATUS_BAR = true;
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (currentArrow < arrowList.size() - 1) {
                        ++currentArrow;
                    } else {
                        currentArrow = 0;
                    }
                    arrowslow();
                    mHandler.sendEmptyMessageDelayed(100, 500);
                    break;
                case 101:
                    // if (ivA1.getVisibility() == View.VISIBLE) {
                    // ivA1.setVisibility(View.GONE);
                    // } else {
                    // ivA1.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(101, 1000);
                    break;
                case 103:
                    // if (ivA3.getVisibility() == View.VISIBLE) {
                    // ivA3.setVisibility(View.GONE);
                    // } else {
                    // ivA3.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(103, 1000);
                    break;
                case 104:
                    // if (ivA4.getVisibility() == View.VISIBLE) {
                    // ivA4.setVisibility(View.GONE);
                    // } else {
                    // ivA4.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(104, 1000);
                    break;
                case 105:
                    // if (ivA5.getVisibility() == View.VISIBLE) {
                    // ivA5.setVisibility(View.GONE);
                    // } else {
                    // ivA5.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(105, 1000);
                    break;
                case 106:
                    // if (ivHeat1.getVisibility() == View.VISIBLE) {
                    // ivHeat1.setVisibility(View.GONE);
                    // } else {
                    // ivHeat1.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(106, 1000);
                    break;
                case 107:
                    // if (ivHeat3.getVisibility() == View.VISIBLE) {
                    // ivHeat3.setVisibility(View.GONE);
                    // } else {
                    // ivHeat3.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(107, 1000);
                    break;
                case 108:
                    // if (ivHeat4.getVisibility() == View.VISIBLE) {
                    // ivHeat4.setVisibility(View.GONE);
                    // } else {
                    // ivHeat4.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(108, 1000);
                    break;
                case 109:
                    // if (ivTempHigh.getVisibility() == View.VISIBLE) {
                    // ivTempHigh.setVisibility(View.GONE);
                    // } else {
                    // ivTempHigh.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(109, 1000);
                    break;
                case 110:
                    // if (ivHum.getVisibility() == View.VISIBLE) {
                    // ivHum.setVisibility(View.GONE);
                    // } else {
                    // ivHum.setVisibility(View.VISIBLE);
                    // }
                    // mHandler.sendEmptyMessageDelayed(110, 1000);
                    break;
                case 111:
                    if (currentArrow1 < arrowList1.size() - 1) {
                        ++currentArrow1;
                    } else {
                        currentArrow1 = 0;
                    }
                    arrowslow1();
                    mHandler.sendEmptyMessageDelayed(111, 500);
                    break;
                case 113:
                    if (currentArrow3 < arrowList3.size() - 1) {
                        ++currentArrow3;
                    } else {
                        currentArrow3 = 0;
                    }
                    arrowslow3();
                    mHandler.sendEmptyMessageDelayed(113, 500);
                    break;
                case 114:
                    if (currentArrow4 < arrowList4.size() - 1) {
                        ++currentArrow4;
                    } else {
                        currentArrow4 = 0;
                    }
                    arrowslow4();
                    mHandler.sendEmptyMessageDelayed(114, 500);
                    break;
                case 115:
                    if (currentArrow5 < arrowList5.size() - 1) {
                        ++currentArrow5;
                    } else {
                        currentArrow5 = 0;
                    }
                    arrowslow5();
                    mHandler.sendEmptyMessageDelayed(115, 500);
                    break;
                case 116:
                    if (currentArrow6 < arrowList6.size() - 1) {
                        ++currentArrow6;
                    } else {
                        currentArrow6 = 0;
                    }
                    arrowslow6();
                    mHandler.sendEmptyMessageDelayed(116, 500);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private void arrowslow() {
        for (int i = 0; i < arrowList.size(); i++) {
            if (i == currentArrow) {
                arrowList.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslow1() {
        for (int i = 0; i < arrowList1.size(); i++) {
            if (i == currentArrow1) {
                arrowList1.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList1.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslow3() {
        for (int i = 0; i < arrowList3.size(); i++) {
            if (i == currentArrow3) {
                arrowList3.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList3.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslow4() {
        for (int i = 0; i < arrowList4.size(); i++) {
            if (i == currentArrow4) {
                arrowList4.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList4.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslow5() {
        for (int i = 0; i < arrowList5.size(); i++) {
            if (i == currentArrow5) {
                arrowList5.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList5.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslow6() {
        for (int i = 0; i < arrowList6.size(); i++) {
            if (i == currentArrow6) {
                arrowList6.get(i).setVisibility(View.VISIBLE);
            } else {
                arrowList6.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void arrowslowStop() {
        ivFan.clearAnimation();
        currentArrow = -1;
        currentArrow1 = -1;
        currentArrow3 = -1;
        currentArrow4 = -1;
        currentArrow5 = -1;
        currentArrow6 = -1;

        for (int i = 0; i < arrowList.size(); i++) {
            arrowList.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < arrowList1.size(); i++) {
            arrowList1.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < arrowList3.size(); i++) {
            arrowList3.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < arrowList4.size(); i++) {
            arrowList4.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < arrowList5.size(); i++) {
            arrowList5.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < arrowList6.size(); i++) {
            arrowList6.get(i).setVisibility(View.GONE);
        }
    }

    private void registerBroadCast() {
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                int addr = arg1.getIntExtra("addr", -1);
                short data = arg1.getShortExtra("data", (short) -1);
                switch (addr) {
                    case SerialSaunaThread.ADDR_STATUS:
                        // 机组状态-系统运行
                        mHandler.removeCallbacksAndMessages(null);
                        int systemRunning = data & 0x01;
                        if (systemRunning == 1) {
                            mHandler.sendEmptyMessage(100);
                            mHandler.sendEmptyMessage(111);
                            mHandler.sendEmptyMessage(113);
                            mHandler.sendEmptyMessage(114);
                            mHandler.sendEmptyMessage(115);
                            mHandler.sendEmptyMessage(116);

                            ivFan.startAnimation(operatingAnim);
                        } else {
                            mHandler.removeMessages(100);
                            mHandler.removeMessages(111);
                            mHandler.removeMessages(113);
                            mHandler.removeMessages(114);
                            mHandler.removeMessages(115);
                            mHandler.removeMessages(116);

                            arrowslowStop();
                        }
                        // 机组状态-值班运行
                        int waitRunning = (data >> 1) & 0x01;
                        // 机组状态-系统故障
                        int alarmSystem = (data >> 2) & 0x01;
                        // 机组状态-初效报警
                        int alarm1 = (data >> 3) & 0x01;
                        if (alarm1 == 1) {
                            // mHandler.sendEmptyMessage(101);
                            ivA11.setImageResource(R.drawable.st_error);
                        } else {
                            // mHandler.removeMessages(101);
                            // ivA1.setVisibility(View.VISIBLE);
                            ivA11.setImageResource(R.drawable.st_on);
                        }
                        // 机组状态-中效报警
                        int alarm2 = (data >> 4) & 0x01;
                        if (alarm2 == 1) {
                            // mHandler.sendEmptyMessage(104);
                            ivA41.setImageResource(R.drawable.st_error);
                        } else {
                            // mHandler.removeMessages(104);
                            // ivA4.setVisibility(View.VISIBLE);
                            ivA41.setImageResource(R.drawable.st_on);
                        }
                        // 机组状态-高效报警
                        int alarm3 = (data >> 5) & 0x01;
                        if (alarm3 == 1) {
                            // mHandler.sendEmptyMessage(105);
                            ivA51.setImageResource(R.drawable.st_error);
                        } else {
                            // mHandler.removeMessages(105);
                            // ivA5.setVisibility(View.VISIBLE);
                            ivA51.setImageResource(R.drawable.st_on);
                        }
                        // 机组状态-缺风报警
                        int alarmwind = (data >> 6) & 0x01;
                        if (alarmwind == 1) {
                            // mHandler.sendEmptyMessage(103);
                            ivA31.setImageResource(R.drawable.st_error);
                        } else {
                            // mHandler.removeMessages(103);
                            // ivA3.setVisibility(View.VISIBLE);
                            ivA31.setImageResource(R.drawable.st_on);
                        }
                        // 机组状态-高温报警
                        int alarmTemp = (data >> 7) & 0x01;
                        if (alarmTemp == 1) {
                            // mHandler.sendEmptyMessage(109);
                        } else {
                            // mHandler.removeMessages(109);
                            // ivTempHigh.setVisibility(View.VISIBLE);
                        }
                        // 机组状态-电加热1运行
                        int elec1 = (data >> 8) & 0x01;
                        if (elec1 == 1) {
                            // mHandler.sendEmptyMessage(106);
                        } else {
                            // mHandler.removeMessages(106);
                            // ivHeat1.setVisibility(View.VISIBLE);
                        }
                        // 机组状态-电加热2运行
                        int elec2 = (data >> 9) & 0x01;
                        if (elec2 == 1) {
                            // mHandler.sendEmptyMessage(107);
                        } else {
                            // mHandler.removeMessages(107);
                            // ivHeat3.setVisibility(View.VISIBLE);
                        }
                        // 机组状态-电加热3运行
                        int elec3 = (data >> 10) & 0x01;
                        if (elec3 == 1) {
                            // mHandler.sendEmptyMessage(108);
                        } else {
                            // mHandler.removeMessages(108);
                            // ivHeat4.setVisibility(View.VISIBLE);
                        }
                        // 机组状态-加湿运行
                        int hum = (data >> 11) & 0x01;
                        if (hum == 1) {
                            // mHandler.sendEmptyMessage(110);
                        } else {
                            // mHandler.removeMessages(110);
                            // ivHum.setVisibility(View.VISIBLE);
                        }
                        break;
                    case SerialSaunaThread.ADDR_AIR_TEMPERATURE:
//					if ((data / 10.0f) % 1.0 == 0) {
//						tvTemp.setText(String
//								.format("%d", (int) (data / 10.0f)));
//					} else {
                        tvTemp.setText(String.format("%.1f", data / 10.0f));
                        //}
                        break;
                    case SerialSaunaThread.ADDR_AIR_HUMIDITY:
//					if ((data / 10.0f) % 1.0 == 0) {
//						tvTemp1.setText(String.format("%d",
//								(int) (data / 10.0f)));
//					} else {
                        tvTemp1.setText(String.format("%.1f", data / 10.0f));
                        //}
                        break;
                    case SerialSaunaThread.ADDR_SET_TEMPERATURE:
                        tvHum.setText(String.valueOf(data));
                        break;
                    case SerialSaunaThread.ADDR_SET_HUMIDITY:
                        tvHum1.setText(String.valueOf(data));
                        break;
                    case SerialSaunaThread.ADDR_ROOM_PRESSURE:
//					if ((data / 10.0f) % 1.0 == 0) {
//						tvPress.setText(String.format("%d Pa",
//								(int) (data / 10.0f)));
//					} else {
                        tvPress.setText(String.format("%.1f Pa", data / 10.0f));
                        //}
                        break;
                    case SerialSaunaThread.ADDR_POWER_KEY:
                        isOpSwitchOpen = data;
                        opSwitchChange();
                        break;
                    case SerialSaunaThread.ADDR_HOST_SLAVE:
                        host = data;
                        break;
                    case SerialSaunaThread.ADDR_SLAVE_ADDRESS:
                        folloAddr = data;
                        break;
                    case SerialSaunaThread.ADDR_LIGHT_OFF_SET:
                        sleepTime = data;
                        break;
                    default:
                        break;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.order");
        this.getActivity().registerReceiver(broadcastReceiver, intentFilter);

        broadcastReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                _adapter.notifyDataSetChanged();
            }
        };

        IntentFilter intentFilter2 = new IntentFilter("com.keruiyun.saike.peers");
        this.getActivity().registerReceiver(broadcastReceiver2, intentFilter2);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (null != broadcastReceiver) {
            try {
                this.getActivity().unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        if (null != broadcastReceiver2) {
            try {
                this.getActivity().unregisterReceiver(broadcastReceiver2);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    private void opSwitchChange() {
        if (isOpSwitchOpen == 0) {
            airBottom[0].setIsSelect(false);
        } else if (isOpSwitchOpen == 1) {
            airBottom[0].setIsSelect(true);
        }
    }

    private void airSetting() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                this.getActivity());
        View contentView = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dialog_air_setting, null);
        final RadioGroup rg = (RadioGroup) contentView.findViewById(R.id.radioGroup1);
        if (1 == host) {
            rg.check(R.id.radio10);
        } else {
            rg.check(R.id.radio11);
        }

        final EditText et = (EditText) contentView.findViewById(R.id.editText1);
        et.setText(String.valueOf(folloAddr));
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                switch (arg1) {
                    case R.id.radio10:
                        break;
                    case R.id.radio11:
                        break;
                    default:
                        break;
                }
            }
        });

        dialog.setTitle("空调通信设置");
        dialog.setView(contentView);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                int addr = -1;
                try {
                    addr = Integer.parseInt(et.getText().toString().trim());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (addr >= 0 && addr <= 99) {
                    if (rg.getCheckedRadioButtonId() == R.id.radio11) {
                        SerialSaunaThread.writeCmdQueue(1,
                                SerialSaunaThread.ADDR_HOST_SLAVE, 0);
                        host = 0;
                    } else {
                        SerialSaunaThread.writeCmdQueue(1,
                                SerialSaunaThread.ADDR_HOST_SLAVE, 1);
                        host = 1;
                    }

                    SerialSaunaThread.writeCmdQueue(1,
                            SerialSaunaThread.ADDR_SLAVE_ADDRESS, addr);
                    arg0.dismiss();
                } else {
                    Toast.makeText(DialogFragment_Air.this.getActivity(), "从站地址范围：0-99",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.create().show();
    }

    private void sleepSetting() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                this.getActivity());
        View contentView = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dialog_sleep_setting, null);
        final EditText et = (EditText) contentView.findViewById(R.id.editText1);
        et.setText(String.valueOf(sleepTime));

        dialog.setTitle("照明延时时间设置");
        dialog.setView(contentView);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                int addr = 0;
                try {
                    addr = Integer.parseInt(et.getText().toString().trim());
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (addr >= 0 && addr <= 250) {
                    SerialSaunaThread.writeCmdQueue(1,
                            SerialSaunaThread.ADDR_LIGHT_OFF_SET, addr);
                } else {
                    Toast.makeText(DialogFragment_Air.this.getActivity(), "照明延时时间范围：0-250",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.create().show();
    }

    private void showChatFragment(PeerModel peer) {
        ChatFragment fragment = new ChatFragment(peer, false);
        fragment.show(this.getActivity().getSupportFragmentManager(), "ChatFragment");
    }

    int[] airBottomSrcYes=new int[]{R.drawable.sk_ktxtjm_31,R.drawable.sk_ktxtjm_32,R.drawable.sk_ktxtjm_33};
    int[] airBottomSrcNo=new int[]{R.drawable.sk_ktxtjm_22,R.drawable.sk_ktxtjm_24,R.drawable.sk_ktxtjm_26};
    class ViewHolderAirBottom {
        @BindView(R.id.img_air_launch)
        ImageView imgAirLaunch;
        @BindView(R.id.txt_air_launch)
        TextView txtAirLaunch;
        View view;
        int type;
        Resources resources;
        int colorYes,colorNo;

        ViewHolderAirBottom(final int type, View view) {
            ButterKnife.bind(this, view);
            resources=getResources();
            this.type=type;
            this.view=view;
            colorYes= TypedValueUtil.getColorValue(getActivity(),R.attr.custom_attr_color_txt_primary);
            colorNo= TypedValueUtil.getColorValue(getActivity(),R.attr.custom_attr_color_txt_primaryDark);
            view.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawableOval(view));
            imgAirLaunch.setImageResource(airBottomSrcNo[type]);
            txtAirLaunch.setTextColor(colorNo);
            txtAirLaunch.setText(resources.getStringArray(R.array.arr_air_bottom)[type]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                    switch (type){
                        case 0:
                            airLaunch();
                            break;
                        case 1:
                            airSetting();
                            airBottom[type].setIsSelect(!v.isSelected());
                            break;
                        case 2:
                            sleepSetting();
                            airBottom[type].setIsSelect(!v.isSelected());
                            break;
                    }
                }
            });
        }

        void setIsSelect(boolean isSelect){
            view.setSelected(isSelect);
            imgAirLaunch.setImageResource(isSelect?airBottomSrcYes[type]:airBottomSrcNo[type]);
            txtAirLaunch.setTextColor(isSelect?colorYes:colorNo);
        }
    }


    int[] airRightSrcYes=new int[]{R.drawable.sk_ktxtjm_05,R.drawable.sk_ktxtjm_14,R.drawable.sk_ktxtjm_18};
//    int[] airRightSrcNo=new int[]{R.drawable.sk_ktxtjm_03,R.drawable.sk_ktxtjm_13,R.drawable.sk_ktxtjm_17};
    class ViewHolderAirRight {
        @BindView(R.id.img_air_runparams)
        TintImageView imgAirRunparams;
        @BindView(R.id.txt_air_runparams)
        TextView txtAirRunparams;
        View view;
        int type;
        Resources resources;
        int colorYes,colorNo;

        ViewHolderAirRight(final int type, View view) {
            ButterKnife.bind(this, view);
            resources=getResources();
            this.type=type;
            this.view=view;
            colorYes= TypedValueUtil.getColorValue(getActivity(),R.attr.custom_attr_color_txt_primary);
            colorNo= TypedValueUtil.getColorValue(getActivity(),R.attr.custom_attr_color_txt_primaryDark);
            view.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawable(view));
            imgAirRunparams.setImageResource(airRightSrcYes[type]);
            setIsSelect(type==0);
            txtAirRunparams.setTextColor(colorNo);
            txtAirRunparams.setText(resources.getStringArray(R.array.arr_air_right)[type]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    for (int i=0;i<airBottom.length;i++){
//                        airRight[i].setIsSelect(false);
//                    }

                    switch (type){
                        case 0:
                            boolean isSelect=v.isSelected();
                            layoutAirParams.setVisibility(isSelect?View.GONE:View.VISIBLE);
                            airRight[type].setIsSelect(!isSelect);
                            break;
                    }

                }
            });
        }

        void setIsSelect(boolean isSelect){
            view.setSelected(isSelect);
            imgAirRunparams.setImageTintList(isSelect?R.color.white:R.color.img_no_select);
            txtAirRunparams.setTextColor(isSelect?colorYes:colorNo);
        }
    }
}
