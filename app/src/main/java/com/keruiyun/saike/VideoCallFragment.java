package com.keruiyun.saike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class VideoCallFragment extends DialogFragment
{
	private VideoCallFragmentListener _listener;
	
	private Button _call01Button;
	private Button _call02Button;
	private Button _call03Button;
	private Button _call04Button;
	private Button _call05Button;
	private Button _call06Button;
	private Button _call07Button;
	private Button _call08Button;
	private Button _call09Button;
	private Button _call10Button;
	private Button _call11Button;
	private Button _call12Button;
	private Button _call13Button;
	private Button _call14Button;
	private Button _call15Button;
	private Button _call16Button;
	private Button _call17Button;
	private Button _call18Button;
	private Button _call19Button;
	private Button _call20Button;
	private Button _call21Button;
	private Button _call22Button;
	private Button _call23Button;
	private Button _call24Button;
	private Button _call25Button;
	private Button _call26Button;
	private Button _call27Button;
	private Button _call28Button;
	private Button _call29Button;
	private Button _call30Button;
	private Button _call31Button;
	private Button _call32Button;
	private Button _call33Button;
	private Button _call34Button;
	private Button _call35Button;
	private Button _call36Button;
	private Button _call37Button;
	private Button _call38Button;
	private Button _call39Button;
	private Button _call40Button;
	private Button _call41Button;
	private Button _call42Button;
	private Button _call43Button;
	private Button _call44Button;
	private Button _call45Button;
	private Button _call46Button;
	private Button _call47Button;
	private Button _call48Button;
	private Button _call49Button;
	private Button _call50Button;
	private Button _call51Button;
	private Button _call52Button;
	private Button _call53Button;
	private Button _call54Button;
	private Button _makeCallButton;
	private Button _editCallButton;
	
	public VideoCallFragment()
	{
	}
	
	public VideoCallFragment(VideoCallFragmentListener listener)
	{
		_listener = listener;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.getDialog().setTitle("视频通话");
		 
		View v = inflater.inflate(R.layout.fragment_videocall, null);

		_call01Button = (Button)v.findViewById(R.id.call01Button);
		_call01Button.setSelected(true);
		_call01Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(true);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call02Button = (Button)v.findViewById(R.id.call02Button);
		_call02Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(true);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call03Button = (Button)v.findViewById(R.id.call03Button);
		_call03Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(true);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call04Button = (Button)v.findViewById(R.id.call04Button);
		_call04Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(true);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call05Button = (Button)v.findViewById(R.id.call05Button);
		_call05Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(true);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call06Button = (Button)v.findViewById(R.id.call06Button);
		_call06Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(true);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call07Button = (Button)v.findViewById(R.id.call07Button);
		_call07Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(true);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call08Button = (Button)v.findViewById(R.id.call08Button);
		_call08Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(true);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call09Button = (Button)v.findViewById(R.id.call09Button);
		_call09Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(true);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call10Button = (Button)v.findViewById(R.id.call10Button);
		_call10Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(true);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call11Button = (Button)v.findViewById(R.id.call11Button);
		_call11Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(true);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call12Button = (Button)v.findViewById(R.id.call12Button);
		_call12Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(true);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call13Button = (Button)v.findViewById(R.id.call13Button);
		_call13Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(true);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call14Button = (Button)v.findViewById(R.id.call14Button);
		_call14Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(true);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call15Button = (Button)v.findViewById(R.id.call15Button);
		_call15Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(true);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call16Button = (Button)v.findViewById(R.id.call16Button);
		_call16Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(true);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call17Button = (Button)v.findViewById(R.id.call17Button);
		_call17Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(true);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call18Button = (Button)v.findViewById(R.id.call18Button);
		_call18Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(true);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call19Button = (Button)v.findViewById(R.id.call19Button);
		_call19Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(true);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call20Button = (Button)v.findViewById(R.id.call20Button);
		_call20Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(true);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call21Button = (Button)v.findViewById(R.id.call21Button);
		_call21Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(true);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call22Button = (Button)v.findViewById(R.id.call22Button);
		_call22Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(true);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call23Button = (Button)v.findViewById(R.id.call23Button);
		_call23Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(true);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call24Button = (Button)v.findViewById(R.id.call24Button);
		_call24Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(true);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call25Button = (Button)v.findViewById(R.id.call25Button);
		_call25Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(true);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call26Button = (Button)v.findViewById(R.id.call26Button);
		_call26Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(true);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call27Button = (Button)v.findViewById(R.id.call27Button);
		_call27Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(true);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call28Button = (Button)v.findViewById(R.id.call28Button);
		_call28Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(true);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call29Button = (Button)v.findViewById(R.id.call29Button);
		_call29Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(true);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call30Button = (Button)v.findViewById(R.id.call30Button);
		_call30Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(true);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call31Button = (Button)v.findViewById(R.id.call31Button);
		_call31Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(true);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call32Button = (Button)v.findViewById(R.id.call32Button);
		_call32Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(true);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call33Button = (Button)v.findViewById(R.id.call33Button);
		_call33Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(true);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call34Button = (Button)v.findViewById(R.id.call34Button);
		_call34Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(true);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call35Button = (Button)v.findViewById(R.id.call35Button);
		_call35Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(true);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call36Button = (Button)v.findViewById(R.id.call36Button);
		_call36Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(true);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call37Button = (Button)v.findViewById(R.id.call37Button);
		_call37Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(true);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call38Button = (Button)v.findViewById(R.id.call38Button);
		_call38Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(true);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call39Button = (Button)v.findViewById(R.id.call39Button);
		_call39Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(true);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call40Button = (Button)v.findViewById(R.id.call40Button);
		_call40Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(true);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call41Button = (Button)v.findViewById(R.id.call41Button);
		_call41Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(true);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call42Button = (Button)v.findViewById(R.id.call42Button);
		_call42Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(true);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call43Button = (Button)v.findViewById(R.id.call43Button);
		_call43Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(true);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call44Button = (Button)v.findViewById(R.id.call44Button);
		_call44Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(true);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call45Button = (Button)v.findViewById(R.id.call45Button);
		_call45Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(true);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call46Button = (Button)v.findViewById(R.id.call46Button);
		_call46Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(true);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call47Button = (Button)v.findViewById(R.id.call47Button);
		_call47Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(true);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call48Button = (Button)v.findViewById(R.id.call48Button);
		_call48Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(true);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});

		_call49Button = (Button)v.findViewById(R.id.call49Button);
		_call49Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(true);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call50Button = (Button)v.findViewById(R.id.call50Button);
		_call50Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(true);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call51Button = (Button)v.findViewById(R.id.call51Button);
		_call51Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(true);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call52Button = (Button)v.findViewById(R.id.call52Button);
		_call52Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(true);
				_call53Button.setSelected(false);
				_call54Button.setSelected(false);
			}
		});
		
		_call53Button = (Button)v.findViewById(R.id.call53Button);
		_call53Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(true);
				_call54Button.setSelected(false);
			}
		});
		
		_call54Button = (Button)v.findViewById(R.id.call54Button);
		_call54Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				_call01Button.setSelected(false);
				_call02Button.setSelected(false);
				_call03Button.setSelected(false);
				_call04Button.setSelected(false);
				_call05Button.setSelected(false);
				_call06Button.setSelected(false);
				_call07Button.setSelected(false);
				_call08Button.setSelected(false);
				_call09Button.setSelected(false);
				_call10Button.setSelected(false);
				_call11Button.setSelected(false);
				_call12Button.setSelected(false);
				_call13Button.setSelected(false);
				_call14Button.setSelected(false);
				_call15Button.setSelected(false);
				_call16Button.setSelected(false);
				_call17Button.setSelected(false);
				_call18Button.setSelected(false);
				_call19Button.setSelected(false);
				_call20Button.setSelected(false);
				_call21Button.setSelected(false);
				_call22Button.setSelected(false);
				_call23Button.setSelected(false);
				_call24Button.setSelected(false);
				_call25Button.setSelected(false);
				_call26Button.setSelected(false);
				_call27Button.setSelected(false);
				_call28Button.setSelected(false);
				_call29Button.setSelected(false);
				_call30Button.setSelected(false);
				_call31Button.setSelected(false);
				_call32Button.setSelected(false);
				_call33Button.setSelected(false);
				_call34Button.setSelected(false);
				_call35Button.setSelected(false);
				_call36Button.setSelected(false);
				_call37Button.setSelected(false);
				_call38Button.setSelected(false);
				_call39Button.setSelected(false);
				_call40Button.setSelected(false);
				_call41Button.setSelected(false);
				_call42Button.setSelected(false);
				_call43Button.setSelected(false);
				_call44Button.setSelected(false);
				_call45Button.setSelected(false);
				_call46Button.setSelected(false);
				_call47Button.setSelected(false);
				_call48Button.setSelected(false);
				_call49Button.setSelected(false);
				_call50Button.setSelected(false);
				_call51Button.setSelected(false);
				_call52Button.setSelected(false);
				_call53Button.setSelected(false);
				_call54Button.setSelected(true);
			}
		});
		
		_makeCallButton = (Button)v.findViewById(R.id.makeCallButton);
		_makeCallButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				if (null != _listener)
				{
					SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
					String strNumber = settings.getString("ip"+getSelectedButtonIndex(), "");
					strNumber = strNumber.trim();
					
					if (strNumber.length() > 0)
					{
					    _listener.onVideoCallSelect(getSelectedButtonName(), strNumber);
					
					    VideoCallFragment.this.dismiss();
					}
				}
			}
		});
		
		_editCallButton = (Button)v.findViewById(R.id.editCallButton);
		_editCallButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				showEditQuickCallDialog();
			}
		});
		
		return v;
	}
	
	private void showEditQuickCallDialog() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(
				this.getActivity());
		View contentView = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.dialog_edit_videocall, null);
		final EditText numberTextView = (EditText)contentView.findViewById(R.id.numberTextView);
		
		SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
		String strNumber = settings.getString("ip"+getSelectedButtonIndex(), "");
		numberTextView.setText(strNumber);
		
		dialog.setTitle("设置IP地址");
		dialog.setView(contentView);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1) 
			{
				SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("ip"+getSelectedButtonIndex(), numberTextView.getText().toString());
				editor.commit();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
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
	
	private int getSelectedButtonIndex()
	{
		int index = 0;
		
		if (_call01Button.isSelected())
		{
			index = 1;
		}
		else if (_call02Button.isSelected())
		{
			index = 2;
		}
		else if (_call03Button.isSelected())
		{
			index = 3;
		}
		else if (_call04Button.isSelected())
		{
			index = 4;
		}
		else if (_call05Button.isSelected())
		{
			index = 5;
		}
		else if (_call06Button.isSelected())
		{
			index = 6;
		}
		else if (_call07Button.isSelected())
		{
			index = 7;
		}
		else if (_call08Button.isSelected())
		{
			index = 8;
		}
		else if (_call09Button.isSelected())
		{
			index = 9;
		}
		else if (_call10Button.isSelected())
		{
			index = 10;
		}
		else if (_call11Button.isSelected())
		{
			index = 11;
		}
		else if (_call12Button.isSelected())
		{
			index = 12;
		}
		else if (_call13Button.isSelected())
		{
			index = 13;
		}
		else if (_call14Button.isSelected())
		{
			index = 14;
		}
		else if (_call15Button.isSelected())
		{
			index = 15;
		}
		else if (_call16Button.isSelected())
		{
			index = 16;
		}
		else if (_call17Button.isSelected())
		{
			index = 17;
		}
		else if (_call18Button.isSelected())
		{
			index = 18;
		}
		else if (_call19Button.isSelected())
		{
			index = 19;
		}
		else if (_call20Button.isSelected())
		{
			index = 20;
		}
		else if (_call21Button.isSelected())
		{
			index = 21;
		}
		else if (_call22Button.isSelected())
		{
			index = 22;
		}
		else if (_call23Button.isSelected())
		{
			index = 23;
		}
		else if (_call24Button.isSelected())
		{
			index = 24;
		}
		else if (_call25Button.isSelected())
		{
			index = 25;
		}
		else if (_call26Button.isSelected())
		{
			index = 26;
		}
		else if (_call27Button.isSelected())
		{
			index = 27;
		}
		else if (_call28Button.isSelected())
		{
			index = 28;
		}
		else if (_call29Button.isSelected())
		{
			index = 29;
		}
		else if (_call30Button.isSelected())
		{
			index = 30;
		}
		else if (_call31Button.isSelected())
		{
			index = 31;
		}
		else if (_call32Button.isSelected())
		{
			index = 32;
		}
		else if (_call33Button.isSelected())
		{
			index = 33;
		}
		else if (_call34Button.isSelected())
		{
			index = 34;
		}
		else if (_call35Button.isSelected())
		{
			index = 35;
		}
		else if (_call36Button.isSelected())
		{
			index = 36;
		}
		else if (_call37Button.isSelected())
		{
			index = 37;
		}
		else if (_call38Button.isSelected())
		{
			index = 38;
		}
		else if (_call39Button.isSelected())
		{
			index = 39;
		}
		else if (_call40Button.isSelected())
		{
			index = 40;
		}
		else if (_call41Button.isSelected())
		{
			index = 41;
		}
		else if (_call42Button.isSelected())
		{
			index = 42;
		}
		else if (_call43Button.isSelected())
		{
			index = 43;
		}
		else if (_call44Button.isSelected())
		{
			index = 44;
		}
		else if (_call45Button.isSelected())
		{
			index = 45;
		}
		else if (_call46Button.isSelected())
		{
			index = 46;
		}
		else if (_call47Button.isSelected())
		{
			index = 47;
		}
		else if (_call48Button.isSelected())
		{
			index = 48;
		}
		else if (_call49Button.isSelected())
		{
			index = 49;
		}
		else if (_call50Button.isSelected())
		{
			index = 50;
		}
		else if (_call51Button.isSelected())
		{
			index = 51;
		}
		else if (_call52Button.isSelected())
		{
			index = 52;
		}
		
		return index;
	}
	
	private String getSelectedButtonName()
	{
		String name = "";
		
		if (_call01Button.isSelected())
		{
			name = _call01Button.getText().toString();
		}
		else if (_call02Button.isSelected())
		{
			name = _call02Button.getText().toString();
		}
		else if (_call03Button.isSelected())
		{
			name = _call03Button.getText().toString();
		}
		else if (_call04Button.isSelected())
		{
			name = _call04Button.getText().toString();
		}
		else if (_call05Button.isSelected())
		{
			name = _call05Button.getText().toString();
		}
		else if (_call06Button.isSelected())
		{
			name = _call06Button.getText().toString();
		}
		else if (_call07Button.isSelected())
		{
			name = _call07Button.getText().toString();
		}
		else if (_call08Button.isSelected())
		{
			name = _call08Button.getText().toString();
		}
		else if (_call09Button.isSelected())
		{
			name = _call09Button.getText().toString();
		}
		else if (_call10Button.isSelected())
		{
			name = _call10Button.getText().toString();
		}
		else if (_call11Button.isSelected())
		{
			name = _call11Button.getText().toString();
		}
		else if (_call12Button.isSelected())
		{
			name = _call12Button.getText().toString();
		}
		else if (_call13Button.isSelected())
		{
			name = _call13Button.getText().toString();
		}
		else if (_call14Button.isSelected())
		{
			name = _call14Button.getText().toString();
		}
		else if (_call15Button.isSelected())
		{
			name = _call15Button.getText().toString();
		}
		else if (_call16Button.isSelected())
		{
			name = _call16Button.getText().toString();
		}
		else if (_call17Button.isSelected())
		{
			name = _call17Button.getText().toString();
		}
		else if (_call18Button.isSelected())
		{
			name = _call18Button.getText().toString();
		}
		else if (_call19Button.isSelected())
		{
			name = _call19Button.getText().toString();
		}
		else if (_call20Button.isSelected())
		{
			name = _call20Button.getText().toString();
		}
		else if (_call21Button.isSelected())
		{
			name = _call21Button.getText().toString();
		}
		else if (_call22Button.isSelected())
		{
			name = _call22Button.getText().toString();
		}
		else if (_call23Button.isSelected())
		{
			name = _call23Button.getText().toString();
		}
		else if (_call24Button.isSelected())
		{
			name = _call24Button.getText().toString();
		}
		else if (_call25Button.isSelected())
		{
			name = _call25Button.getText().toString();
		}
		else if (_call26Button.isSelected())
		{
			name = _call26Button.getText().toString();
		}
		else if (_call27Button.isSelected())
		{
			name = _call27Button.getText().toString();
		}
		else if (_call28Button.isSelected())
		{
			name = _call28Button.getText().toString();
		}
		else if (_call29Button.isSelected())
		{
			name = _call29Button.getText().toString();
		}
		else if (_call30Button.isSelected())
		{
			name = _call30Button.getText().toString();
		}
		else if (_call31Button.isSelected())
		{
			name = _call31Button.getText().toString();
		}
		else if (_call32Button.isSelected())
		{
			name = _call32Button.getText().toString();
		}
		else if (_call33Button.isSelected())
		{
			name = _call33Button.getText().toString();
		}
		else if (_call34Button.isSelected())
		{
			name = _call34Button.getText().toString();
		}
		else if (_call35Button.isSelected())
		{
			name = _call35Button.getText().toString();
		}
		else if (_call36Button.isSelected())
		{
			name = _call36Button.getText().toString();
		}
		else if (_call37Button.isSelected())
		{
			name = _call37Button.getText().toString();
		}
		else if (_call38Button.isSelected())
		{
			name = _call38Button.getText().toString();
		}
		else if (_call39Button.isSelected())
		{
			name = _call39Button.getText().toString();
		}
		else if (_call40Button.isSelected())
		{
			name = _call40Button.getText().toString();
		}
		else if (_call41Button.isSelected())
		{
			name = _call41Button.getText().toString();
		}
		else if (_call42Button.isSelected())
		{
			name = _call42Button.getText().toString();
		}
		else if (_call43Button.isSelected())
		{
			name = _call43Button.getText().toString();
		}
		else if (_call44Button.isSelected())
		{
			name = _call44Button.getText().toString();
		}
		else if (_call45Button.isSelected())
		{
			name = _call45Button.getText().toString();
		}
		else if (_call46Button.isSelected())
		{
			name = _call46Button.getText().toString();
		}
		else if (_call47Button.isSelected())
		{
			name = _call47Button.getText().toString();
		}
		else if (_call48Button.isSelected())
		{
			name = _call48Button.getText().toString();
		}
		else if (_call49Button.isSelected())
		{
			name = _call49Button.getText().toString();
		}
		else if (_call50Button.isSelected())
		{
			name = _call50Button.getText().toString();
		}
		else if (_call51Button.isSelected())
		{
			name = _call51Button.getText().toString();
		}
		else if (_call52Button.isSelected())
		{
			name = _call52Button.getText().toString();
		}
		
		return name;
	}
}
