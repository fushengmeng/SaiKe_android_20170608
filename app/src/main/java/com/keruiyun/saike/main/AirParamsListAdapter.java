package com.keruiyun.saike.main;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keruiyun.saike.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/28.
 */

public class AirParamsListAdapter extends BaseAdapter {
    String[] paramsKeyParams;
    String[] paramsKeyRun;
    String[] paramsKeyWarning;
    String[] paramsUnit;

    Map<Integer,String> valuesRunParams;
    Map<Integer,String> valuesRunSataus;
    Map<Integer,String> valuesRunAlarm;
    LayoutInflater inflater;

    int type;

    public AirParamsListAdapter(Context mContext,int type) {
        this.type=type;
        Resources resources=mContext.getResources();
        paramsKeyParams = resources.getStringArray(R.array.arr_air_params);
        paramsKeyRun = resources.getStringArray(R.array.arr_air_params_run);
        paramsKeyWarning = resources.getStringArray(R.array.arr_air_params_warning);
        paramsUnit= resources.getStringArray(R.array.arr_air_params_unit);



        inflater = LayoutInflater.from(mContext);
        valuesRunParams=new HashMap<Integer, String>();
        valuesRunSataus=new HashMap<Integer, String>();
        valuesRunAlarm=new HashMap<Integer, String>();
        String defaultValue="";
        for (int i=0;i<paramsKeyParams.length;i++)
            valuesRunParams.put(i,defaultValue);
//        defaultValue=resources.getString(R.string.status_normal);
        for (int i=0;i<paramsKeyRun.length;i++)
            valuesRunSataus.put(i,defaultValue);
//        defaultValue=resources.getString(R.string.run);
        for (int i=0;i<paramsKeyWarning.length;i++)
            valuesRunAlarm.put(i,defaultValue);
    }

    private int getSize(int type){
        int size=0;
        switch (type){
            case 0:
                size=paramsKeyParams.length;
                break;
            case 1:
                size=paramsKeyRun.length;
                break;
            case 2:
                size=paramsKeyWarning.length;
                break;

        }
        return size;
    }

    /**
     * @param type  显示类型
     *              0---运行参数
     *              1---运行状态
     *              2---报警状态
     * */
    public void refreshType(int type){
        this.type=type;
        notifyDataSetChanged();
    }
    /**
     * type  显示类型
     *              0---运行参数
     *              1---运行状态
     *              2---报警状态
     *
     *@param typeR 当前显示类型
     *
     * @param typeValue 参数类型
     *  */
    public void refreshItem(int typeR,int typeValue,int position,String value){

        switch (typeValue){
            case 0:
                valuesRunParams.put(position,value);
                break;
            case 1:
                valuesRunSataus.put(position,value);
                break;
            case 2:
                valuesRunAlarm.put(position,value);
                break;

        }
        if (type!=typeR){
            return;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return getSize(type);
    }

    @Override
    public String getItem(int position) {
        String key="";
        switch (type){
            case 0:
                key=paramsKeyParams[position];
                break;
            case 1:
                key=paramsKeyRun[position];
                break;
            case 2:
                key=paramsKeyWarning[position];
                break;

        }
        return key;
    }

    public String getItemValue(int position) {
        String value="";
        switch (type){
            case 0:
                value=valuesRunParams.get(position);
                if (!TextUtils.isEmpty(value))
                    value=value+paramsUnit[position];
                break;
            case 1:
                value=valuesRunSataus.get(position);
                break;
            case 2:
                value=valuesRunAlarm.get(position);
                break;

        }
        return value;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_airparams,parent,false);
            viewHolder.txtKey= (TextView) convertView.findViewById(R.id.txt_key);
            viewHolder.txtValue= (TextView) convertView.findViewById(R.id.txt_value);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.txtKey.setText(getItem(position));
        viewHolder.txtValue.setText(""+getItemValue(position));
        return convertView;
    }

    private class ViewHolder{
        TextView txtKey,txtValue;
    }
}
