package com.wifilistconnect.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.wifilistconnect.bean.WifiBean;

import java.util.List;




/**
 * Created by ${GuoZhaoHui} on 2017/11/7.
 * Email:guozhaohui628@gmail.com
 */

public class WifiListAdapter extends BaseAdapter {

    private Context mContext;
    private List<WifiBean> resultList;
    private onItemClickListener onItemClickListener;

    private String wifi_connect,wifi_on_connecting,wifi_unconnect;
    LayoutInflater inflater;

    public void setOnItemClickListener(WifiListAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WifiListAdapter(Context mContext, List<WifiBean> resultList) {
        this.mContext = mContext;
        this.resultList = resultList;
        wifi_connect=mContext.getResources().getString(R.string.wifi_connect);
        wifi_on_connecting=mContext.getResources().getString(R.string.wifi_on_connecting);
        wifi_unconnect=mContext.getResources().getString(R.string.wifi_unconnect);
        inflater = LayoutInflater.from(mContext);
    }


    public void replaceAll(List<WifiBean> datas) {
        if (resultList.size() > 0) {
            resultList.clear();
        }
        resultList.addAll(datas);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public WifiBean getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_wifi_list, parent, false);
            viewHolder=new ViewHolder(convertView,position);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.refresh(position);
        return convertView;
    }


    class ViewHolder{

        View itemview;
        TextView tvItemWifiName, tvItemWifiStatus;
        ImageView iconWifi;
        private int position;
        WifiBean bean;
        public ViewHolder(View itemView,int positionItem) {
            itemview = itemView;
            position=positionItem;
            bean=getItem(positionItem);
            tvItemWifiName = (TextView) itemView.findViewById(R.id.tv_item_wifi_name);
            tvItemWifiStatus = (TextView) itemView.findViewById(R.id.tv_item_wifi_status);
            iconWifi = (ImageView) itemView.findViewById(R.id.icon_wifi);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view,position,bean);
                }
            });
            refresh(positionItem);
        }

        private void refresh(int positionItem){
            position=positionItem;
            bean = resultList.get(position);
            tvItemWifiName.setText(bean.getWifiName());
            tvItemWifiStatus.setText(bean.getState()+"");

            //可以传递给adapter的数据都是经过处理的，已连接或者正在连接状态的wifi都是处于集合中的首位，所以可以写出如下判断
            if(position == 0  && (wifi_on_connecting.equals(bean.getState()) || wifi_connect.equals(bean.getState()))){
                tvItemWifiName.setTextColor(mContext.getResources().getColor(R.color.aquamarine));
                tvItemWifiStatus.setTextColor(mContext.getResources().getColor(R.color.aquamarine));
            }else{
                tvItemWifiName.setTextColor(mContext.getResources().getColor(R.color.white));
                tvItemWifiStatus.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            switch (bean.getLevel()){
                case "5":
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_5);
                    break;
                case "4":
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_4);
                    break;
                case "3":
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_3);
                    break;
                case "2":
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_2);
                    break;
                case "1":
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_1);
                    break;
                default:
                    iconWifi.setImageResource(R.drawable.sk_ic_wifi_0);
            }


        }

    }

    public interface onItemClickListener{
        void onItemClick(View view, int postion, Object o);
    }

}
