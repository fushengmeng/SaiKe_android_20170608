package com.keruiyun.saike;

import com.keruiyun.saike.R.color;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicListAdapter extends BaseAdapter 
{
	private Context mcontext;
	private Cursor mcursor;

	private int _selectItem = -1;
	
	public void setSelectItem(int selectItem) 
	{  
		_selectItem = selectItem;  
    }  
	
	public int getSelectItem() 
	{  
		return _selectItem;
    }  
	
	public MusicListAdapter(Context context, Cursor cursor) 
	{
		mcontext = context;
		mcursor = cursor;
	}

	public int getCount() 
	{
		return mcursor.getCount();
	}

	
	public Object getItem(int position)
	{
		return position;
	}
	
	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = LayoutInflater.from(mcontext).inflate(
				R.layout.musiclist_item, null);
		mcursor.moveToPosition(position);

		TextView music_title = (TextView) convertView
				.findViewById(R.id.musicname);
		music_title.setText(mcursor.getString(0));
		TextView music_singer = (TextView) convertView
				.findViewById(R.id.singer);
		music_singer.setText(mcursor.getString(2));
		TextView music_time = (TextView) convertView.findViewById(R.id.time);
		music_time.setText(toTime(mcursor.getInt(1)));
		
		if (position == _selectItem) 
		{
			convertView.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		else
		{
			convertView.setBackgroundColor(Color.parseColor("#000000"));
		}
		
		return convertView;
	}

	public String toTime(int time) 
	{
		time /= 1000;
		int minute = time / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}
}
