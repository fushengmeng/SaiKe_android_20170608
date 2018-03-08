package com.keruiyun.saike.fragment;

import org.webrtc.MediaStream;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;

import com.keruiyun.saike.MenuFragmentListener;
import com.keruiyun.saike.R;
import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.model.PeerModel;
import com.util.CameraCheck;
import com.util.ToastUtil;


import android.annotation.SuppressLint;

import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class ChatFragment extends BaseDialogFragment
{
	private int[] _ids;
	private String[] _artists;
	private String[] _titles;
	private int _count = 0;
	private MenuFragmentListener _listener;
	private Handler handler = new Handler();
	private ImageView _playButton;
	private Button _endButton;
	private Button _switchCameraButton;
	boolean _isIncoming = false;
	
	private static final int LOCAL_X_CONNECTING = 0;
    private static final int LOCAL_Y_CONNECTING = 0;
    private static final int LOCAL_WIDTH_CONNECTING = 100;
    private static final int LOCAL_HEIGHT_CONNECTING = 100;
    private static final int LOCAL_X_CONNECTED = 80;
    private static final int LOCAL_Y_CONNECTED = 0;
    private static final int LOCAL_WIDTH_CONNECTED = 20;
    private static final int LOCAL_HEIGHT_CONNECTED = 20;
    private static final int REMOTE_X = 0;
    private static final int REMOTE_Y = 0;
    private static final int REMOTE_WIDTH = 100;
    private static final int REMOTE_HEIGHT = 100;
    private VideoRendererGui.ScalingType scalingType = VideoRendererGui.ScalingType.SCALE_ASPECT_FILL;
    private GLSurfaceView vsv;
    private VideoRenderer.Callbacks localRender;
    private VideoRenderer.Callbacks remoteRender;
    
    public PeerModel _peer;
    
    BroadcastReceiver _broadcastReceiver;

	
	Runnable runnable = new Runnable() 
	{  
        @Override  
        public void run() 
        {   
            try 
            {  
            	loadMusicData();
            	
                handler.postDelayed(this, 2000);  
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
            }  
        }  
    };  
    
    public ChatFragment()
	{
	}
    
	@SuppressLint("ValidFragment")
	public ChatFragment(PeerModel peer, boolean isIncoming)
	{
		_peer = peer;
		_isIncoming = isIncoming;
		
		MainApplication.getInstance()._chatFragment = this;

	}
	
	private String[] media_info = new String[] { MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID };
	@Override
	public int loadContentView() {
		return R.layout.fragment_chat;
	}

	@Override
	public void initView(View view) {
		super.initView(view);

		this.setCancelable(false);
		this.getDialog().setCanceledOnTouchOutside(false);
		_playButton =findById(R.id.closeVideoButton);

		_playButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (_playButton.isSelected())
				{
					if (CameraCheck.hasCamera()){
						MainApplication.getInstance()._webRTCClient.startLocalVideo();
						_playButton.setSelected(false);
						_playButton.setImageResource(R.drawable.sk_video);
//				    _playButton.setText("关闭本地视频");
					}else {
						ToastUtil.showToast(mContext.getResources().getString(R.string.err_hascamera));
					}

				}
				else
				{
					MainApplication.getInstance()._webRTCClient.closeLocalVideo();
					_playButton.setSelected(true);
					_playButton.setImageResource(R.drawable.sk_videoon);
//				    _playButton.setText("打开本地视频");
				}
			}
		});

		_endButton = findById(R.id.endButton);
		_endButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				MainApplication.getInstance()._webRTCClient.endCall();
				MainApplication.getInstance().sendEndCallPacket();

				ChatFragment.this.dismiss();
			}
		});

		_switchCameraButton = findById(R.id.switchCameraButton);
		_switchCameraButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				MainApplication.getInstance()._webRTCClient.switchCamera();
			}
		});

		vsv = findById(R.id.glview_call);
		vsv.setPreserveEGLContextOnPause(true);
		vsv.setKeepScreenOn(true);
		VideoRendererGui.setView(vsv, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});

		// local and remote render
		remoteRender = VideoRendererGui.create(
				REMOTE_X, REMOTE_Y,
				REMOTE_WIDTH, REMOTE_HEIGHT, scalingType, false);
		localRender = VideoRendererGui.create(
				LOCAL_X_CONNECTING, LOCAL_Y_CONNECTING,
				LOCAL_WIDTH_CONNECTING, LOCAL_HEIGHT_CONNECTING, scalingType, true);

		if (!_isIncoming)
		{
			try
			{

				if (CameraCheck.hasCamera()) {
					MainApplication.getInstance()._webRTCClient.endCall();
					MainApplication.getInstance()._webRTCClient.makeCall(_peer.ipAddress);
				}
			}
			catch (Exception ex)
			{
			}
		}

	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context arg0, Intent arg1)
			{
                MainApplication.getInstance()._webRTCClient.endCall();
				
				ChatFragment.this.dismiss();
			}
		};
		
		IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.closechat");
		this.getActivity().registerReceiver(_broadcastReceiver, intentFilter);
	}

	@Override
	public void onStart() 
	{
		super.onStart();
		if(!CameraCheck.hasCamera()){
			ToastUtil.showToast(mContext.getResources().getString(R.string.err_hascamera));
			mContext.sendBroadcast(new Intent("com.keruiyun.saike.closechat"));
		}
	}

	public void onDestroy()
	{
		super.onDestroy();

		MainApplication.getInstance()._chatFragment = null;
		
		if (null != _broadcastReceiver)
		{
			try 
			{
				this.getActivity().unregisterReceiver(_broadcastReceiver);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) 
		{
		    case Activity.RESULT_OK:
			    break;
		    case Activity.RESULT_CANCELED:
			    getActivity().finish();
			    break;
		    default:
			    break;
		}
	}
	
	private void loadMusicData()
	{
		Cursor cursor = this.getActivity().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, media_info, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
		if (cursor.getCount() != _count)
		{
			_count = cursor.getCount();
			
			cursor.moveToFirst();
			
			_ids = new int[cursor.getCount()];
			_artists = new String[cursor.getCount()];
			_titles = new String[cursor.getCount()];
			
			for (int i = 0; i < cursor.getCount(); i++) 
			{
				_ids[i] = cursor.getInt(3);
				_titles[i] = cursor.getString(0);
				_artists[i] = cursor.getString(2);
				
				cursor.moveToNext();
			}
		}
	}
	
	public static void ensureVisible(ListView listView, int pos)
	{
	    if (listView == null)
	    {
	        return;
	    }

	    if(pos < 0 || pos >= listView.getCount())
	    {
	        return;
	    }

	    int first = listView.getFirstVisiblePosition();
	    int last = listView.getLastVisiblePosition();

	    if (pos < first)
	    {
	        listView.smoothScrollToPosition(pos);
	        return;
	    }

	    if (pos >= last)
	    {
	        listView.smoothScrollToPosition(1 + pos - (last - first));
	        return;
	    }
	}

	public void onLocalStream(MediaStream localStream) {
        localStream.videoTracks.get(0).addRenderer(new VideoRenderer(localRender));
        VideoRendererGui.update(localRender,
        		LOCAL_X_CONNECTED, LOCAL_Y_CONNECTED,
                LOCAL_WIDTH_CONNECTED, LOCAL_HEIGHT_CONNECTED,
                scalingType);
    }

	public void onAddRemoteStream(MediaStream remoteStream, String arg1)
	{
        remoteStream.videoTracks.get(0).addRenderer(new VideoRenderer(remoteRender));
        VideoRendererGui.update(remoteRender,
                REMOTE_X, REMOTE_Y,
                REMOTE_WIDTH, REMOTE_HEIGHT, scalingType);
        
        AudioManager audioManager = (AudioManager)ChatFragment.this.getActivity().getSystemService(Context.AUDIO_SERVICE);
		audioManager.setSpeakerphoneOn(true);
    }

    public void onRemoveRemoteStream(int endPoint) {
        VideoRendererGui.update(localRender,
                LOCAL_X_CONNECTING, LOCAL_Y_CONNECTING,
                LOCAL_WIDTH_CONNECTING, LOCAL_HEIGHT_CONNECTING,
                scalingType);
        
        AudioManager audioManager = (AudioManager)ChatFragment.this.getActivity().getSystemService(Context.AUDIO_SERVICE);
		audioManager.setSpeakerphoneOn(false);
    }
	
}
