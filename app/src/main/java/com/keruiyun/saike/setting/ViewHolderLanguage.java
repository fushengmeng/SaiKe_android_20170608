package com.keruiyun.saike.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.keruiyun.saike.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----语言
 */

public class ViewHolderLanguage extends BaseViewHolder {

    @BindView(R.id.listview_lan)
    ListView listviewLan;

    public ViewHolderLanguage(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_language;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        listviewLan.setAdapter(new LanguageAdapter());
        listviewLan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private class LanguageAdapter extends BaseAdapter{
        String[] datasArr;
        LayoutInflater inflater;

        public LanguageAdapter() {
            inflater=LayoutInflater.from(context);
            this.datasArr = context.getResources().getStringArray(R.array.arr_language);
        }

        @Override
        public int getCount() {
            return datasArr.length;
        }

        @Override
        public String getItem(int position) {
            return datasArr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView==null){
                convertView=inflater.inflate(R.layout.item_text,parent,false);
            }
            TextView txt= (TextView) convertView.findViewById(R.id.txt_item);
            txt.setText(getItem(position)+"");
            return convertView;
        }
    }
}
