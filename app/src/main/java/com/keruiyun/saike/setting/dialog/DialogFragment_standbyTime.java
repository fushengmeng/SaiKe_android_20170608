package com.keruiyun.saike.setting.dialog;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.keruiyun.saike.R;
import com.keruiyun.saike.controls.MyListView;
import com.keruiyun.saike.setting.data.Data_Advanced;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 */

public class DialogFragment_standbyTime extends BaseSettingDialog {


    @BindView(R.id.listview)
    MyListView listview;

    private int currPosition= Data_Advanced.getStandbyTimePositon();


    @Override
    public int loadContentView() {
        return R.layout.layout_mylist;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setTxtTitle(R.string.setting_stand_by_time);
        listview.setAdapter(new StringAdapter());
    }

    @Override
    public void initTopView(View topView) {
        super.initTopView(topView);
        topView.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private class StringAdapter extends BaseAdapter {
        String[] arrString;
        LayoutInflater inflater;
        int colorYes,colorNo;

        public StringAdapter() {
            this.arrString = getResources().getStringArray(R.array.arr_standby_time);
            inflater = LayoutInflater.from(mContext);
//            colorYes= ContextCompat.getColor(mContext,R.color.theme_color_primary);
            colorYes= ThemeUtils.replaceColorById(mContext,R.color.theme_color_primary);
            colorNo= ContextCompat.getColor(mContext,android.R.color.transparent);
        }

        @Override
        public int getCount() {
            return arrString.length;
        }

        @Override
        public String getItem(int position) {
            return arrString[position];
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
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) txt.getLayoutParams();
            lp.setMargins(1,0,1,1);
            txt.setText(getItem(position));
            txt.setPadding(32, 16, 16, 16);
            txt.setBackgroundColor(currPosition==position?colorYes:colorNo);

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currPosition=position;
                    Data_Advanced.setStandbyTimePositon(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}
