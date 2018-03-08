package com.keruiyun.saike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MenuFragment extends DialogFragment
{
	private int[] _ids;
	private String[] _artists;
	private String[] _titles;
	private int _count = 0;
	private ListView _listView;
	private MenuFragmentListener _listener;
	private Handler handler = new Handler();
	private BroadcastReceiver _receiver;
	private MusicListAdapter _adapter;
	private Button _playButton;
	private Button _stopButton;
	private Button _previousButton;
	private Button _nextButton;
	private RadioGroup _playOrderRadioGroup;
	private RadioButton _radioButton0;
	private RadioButton _radioButton1;
	private RadioButton _radioButton2;
	
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

    public MenuFragment()
	{
	}
    
	@SuppressLint("ValidFragment")
	public MenuFragment(MenuFragmentListener listener)
	{
		_listener = listener;
	}
	
	private String[] media_info = new String[] { MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID };
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.getDialog().setTitle("请选择背景音乐");
		 
		View v = inflater.inflate(R.layout.fragment_menu, null);

        _listView = (ListView)v.findViewById(R.id.music_listview);
        _listView.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				_adapter.setSelectItem(arg2);
				_adapter.notifyDataSetInvalidated(); 
				
				if (null != _listener)
				{
					_listener.onMusicSelect(_ids, _titles, _artists, arg2);
				}
				
				//MenuFragment.this.dismiss();
			}
		});
        
        _playButton = (Button)v.findViewById(R.id.playButton);
        _playButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				if (null != _listener)
				{
					_listener.onMusicPlay();
				}
			}
		});
        
        _stopButton = (Button)v.findViewById(R.id.stopButton);
        _stopButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				if (null != _listener)
				{
					_listener.onMusicStop();
				}
			}
		});
        
        _previousButton = (Button)v.findViewById(R.id.previousButton);
        _previousButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				int index = _adapter.getSelectItem() - 1;
				if (index >= 0)
				{
					_adapter.setSelectItem(index);
					_adapter.notifyDataSetInvalidated();
					
					ensureVisible(_listView, index);
					
					if (null != _listener)
					{
						_listener.onMusicSelect(_ids, _titles, _artists, index);
					}
				}
			}
		});
        
        _nextButton = (Button)v.findViewById(R.id.nextButton);
        _nextButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				int index = _adapter.getSelectItem() + 1;
				if (index < _count)
				{
					_adapter.setSelectItem(index);
					_adapter.notifyDataSetInvalidated();
					
					ensureVisible(_listView, index);
					
					if (null != _listener)
					{
						_listener.onMusicSelect(_ids, _titles, _artists, index);
					}
				}
			}
		});

        _radioButton0 = (RadioButton)v.findViewById(R.id.radio0);
        _radioButton1 = (RadioButton)v.findViewById(R.id.radio1);
        _radioButton2 = (RadioButton)v.findViewById(R.id.radio2);
        
        SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
		int playOrder = settings.getInt("PlayOrder", 0);
		if (1 == playOrder)
		{
			_radioButton1.setChecked(true);
		}
		else if (2 == playOrder)
		{
			_radioButton2.setChecked(true);
		}
		else
		{
			_radioButton0.setChecked(true);
		}
		
        _playOrderRadioGroup = (RadioGroup)v.findViewById(R.id.playOrderRadioGroup);
        _playOrderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        { 
            @Override 
            public void onCheckedChanged(RadioGroup group, int checkedId)
            { 
            	if (checkedId == _radioButton1.getId())
            	{ 
            		checkedId = 1;
                }
            	else if (checkedId== _radioButton2.getId())
            	{ 
            		checkedId = 2; 
                } 
            	else
            	{
            		checkedId = 0;
            	}
            	
            	if (null != _listener)
				{
					_listener.onMusicPlayOrder(checkedId);
				}
            } 
        }); 
        
		loadMusicData();
		
		registerBroadcast();
		//handler.postDelayed(runnable, 2000);
		
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() 
	{
		super.onStart();
		getDialog().setCanceledOnTouchOutside(true);
	}

	public void onDestroy()
	{
		super.onDestroy();
		
		removeBroadcast();
		//handler.removeCallbacks(runnable);
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
			
			_adapter = new MusicListAdapter(this.getActivity(), cursor);
			_listView.setAdapter(_adapter);
		}
	}
	
	private void registerBroadcast() 
	{
		_receiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String action = intent.getAction();
				if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action))
				{
				}
				else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action))
				{
					loadMusicData();
				}
			}
		};
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter.addDataScheme("file");
		getActivity().registerReceiver(_receiver, filter);
	}
	
	private void removeBroadcast() 
	{
		getActivity().unregisterReceiver(_receiver);
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
	
}
