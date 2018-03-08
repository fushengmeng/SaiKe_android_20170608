package com.keruiyun.saike.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class PopList extends BasePopupWindow {
    private ListView listView;
    private String[] arrDatas;
    private LayoutInflater infater;
    public PopList(Context context,String[] arrDatast) {
        super(context);
        infater = LayoutInflater.from(context);
        this.arrDatas=arrDatast;
        initView();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(145, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (arrDatas.length>5)
            layoutParams=new LinearLayout.LayoutParams(145, 285);
        listView.setLayoutParams(layoutParams);
        listView.setAdapter(new StringBaseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onPopListItemClick!=null)
                    onPopListItemClick.onItemClick(PopList.this,position,arrDatas[position]);
            }
        });
    }

    @Override
    public int loadContentView() {
        return R.layout.pop_list;
    }

    @Override
    public void initView() {
        super.initView();
        listView=findById(R.id.listview);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(145, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(layoutParams);
    }

    @Override
    public void showAsDropDown(View anchor) {

        super.showAsDropDown(anchor);
    }

    private OnPopListItemClick onPopListItemClick;

    public PopList setOnPopListItemClick(OnPopListItemClick onPopListItemClick) {
        this.onPopListItemClick = onPopListItemClick;
        return this;
    }

    public interface OnPopListItemClick{
        public void onItemClick(PopupWindow popupWindow, int position, String item);
    }

    private class StringBaseAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return arrDatas.length;
        }

        @Override
        public String getItem(int position) {
            return arrDatas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt;
            if (convertView==null){
                convertView = infater.inflate(R.layout.item_text,parent,false);
            }
            txt= (TextView) convertView.findViewById(R.id.txt_item);
            txt.setText(getItem(position)+"");
            txt.setGravity(Gravity.CENTER);
            return convertView;
        }
    }
}
