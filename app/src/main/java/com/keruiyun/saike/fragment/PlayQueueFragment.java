package com.keruiyun.saike.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.keruiyun.saike.R;
import com.keruiyun.saike.uiview.ListScrollView;
import com.keruiyun.saike.uiview.ListViewBar;
import com.keruiyun.saike.uiview.SaikeScollBar;
import com.keruiyun.saike.util.LogCus;
import com.music.musicplayer.bean.Mp3Info;
import com.music.musicplayer.service.MusicService;
import com.music.musicplayer.utility.Constants;
import com.music.musicplayer.utility.MediaScanner;
import com.music.musicplayer.utility.MediaUtil;
import com.music.musicplayer.utility.SpTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wm on 2016/2/4.
 */
public class PlayQueueFragment extends BaseDialogFragment {

    @BindView(R.id.listscrollview)
    ListScrollView listScrollView;
    @BindView(R.id.scollbar)
    SaikeScollBar saikeScollBar;
    @BindView(R.id.play_list)
    ListViewBar listView;  //弹出的activity列表

    private PlaylistAdapter adapter;
    private List<Mp3Info> playlist = new ArrayList<>();
    private TextView clearAll;
    private TextView playlistNumber, addToPlaylist;

    private int mCurrentPosition = 0;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
        mCurrentPosition = SpTools.getInt(mContext, "music_current_position", 0);
    }


    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
//        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
//        getDialog().getWindow().setLayout(400,600);

        getDialog().setCanceledOnTouchOutside(true);

    }


    @Override
    public int loadContentView() {
        return R.layout.fragment_queue;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.x = 280;
        params.y = 240;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);
        // 一定要设置Background，如果不设置，window属性设置无效
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view.setBackgroundColor(Color.parseColor("#272829"));

        //布局
        playlistNumber = (TextView) view.findViewById(R.id.play_list_number);
        addToPlaylist = (TextView) view.findViewById(R.id.playlist_addto);
        clearAll = (TextView) view.findViewById(R.id.playlist_clear_all);

        addToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Constants.ACTION_REFRESH_LIST);
                mContext.sendBroadcast(intent);

            }
        });


        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Constants.ACTION_CLEAR);
                mContext.sendBroadcast(intent);
                playlist.clear();
                adapter.notifyDataSetChanged();

            }
        });


        playlist = MediaUtil.getMp3Infos(mContext);
        adapter = new PlaylistAdapter();
        listView.setOnGridViewBarListener(saikeScollBar,listScrollView);
        listScrollView.setOnScrolledY(listView);
        listView.setAdapter(adapter);
    }

    public void refrshList(){
        playlist.clear();
        adapter.notifyDataSetChanged();
        playlist = MediaUtil.getMp3Infos(mContext);
        mCurrentPosition = SpTools.getInt(mContext, "music_current_position", 0);
        adapter.notifyDataSetChanged();
    }

    public void refreshPosition(int position){
        this.mCurrentPosition=position;
        adapter.notifyDataSetChanged();
    }

    class PlaylistAdapter extends BaseAdapter {
        LayoutInflater inflater;
        public PlaylistAdapter() {
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return playlist == null ? 0 : playlist.size();
        }

        @Override
        public Mp3Info getItem(int position) {
            return playlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView = inflater.inflate(R.layout.fragment_playqueue_item, parent, false);
                viewHolder=new ViewHolder(convertView,position);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.refresh(getItem(position),position);
            return convertView;
        }



        class ViewHolder {
            @BindView(R.id.play_state)
            TintImageView playState;
            @BindView(R.id.play_list_musicname)
            TextView playListMusicname;
            @BindView(R.id.play_list_artist)
            TextView playListArtist;
            int position;

            ViewHolder(View view,int positionItem) {
                ButterKnife.bind(this, view);
                this.position=positionItem;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == -1) {
                            return;
                        }
                        mCurrentPosition=position;
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        intent.setAction(Constants.ACTION_LIST_ITEM);
                        mContext.sendBroadcast(intent);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            void refresh(Mp3Info musicInfo,int position){
                this.position=position;
                playListMusicname.setText((position+1) + " " + musicInfo.getTitle());
//                playListArtist.setText("-" + musicInfo.getArtist());
                //判断该条目音乐是否在播放
                if (mCurrentPosition == position) {
                    playState.setVisibility(View.VISIBLE);
                    playState.setImageResource(R.drawable.song_play_icon);
                    playState.setImageTintList(R.color.theme_color_primary);

                } else {
                    playState.setVisibility(View.GONE);
                }
            }
        }
    }


}
