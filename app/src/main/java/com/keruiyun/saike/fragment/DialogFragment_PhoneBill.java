package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintLinearLayout;
import com.keruiyun.db.BeanPhone;
import com.keruiyun.db.T_M_PhoneCall;
import com.keruiyun.db.T_M_PhoneVideo;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.model.PeerModel;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.uiview.SaikeScollBar;
import com.keruiyun.saike.util.LogCus;
import com.music.musicplayer.utility.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 *
 */

@SuppressLint("ValidFragment")
public class DialogFragment_PhoneBill extends BaseDialogFragment {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.scollbar)
    SaikeScollBar saikeScollBar;

    T_M_PhoneVideo t_m_phone;
    T_M_PhoneCall t_m_phoneCall;

    LinearLayout layoutCall;
    LinearLayout layoutAdd;
    LinearLayout layoutDelete;
    LinearLayout layoutEdit;
    Unbinder unbinder;
    private List<BeanPhone> listData;
    private PhoneAdapter phoneAdapter;

    private int curCheck = 0;
    private boolean isVideo =false;

    @SuppressLint("ValidFragment")
    public DialogFragment_PhoneBill(boolean isVideo) {
        this.isVideo = isVideo;
    }

    @Override
    public int loadContentView() {
        return R.layout.fragment_phonebill;
    }

    @Override
    public boolean isTintList() {
        return false;
    }

    @Override
    public int loadContentBottomView() {
        return R.layout.layout_bottom_phonebill;

    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        layoutCall=findById(bottomView,R.id.layout_call);
        layoutAdd=findById(bottomView,R.id.layout_add);
        layoutDelete=findById(bottomView,R.id.layout_delete);
        layoutEdit=findById(bottomView,R.id.layout_edit);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_call:
                        if (isVideo){
                            layoutCall.setEnabled(false);
                            videoCall();
                        }else {
                            BeanPhone item = listData.get(curCheck);
                            String number= item.getIp();
                            if (onQuickCallListener!=null)
                                onQuickCallListener.onQuickCall(item.getName(),number);
                            dismiss();
                        }
                        break;
                    case R.id.layout_add:
                        addOrEdit(0);
                        break;
                    case R.id.layout_delete:
                        addOrEdit(2);
                        break;
                    case R.id.layout_edit:
                        addOrEdit(1);
                        break;
                }
            }
        };
        layoutCall.setOnClickListener(onClickListener);
        layoutAdd.setOnClickListener(onClickListener);
        layoutDelete.setOnClickListener(onClickListener);
        layoutEdit.setOnClickListener(onClickListener);
    }

    @Override
    public int loadTintImage() {
        return R.drawable.sk_djs1_03;
    }

    int last;
    @Override
    public void initView(View view) {
        super.initView(view);
        if (!isVideo){
            txtTitle.setText(getResources().getString(R.string.title_specially_call));
            t_m_phoneCall = new T_M_PhoneCall(mContext);
            listData = t_m_phoneCall.queryAll();
        }else {
            t_m_phone = new T_M_PhoneVideo(mContext);
            listData = t_m_phone.queryAll();
        }


        phoneAdapter = new PhoneAdapter();
        gridview.setAdapter(phoneAdapter);


        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            /**
             *监听着ListView的滑动状态改变。官方的有三种状态SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING、SCROLL_STATE_IDLE：
             * SCROLL_STATE_TOUCH_SCROLL：手指正拖着ListView滑动
             * SCROLL_STATE_FLING：ListView正自由滑动
             * SCROLL_STATE_IDLE：ListView滑动后静止
             * */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                LogCus.msg("gridview:onScrollStateChanged:"+scrollState);
                if (scrollState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    last=gridview.getScrollY();
                }else{
                    int offsetY = gridview.getScrollY()-last;
                    saikeScollBar.setOffsetY(offsetY);
                    last= gridview.getScrollY();
                }
            }

            /*滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
            firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
            visibleItemCount：当前能看见的列表项个数（小半个也算）
            totalItemCount：列表项共数

            判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastRow = true;
            }*/
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        saikeScollBar.setOnSeekBarChangeListener(new SaikeScollBar.OnSeekBarChangeListener() {
            @Override
            public void onSeekUp() {

            }

            @Override
            public void onSeekDown() {

            }

            @Override
            public void onSeekTop() {
                gridview.scrollTo(gridview.getScrollX(),0);
            }

            @Override
            public void onSeekBottom() {
//                AbsListView.LayoutParams lp = (AbsListView.LayoutParams) gridview.getLayoutParams();
//                LogCus.msg("AbsListView:"+);
//                gridview.scrollTo(gridview.getScrollX(),lp.width);
            }

            @Override
            public void onProgressChanged(int offsetY) {
                gridview.scrollTo(gridview.getScrollX(),gridview.getScrollY()+offsetY);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (t_m_phone!=null)
            t_m_phone.close();
        if (t_m_phoneCall!=null)
            t_m_phoneCall.close();
    }

    /*视频通话*/
    public void videoCall()
    {
        BeanPhone item = listData.get(curCheck);
        PeerModel peer = new PeerModel();
        peer.name = item.getName();
        peer.ipAddress = item.getIp();

        ChatFragment fragment = new ChatFragment(peer, false);
        fragment.setOnDialogFragmentListener(new OnDialogFragmentListener() {
            @Override
            public void onDialogFragmentDismissed() {
                layoutCall.setEnabled(true);
            }
        });
        fragment.show(this.getActivity().getSupportFragmentManager(), "ChatFragment");
    }



    private void addOrEdit(int type){
        BeanPhone beanPhone=null;
        if (listData.size()>curCheck)
            beanPhone=listData.get(curCheck);
        if (type!=0&&listData.size()<1)
            return;
        new DialogFragment_Phone_Edit(isVideo,type,curCheck,beanPhone)

                .setOnAddResultListener(new DialogFragment_Phone_Edit.OnAddResultListener() {
                    @Override
                    public void onAddResult(int type,int position,BeanPhone result) {
                        if (type==0){
                            listData.add(result);
                            phoneAdapter.refreshDatas();
                        }else if (type==1){
                            listData.get(position).setName(result.getName());
                            listData.get(position).setIp(result.getIp());
                            phoneAdapter.refreshDatas();
                        }else if (type==2){

                            phoneAdapter.refreshDatas();
                        }

                    }
                })
                .show(((BaseActivity)mContext).getSupportFragmentManager()
                        ,DialogFragment_Phone_Edit.class.getName());
    }

    private class PhoneAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public PhoneAdapter() {
            inflater = LayoutInflater.from(mContext);
        }

        private void refreshDatas() {
            if (isVideo)
                listData=t_m_phone.queryAll();
            else
                listData=t_m_phoneCall.queryAll();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public BeanPhone getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_phone, parent, false);
                viewHolder.layout = (TintLinearLayout) convertView.findViewById(R.id.layout);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.txtIp = (TextView) convertView.findViewById(R.id.txt_ip);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            BeanPhone item = getItem(position);
            viewHolder.txtName.setText(item.getName() + "");
            viewHolder.txtIp.setText(item.getIp() + "");
//            convertView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gold));
            int color=curCheck==position?R.color.theme_color_primary:R.color.white;
            viewHolder.layout.setSelected(curCheck==position);
            viewHolder.txtName.setTextColor(ContextCompat.getColor(mContext,color));
            viewHolder.txtIp.setTextColor(ContextCompat.getColor(mContext,color));

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curCheck = position;
                    notifyDataSetChanged();

                }
            });
            return convertView;
        }
    }

    private class ViewHolder {
        TintLinearLayout layout;
        TextView txtName, txtIp;
    }
    OnQuickCallListener onQuickCallListener;

    public void setOnQuickCallListener(OnQuickCallListener onQuickCallListener) {
        this.onQuickCallListener = onQuickCallListener;
    }

    public interface OnQuickCallListener{
        public void onQuickCall(String name,String number);
    }
}
