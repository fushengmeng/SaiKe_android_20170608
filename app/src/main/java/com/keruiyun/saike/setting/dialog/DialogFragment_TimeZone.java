package com.keruiyun.saike.setting.dialog;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.controls.MyListView;
import com.keruiyun.saike.setting.data.Data_Advanced;
import com.keruiyun.saike.setting.util.TimeZoneSetting;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 */

public class DialogFragment_TimeZone extends BaseSettingDialog {


    @BindView(R.id.listview)
    ListView listview;

    //存放时区信息的HashMap
    private HashMap<String, String> map = new HashMap<String, String>();

    //这个数组只存放时区名，用于列表显示
    private ArrayList<String> list = new ArrayList<String>();

    private TimeZoneSetting timeZoneSetting;


    @Override
    public int loadContentView() {
        return R.layout.layout_list;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setTxtTitle(R.string.select_timezone);
        getdata();
        timeZoneSetting=new TimeZoneSetting(mContext);
        listview.setAdapter(new StringAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                if (timeZoneSetting.isTimeZoneAuto()){
//                    timeZoneSetting.setAutoTimeZone(0);
//                }
                timeZoneSetting.setTimeZone(map.get(list.get(position)));
                dismiss();

            }
        });
    }

    @Override
    public void initTopView(View topView) {
        super.initTopView(topView);
        topView.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    //一次次的从xml文件获取信息
    public void getdata()
    {
        try {
//将上次的数据清空，方便重新搜索
            map.clear();
            list.clear();
//获取信息的方法
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.timezones);
//判断是否已经到了文件的末尾
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if (name.equals("timezone")) {
//关键词搜索，如果匹配，那么添加进去如果不匹配就不添加，如果没输入关键词“”,那么默认搜索全部
                        if(xrp.getAttributeValue(1).indexOf("") != -1)
                        {
//0，标识id，1标识名称
                            map.put(xrp.getAttributeValue(1),
                                    xrp.getAttributeValue(0));
                            list.add(xrp.getAttributeValue(1));
                        }
                    }
                }
//搜索过第一个信息后，接着搜索下一个
                xrp.next();
            }

        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class StringAdapter extends BaseAdapter {

        LayoutInflater inflater;
        int colorYes,colorNo;

        public StringAdapter() {

            inflater = LayoutInflater.from(mContext);
            colorYes= ContextCompat.getColor(mContext,R.color.theme_color_primary);
            colorNo= ContextCompat.getColor(mContext,android.R.color.transparent);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_text, parent, false);
            }
            TextView txt = (TextView) convertView.findViewById(R.id.txt_item);
            txt.setText(getItem(position));
            txt.setPadding(32, 16, 16, 16);
            return convertView;
        }
    }
}
