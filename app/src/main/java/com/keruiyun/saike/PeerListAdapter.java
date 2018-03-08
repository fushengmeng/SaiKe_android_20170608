package com.keruiyun.saike;

import com.keruiyun.saike.model.PeerModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PeerListAdapter extends BaseAdapter 
{
	private Context mcontext;
	
	public PeerListAdapter(Context context) 
	{
		mcontext = context;
	}

	public int getCount() 
	{
		return AppData.getPeerArray().size();
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
				R.layout.peerlist_item, null);

        PeerModel peer = AppData.getPeerArray().get(position);

		TextView music_title = (TextView) convertView
				.findViewById(R.id.peerTextView);
		music_title.setText(peer.name);
		
		return convertView;
	}

}
