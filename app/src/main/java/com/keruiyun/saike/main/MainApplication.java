package com.keruiyun.saike.main;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.webrtc.MediaStream;
import org.webrtc.VideoRendererGui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeHelper;
import com.bilibili.magicasakura.utils.ThemeUtils;

import com.keruiyun.saike.AppData;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.fragment.ChatFragment;
import com.keruiyun.saike.R;
import com.keruiyun.saike.model.PeerModel;
import com.keruiyun.saike.serialservice.SerialSaikeListener;
import com.keruiyun.saike.serialservice.SerialSaunaService;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.util.Consts;
import com.keruiyun.saike.util.LogCus;
import com.music.soundpool.SoundPlayer;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
import com.util.ToastUtil;

import fr.pchab.webrtcclient.PeerConnectionParameters;
import fr.pchab.webrtcclient.WebRtcClient;
import fr.pchab.webrtcclient.WebRtcClient.WebRtcClientListener;

public class MainApplication extends Application implements Runnable, WebRtcClientListener ,ThemeUtils.switchColor{
	private static MainApplication mInstance = null;
	public static Context context;
	private Thread _thread;
	private DatagramSocket _socket;
	private InetAddress _group;
	private Thread _recieveThread;
	private DatagramSocket _recieveSocket;
	public WebRtcClient _webRTCClient;
	public ChatFragment _chatFragment;
	public String _peerIP;
	public BaseActivity _activity;
	public short enabled, year, month, day, hour, minute, second;


	
	Handler myHandler = new Handler(){
        public void handleMessage(Message msg){
        	chatHandleMessage(msg);
			super.handleMessage(msg);
        }
   };

	private void chatHandleMessage(Message msg){
		switch (msg.what)
		{
			case 1:
			{
				if (null != _chatFragment)
				{
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								if (null != _peerIP && _peerIP.length() > 0)
								{
									if (null == _socket)
									{
										_socket = new DatagramSocket();
										_socket.setReuseAddress(true);
									}

									if (null == _group)
									{
										_group = getBroadcastAddress(MainApplication.this);
									}

									WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
									WifiInfo wifiInfo = wifi.getConnectionInfo();
									int ipAddress = wifiInfo.getIpAddress();
									String strIP = (ipAddress & 0xFF ) + "." +
											((ipAddress >> 8 ) & 0xFF) + "." +
											((ipAddress >> 16 ) & 0xFF) + "." +
											( ipAddress >> 24 & 0xFF);

									String html = "saike_busy|" + strIP + "|" + strIP;

									byte[] data = html.getBytes();
									DatagramPacket packet = new DatagramPacket(data,data.length, _group, 8008);
									_socket.send(packet);

									Log.i("发送广播", html);
								}
							}
							catch (Exception ex)
							{
								ex.printStackTrace();
							}
						}
					}).start();
				}
				else
				{
					if (null != _peerIP && _peerIP.length() > 0)
					{
						PeerModel peer = new PeerModel();
						peer.name = _peerIP;
						peer.ipAddress = _peerIP;

						ChatFragment fragment = new ChatFragment(peer, true);
						fragment.show(_activity.getSupportFragmentManager(), "ChatFragment");
					}

                		/*
	                    ArrayList<PeerModel> peerArray = AppData.getPeerArray();
	                	for (PeerModel peer : peerArray)
	                  	{
	                  		if (0 == peer.ipAddress.compareToIgnoreCase(_peerIP))
	                  		{
	                  	        ChatFragment fragment = new ChatFragment(peer, true);
	              		        fragment.show(_activity.getFragmentManager(), "ChatFragment");

	              		        break;
	                  		}
	                  	}
	                  	*/
				}

				break;
			}
			case 2:
				Toast.makeText(MainApplication.this, "对方忙线中",
						Toast.LENGTH_SHORT).show();
				break;
		}
	}

	private void initSerialSaunaService(){
		SerialSaunaService sss = new SerialSaunaService();
		sss.start();
		sss.setListener(new SerialSaikeListener() {

			@Override
			public void onDataRecieved(int addr, short data) {
				// TODO Auto-generated method stub
				sendBroadcast(new Intent("com.keruiyun.saike.order").putExtra(
						"addr", addr).putExtra("data", data));
			}

			@Override
			public void onGPSDateRecieved(short enabled, short year, short month, short day, short hour, short minute,
										  short second)
			{
				MainApplication.this.enabled = enabled;
				MainApplication.this.year = year;
				MainApplication.this.month = month;
				MainApplication.this.day = day;
				MainApplication.this.hour = hour;
				MainApplication.this.minute = minute;
				MainApplication.this.second = second;
			}

			@Override
			public void onDustParticleDataRecieved(long data1, long data2)
			{
				sendBroadcast(new Intent("com.keruiyun.saike.order").putExtra(
						"addr", SerialSaunaThread.ADDR_DUST_PARTICLE_01).putExtra("data1", data1).putExtra("data2", data2));
			}
		});
	}

	private void recieveSerial(){
		if (null == _thread)
		{
			_thread = new Thread(this);
		}

		if (!_thread.isAlive())
		{
			//_thread.start();
		}

		if (null == _recieveThread)
		{
			_recieveThread = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					byte[] by = new byte[1024*128];
					DatagramPacket packet = new DatagramPacket(by,by.length);

					while (true)
					{
						try
						{
							if (null == _recieveSocket)
							{
//	    						_recieveSocket = new DatagramSocket(8008);
//	    						_recieveSocket.setReuseAddress(true);
								_recieveSocket = new DatagramSocket(null);
								_recieveSocket.setReuseAddress(true);
								_recieveSocket.bind(new InetSocketAddress(8008));
							}

							_recieveSocket.receive(packet);
							String str = new String(packet.getData(), 0, packet.getLength());
							LogCus.msg("收到广播:"+ str);
							if (str.startsWith("offer:"))
							{
								//_peerIP = packet.getAddress().toString().substring(1);
								_peerIP = str.substring(6, str.indexOf("|"));

								if (null != _peerIP && _peerIP.length() > 0)
								{
									Message message = new Message();
									message.what = 1;
									myHandler.sendMessage(message);

									_webRTCClient.answerCall(_peerIP, str.substring(7+_peerIP.length()));
								}
							}
							else if (str.startsWith("answer:"))
							{
								if (null != _peerIP && _peerIP.length() > 0)
								{
									_webRTCClient.confirmCall(_peerIP, str.substring(7));
								}
							}
							else if (str.startsWith("candidate:"))
							{
								if (null != _peerIP && _peerIP.length() > 0)
								{
									_webRTCClient.addCandidate(_peerIP, str.substring(10));
								}
							}
							else
							{
								String[] strArray = str.split("\\|");
								if (strArray.length >= 3)
								{
									if (0 == strArray[0].compareToIgnoreCase("saike_busy"))
									{
										if (null != _peerIP && _peerIP.length() > 0 && 0 == _peerIP.compareToIgnoreCase(strArray[1]))
										{
											sendBroadcast(new Intent("com.keruiyun.saike.closechat"));

											Message message = new Message();
											message.what = 2;
											myHandler.sendMessage(message);
										}
									}
									else if (0 == strArray[0].compareToIgnoreCase("saike_close"))
									{
										if (null != _peerIP && _peerIP.length() > 0 && 0 == _peerIP.compareToIgnoreCase(strArray[1]))
										{
											sendBroadcast(new Intent("com.keruiyun.saike.closechat"));
										}
									}
									else if (0 == strArray[0].compareToIgnoreCase("saike"))
									{
										PeerModel peer = new PeerModel();
										peer.ipAddress = strArray[1];
										peer.name = strArray[2];
										peer.timestamp = System.currentTimeMillis();

										WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
										WifiInfo wifiInfo = wifi.getConnectionInfo();
										int ipAddress = wifiInfo.getIpAddress();
										String strIP = (ipAddress & 0xFF ) + "." +
												((ipAddress >> 8 ) & 0xFF) + "." +
												((ipAddress >> 16 ) & 0xFF) + "." +
												( ipAddress >> 24 & 0xFF);

										if (0 != strIP.compareToIgnoreCase(peer.ipAddress))
										{
											if (AppData.updatePeer(peer))
											{
												sendBroadcast(new Intent("com.keruiyun.saike.peers"));
											}
										}
									}
								}
							}


						}
						catch (Exception ex){
							ex.printStackTrace();
						}
					}
				}
			});
		}

		if (!_recieveThread.isAlive())
		{
			_recieveThread.start();
		}
	}
   
	@Override
	public void onCreate() {
		super.onCreate();

		CrashReport.initCrashReport(getApplicationContext());
		initSerialSaunaService();

		mInstance = this;
		context=getBaseContext();
		ThemeUtils.setSwitchColor(this);
		ToastUtil.init(context);
		SoundPlayer.getInstance().init(context);
		initImageLoader();
		File file = new File(Consts.SAUNA_VIDEO_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}

		recieveSerial();
	    
	    _handler.postDelayed(runnable, 5000);
	    
	    if (null == _webRTCClient)
        {
            initWebRTC();
        }
	}

	private void initWebRTC()
	{
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
	    int screenWidth = 1920;//dm.widthPixels;
		int screenHeight = 1080;//dm.heightPixels;
		
        PeerConnectionParameters params = new PeerConnectionParameters(
                true, false, 640, 480, 24, 1, "VP9", true, 1, "opus", true);

        _webRTCClient = new WebRtcClient(this, params, VideoRendererGui.getEGLContext(), null);
    }
	
	public static MainApplication getInstance() {
		return mInstance;
	}

	public void sendEndCallPacket()
	{
		new Thread(new Runnable()
    	{  
    	    @Override  
    	    public void run() 
    	    {  
            	try
    			{
            		if (null != _peerIP && _peerIP.length() > 0)
            		{
        				if (null == _socket)
        				{
        					_socket = new DatagramSocket();
        					_socket.setReuseAddress(true);
        				}
        				
        				if (null == _group) 
        				{
        					_group = getBroadcastAddress(MainApplication.this);
        				}
        				
        				WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        	            WifiInfo wifiInfo = wifi.getConnectionInfo();       
        	            int ipAddress = wifiInfo.getIpAddress(); 
        	            String strIP = (ipAddress & 0xFF ) + "." +       
        	                           ((ipAddress >> 8 ) & 0xFF) + "." +       
        			                   ((ipAddress >> 16 ) & 0xFF) + "." +       
        			                   ( ipAddress >> 24 & 0xFF);
        	            
        				String html = "saike_close|" + strIP + "|" + strIP;
						LogCus.msg("发送广播:"+ html);
        				byte[] data = html.getBytes();
        				DatagramPacket packet = new DatagramPacket(data,data.length, _group, 8008);
        				_socket.send(packet);


        			}
    			}
    			catch (Exception ex)
    			{
    				ex.printStackTrace();
    			} 
    	    }
    	}).start();
	}
	
	public void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				MainApplication.getInstance())
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
				.memoryCache(new WeakMemoryCache())
				.memoryCacheSize((int) (2 * 1024 * 1024))
				.memoryCacheSizePercentage(13)
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.defaultDisplayImageOptions(defaultOptions).writeDebugLogs() // Remove
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void run() 
	{
		while (true)
		{
			try
			{
				if (null == _socket)
				{
					_socket = new DatagramSocket();
					_socket.setReuseAddress(true);
				}
				
				if (null == _group) 
				{
					_group = getBroadcastAddress(this);
				}
				
				WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	            WifiInfo wifiInfo = wifi.getConnectionInfo();       
	            int ipAddress = wifiInfo.getIpAddress(); 
	            String strIP = (ipAddress & 0xFF ) + "." +       
	                           ((ipAddress >> 8 ) & 0xFF) + "." +       
			                   ((ipAddress >> 16 ) & 0xFF) + "." +       
			                   ( ipAddress >> 24 & 0xFF);
	            
				String html = "saike|" + strIP + "|" + strIP;
				
				byte[] data = html.getBytes();
				DatagramPacket packet = new DatagramPacket(data,data.length, _group, 8008);
				_socket.send(packet);
				
				Log.i("发送广播", html);
				
				Thread.sleep(5000);
			}
			catch (Exception ex)
			{
			} 
		}
	}
	
	Handler _handler = new Handler();  
    Runnable runnable = new Runnable()
    {
        @Override  
        public void run()
        {  
            try 
            {
                if (AppData.refreshPeerList())
                {
                	sendBroadcast(new Intent("com.keruiyun.saike.peers"));
                }
            } 
            catch (Exception ex)
            {
            }  
            
            _handler.postDelayed(this, 5000);
        }  
    };  
    
	public static InetAddress getBroadcastAddress(Context context) throws UnknownHostException {
	    WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	    DhcpInfo dhcp = wifi.getDhcpInfo();
	    if(dhcp==null) {
	        return InetAddress.getByName("255.255.255.255");
	    }
	    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	    byte[] quads = new byte[4];
	    for (int k = 0; k < 4; k++)
	        quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
	    return InetAddress.getByAddress(quads);
	}

	@Override
	public void onAddRemoteStream(MediaStream arg0, String arg1)
	{
		try
		{
			_chatFragment.onAddRemoteStream(arg0, arg1);
		}
		catch (Exception ex)
		{
		}
	}

	@Override
	public void onAnswerCallReady(String arg0)
	{
		try 
        {
			if (null == _socket)
			{
				_socket = new DatagramSocket();
				_socket.setReuseAddress(true);
			}
			
			if (null == _group) 
			{
				_group = getBroadcastAddress(this);
			}
			
			String html = "answer:" + arg0;
			
			byte[] data = html.getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName(_peerIP), 8008);
			_socket.send(packet);
        }
		catch (Exception ex)
		{
		}
	}

	@Override
	public void onCandidate(String arg0) 
	{
		try 
        {
			if (null == _socket)
			{
				_socket = new DatagramSocket();
				_socket.setReuseAddress(true);
			}
			
			if (null == _group) 
			{
				_group = getBroadcastAddress(this);
			}
			
			String html = "candidate:" + arg0;
			
			byte[] data = html.getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName(_peerIP), 8008);
			_socket.send(packet);
        }
		catch (Exception ex)
		{
		}
	}

	@Override
	public void onLocalStream(MediaStream localStream) 
	{
		try
		{
			_chatFragment.onLocalStream(localStream);
		}
		catch (Exception ex)
		{
		}
	}

	@Override
	public void onMakeCallReady(String arg0) 
	{
		try 
        {
			_peerIP = _chatFragment._peer.ipAddress;
			
			if (null == _socket)
			{
				_socket = new DatagramSocket();
				_socket.setReuseAddress(true);
			}
			
			if (null == _group) 
			{
				_group = getBroadcastAddress(this);
			}
			
			WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi.getConnectionInfo();       
            int ipAddress = wifiInfo.getIpAddress(); 
            String strIP = (ipAddress & 0xFF ) + "." +       
                           ((ipAddress >> 8 ) & 0xFF) + "." +       
		                   ((ipAddress >> 16 ) & 0xFF) + "." +       
		                   ( ipAddress >> 24 & 0xFF);
            
			String html = "offer:" + strIP + "|" + arg0;
			
			byte[] data = html.getBytes();
			DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName(_peerIP), 8008);
			_socket.send(packet);
        }
		catch (Exception ex)
		{
		}
	}

	@Override
	public void onRemoveRemoteStream(String arg0) 
	{
		sendBroadcast(new Intent("com.keruiyun.saike.closechat"));
	}

	@Override
	public void onStatusChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int replaceColorById(Context context, @ColorRes int colorId) {
		if (ThemeHelper.isDefaultTheme(context)) {
			return context.getResources().getColor(colorId);
		}
		String theme = getTheme(context);
		if (theme != null) {
			colorId = getThemeColorId(context, colorId, theme);
		}
		return context.getResources().getColor(colorId);
	}

	@Override
	public int replaceColor(Context context, @ColorInt int originColor) {
		if (ThemeHelper.isDefaultTheme(context)) {
			return originColor;
		}
		String theme = getTheme(context);
		int colorId = -1;
		if (theme != null) {
			colorId = getThemeColor(context, originColor, theme);
		}

		return colorId != -1 ? getResources().getColor(colorId) : originColor;
	}

	public static String getTheme(Context context) {
		String color="black";
		switch (ThemeHelper.getTheme(context)){
			case ThemeHelper.THEME_BLACK:
				color="black";
				break;
			case ThemeHelper.THEME_CYAN:
				color="cyan";
				break;
			case ThemeHelper.THEME_GREEN:
				color="green";
				break;
			case ThemeHelper.THEME_PINK:
				color="pink";
				break;
			case ThemeHelper.THEME_YELLOW:
				color="yellow";
				break;


			case ThemeHelper.CARD_SAKURA:
				color="red";
				break;
			case ThemeHelper.CARD_STORM:
				color="blue";
				break;
			case ThemeHelper.CARD_HOPE:
				color="purple";
				break;
			case ThemeHelper.CARD_WOOD:
				color="green";
				break;
			case ThemeHelper.CARD_LIGHT:
				color="lightgreen";
				break;
			case ThemeHelper.CARD_THUNDER:
				color="yellow";
				break;
			case ThemeHelper.CARD_SAND:
				color="orange";
				break;
			case ThemeHelper.CARD_FIREY:
				color="white";
				break;


		}

		return color;
	}

	private
	@ColorRes
	int getThemeColorId(Context context, int colorId, String theme) {
		switch (colorId) {
			case R.color.theme_color_primary:
				return context.getResources().getIdentifier(theme, "color", getPackageName());
			case R.color.theme_color_primary_pressed:
				return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
			case R.color.playbarProgressColor:

				return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
		}
		return colorId;
	}

	private
	@ColorRes
	int getThemeColor(Context context, int color, String theme) {
		LogCus.msg("theme:"+theme+":"+color+":0xffffff:"+(0xffffff));
		switch (color) {
			case 0xffffff:
				return context.getResources().getIdentifier(theme, "color", getPackageName());
		}
		return -1;
	}
}
