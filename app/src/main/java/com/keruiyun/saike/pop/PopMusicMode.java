package com.keruiyun.saike.pop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.music.musicplayer.service.MusicService;
import com.keruiyun.saike.R;
import com.music.musicplayer.utility.Constants;
import com.music.musicplayer.utility.SpTools;
import com.util.ToastUtil;


/**
 * Created by Administrator on 2017/12/27.
 */

public class PopMusicMode extends BasePopupWindow {


    private LinearLayout layout;

    private View layoutLoop;
    private View layoutShuffle;
    private View layoutOne;

    private ImageView icon;

    Resources res;

    public PopMusicMode(Context context,ImageView icon) {
        super(context);
        this.icon=icon;
    }



    @Override
    public int loadContentView() {
        return R.layout.layout_music_mode;
    }

    @Override
    public void initView() {
        super.initView();
        layout=findById(R.id.layout);
        layoutLoop=findById(R.id.layout_music_mode_loop);
        layoutShuffle=findById(R.id.layout_music_mode_shuffle);
        layoutOne=findById(R.id.layout_music_mode_one);
        res = getContext().getResources();
        layoutLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicService.playMode=2;
                SpTools.setInt(getContext(), Constants.KEY_player_mode,2);
                icon.setImageResource(R.drawable.sk_leku_55_1);
                ToastUtil.showToast(res.getString(R.string.loop_play));
                dismiss();
            }
        });

        layoutShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicService.playMode=0;
                SpTools.setInt(getContext(), Constants.KEY_player_mode,0);
                icon.setImageResource(R.drawable.sk_leku_36_1);
                ToastUtil.showToast(res.getString(R.string.random_play));
                dismiss();
            }
        });

        layoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicService.playMode=1;
                SpTools.setInt(getContext(), Constants.KEY_player_mode,1);
                icon.setImageResource(R.drawable.sk_leku_38_1);
                ToastUtil.showToast(res.getString(R.string.play_one));
                dismiss();
            }
        });


    }

    public void setColorBg(String color){
        layout.setBackgroundColor(Color.parseColor(color));
    }


}
