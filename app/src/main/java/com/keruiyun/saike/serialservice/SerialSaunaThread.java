package com.keruiyun.saike.serialservice;

import android.os.Binder;
import android.util.Log;

import com.keruiyun.saike.UartComm;
import com.keruiyun.saike.util.Consts;

import java.util.LinkedList;
import java.util.Queue;


public class SerialSaunaThread extends Binder
{
	private UartComm UC;
	
	public static final int ADDR_AIR_TEMPERATURE = 40001; // 回风温度
	public static final int ADDR_AIR_HUMIDITY = 40002;    // 回风湿度
	public static final int ADDR_ROOM_PRESSURE = 40003;   // 房间压差
	public static final int ADDR_SET_TEMPERATURE = 40004; // 温度设定
	public static final int ADDR_SET_HUMIDITY = 40005;    // 湿度设定
	public static final int ADDR_SET_PRESSURE = 40006;    // 压差设定
	public static final int ADDR_POWER_KEY = 40007;       // 开/关按键(空调机组)
	public static final int ADDR_DUTY_KEY = 40008;        // 值班按键(空调机组)
	public static final int ADDR_CLEAN_KEY = 40009;       // 消毒按键(空调机组)
	public static final int ADDR_PRESSURE_KEY = 40010;    // 正/负压按键(空调机组)
	public static final int ADDR_CONTROL_KEY = 40011;     // 控制屏--照明1按键    0
													      // 控制屏--照明2按键    1
														  // 控制屏--排风机按键    2
														  // 控制屏--观片灯按键    3
														  // 控制屏--无影灯按键    4
														  // 控制屏--书写台按键    5
														  // 控制屏--手术中按键    6
	public static final int ADDR_STATUS = 40012;          // 机组状态-系统运行    0
														  // 机组状态-值班运行    1
														  // 机组状态-系统故障    2
														  // 机组状态-初效报警    3
														  // 机组状态-中效报警    4
														  // 机组状态-高效报警    5
														  // 机组状态-缺风报警    6
														  // 机组状态-高温报警    7
														  // 机组状态-电加热1运行    8
														  // 机组状态-电加热2运行    9
														  // 机组状态-电加热3运行    10
														  // 机组状态-加湿运行    11
													      // 机组状态-消防状态    12
														  // 机组状态-IT电源状态    13
	public static final int ADDR_AIR_ALARM = 40013;       // 氧气过高报警    0
														  // 氧气过低报警    1
														  // 氮气过高报警    2
														  // 氮气气过低报警    3
														  // 笑气过高报警    4
														  // 笑气过低报警    5
														  // 氩气过高报警    6
														  // 氩气过低报警    7
														  // 压缩空气过高报警    8
														  // 压缩空气过低报警    9
	
	public static final int ADDR_OTHER_ALARM = 40014;     // 二氧化碳过高报警    0
														  // 二氧化碳过低报警    1
														  // 负压吸引过高报警    2
														  // 负压吸引过低报警    3
	public static final int ADDR_HANDFREE_KEY = 40015;    // 免提按键
	public static final int ADDR_CALL_KEY = 40016;        // 呼叫按键
	public static final int ADDR_CALL_ALL_KEY = 40017;    // 群呼按键
	public static final int ADDR_MUSIC_KEY = 40018;       // 背景音乐按键
	public static final int ADDR_VOLUMN_KEY = 40019;      // 背景音乐音量
	public static final int ADDR_PHONE_KEY = 40020;       // 电话号码
	public static final int ADDR_LIGHT_OFF_TINT   = 40021; // 照明延时关闭提示信息
	public static final int ADDR_LIGHT_OFF_SET    = 40022; // 照明延时关闭时间
	public static final int ADDR_HOST_SLAVE       = 40023; // 通讯设置--主从站设置
	public static final int ADDR_SLAVE_ADDRESS    = 40024; // 通讯设置--从站地址设置
	public static final int ADDR_DATE_FLAG        = 40025; // 日期时间更新标志位
	public static final int ADDR_DATE_YEAR        = 40026; // 年份
	public static final int ADDR_DATE_MONTH       = 40027; // 月份
	                                                       // 日
	public static final int ADDR_DATE_HOUR        = 40028; // 小时
	                                                       // 分钟
	public static final int ADDR_DATE_SECOND      = 40029; // 秒
	                                                       // 星期
	public static final int ADDR_DUST_PARTICLE_01 = 40030; // 0.5μm尘埃粒子
	public static final int ADDR_DUST_PARTICLE_02 = 40032; // 5μm尘埃粒子
	public static final int ADDR_AIR_PRESS_01     = 40034; // 数显压力值1
	public static final int ADDR_AIR_PRESS_02     = 40035; // 数显压力值2
	public static final int ADDR_AIR_PRESS_03     = 40036; // 数显压力值3
	public static final int ADDR_AIR_PRESS_04     = 40037; // 数显压力值4
	public static final int ADDR_AIR_PRESS_05     = 40038; // 数显压力值5
	public static final int ADDR_AIR_PRESS_06     = 40039; // 数显压力值6
	public static final int ADDR_AIR_PRESS_07     = 40040; // 数显压力值7
	
	
	private int _recieveStep = 0;
	
	Thread sendthread;
	
	SerialSaunaService serialservice;
	
	private SerialSaikeListener _listener;
	
	public static Queue<SerialCommand> queue = new LinkedList<SerialCommand>();
	
	int[] sendBuffer = new int[256];
	
	public SerialSaunaThread(SerialSaunaService service, UartComm UC)
	{
        serialservice = service;
        this.UC = UC;
		
		try 
		{
			start();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public void start()
	{ 
		if (null == sendthread)
		{
			sendthread = new Thread(new Runnable()
		    {
			    public void run()
			    {
				    SendRun();
			    }
		    });
		}
		
		if (!sendthread.isAlive()) 
		{
			sendthread.start();
		}
	}
	
	public void SendRun()
	{
		SerialCommand cmd = new SerialCommand();
		int[] buffer = new int[1];
		
		boolean isWrite = false;
		
		while (true)
		{
			try
			{
		        if (!queue.isEmpty())
		        {
		        	if (isWrite)
		        	{
		        		Thread.sleep(200);
		        	}
		        	
		            UC.setRS485Read(0);

		            SerialCommand sendCmd = queue.poll();
				    SendSerial(sendCmd);
					    
		            UC.setRS485Read(1);
		            
		            isWrite = true;
		        } 
		        else 
		        {
		        	isWrite = false;
		        	
		         	UC.setRS485Read(1);
		         	
		            try
					{
		            	if (0 != UC.recv(buffer, 1))
		            	{
							cmd.cmd[cmd.len++] = (byte)buffer[0];
	
							if (1 == _recieveStep || !Consts.IS_READ_BY_TWO_STEP)
							{
								if (cmd.len >= 63)
								{
									StringBuffer buf=new StringBuffer();
									for (int i=0;i<cmd.len;i++){
										buf.append(cmd.cmd[i]);
									}
//									Log.i("接收：63：",buf.toString()+"");
				                    int crc = Modbus.doCRC16(cmd.cmd, 0, 61);
				                    if (cmd.cmd[61] == (byte)(crc & 0xFF)
				                    	&& cmd.cmd[62] == (byte)((crc >> 8) & 0xFF))
				                    {
					                    if (null != _listener)
					                    {
					                    	_listener.onDataRecieved(ADDR_AIR_TEMPERATURE, (short)(((cmd.cmd[3] << 8) & 0xff00) | (cmd.cmd[4] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_HUMIDITY, (short)(((cmd.cmd[5] << 8) & 0xff00) | (cmd.cmd[6] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_ROOM_PRESSURE, (short)(((cmd.cmd[7] << 8) & 0xff00) | (cmd.cmd[8] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_SET_TEMPERATURE, (short)(((cmd.cmd[9] << 8) & 0xff00) | (cmd.cmd[10] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_SET_HUMIDITY, (short)(((cmd.cmd[11] << 8) & 0xff00) | (cmd.cmd[12] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_SET_PRESSURE, (short)(((cmd.cmd[13] << 8) & 0xff00) | (cmd.cmd[14] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_POWER_KEY, (short)(((cmd.cmd[15] << 8) & 0xff00) | (cmd.cmd[16] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_DUTY_KEY, (short)(((cmd.cmd[17] << 8) & 0xff00) | (cmd.cmd[18] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_CLEAN_KEY, (short)(((cmd.cmd[19] << 8) & 0xff00) | (cmd.cmd[20] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_PRESSURE_KEY, (short)(((cmd.cmd[21] << 8) & 0xff00) | (cmd.cmd[22] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_CONTROL_KEY, (short)(((cmd.cmd[23] << 8) & 0xff00) | (cmd.cmd[24] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_STATUS, (short)(((cmd.cmd[25] << 8) & 0xff00) | (cmd.cmd[26] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_ALARM, (short)(((cmd.cmd[27] << 8) & 0xff00) | (cmd.cmd[28] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_OTHER_ALARM, (short)(((cmd.cmd[29] << 8) & 0xff00) | (cmd.cmd[30] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_HANDFREE_KEY, (short)(((cmd.cmd[31] << 8) & 0xff00) | (cmd.cmd[32] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_CALL_KEY, (short)(((cmd.cmd[33] << 8) & 0xff00) | (cmd.cmd[34] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_CALL_ALL_KEY, (short)(((cmd.cmd[35] << 8) & 0xff00) | (cmd.cmd[36] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_MUSIC_KEY, (short)(((cmd.cmd[37] << 8) & 0xff00) | (cmd.cmd[38] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_VOLUMN_KEY, (short)(((cmd.cmd[39] << 8) & 0xff00) | (cmd.cmd[40] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_LIGHT_OFF_TINT, (short)(((cmd.cmd[43] << 8) & 0xff00) | (cmd.cmd[44] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_LIGHT_OFF_SET, (short)(((cmd.cmd[45] << 8) & 0xff00) | (cmd.cmd[46] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_HOST_SLAVE, (short)(((cmd.cmd[47] << 8) & 0xff00) | (cmd.cmd[48] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_SLAVE_ADDRESS, (short)(((cmd.cmd[49] << 8) & 0xff00) | (cmd.cmd[50] & 0xff)));
					                    	
					                    	_listener.onGPSDateRecieved((short)(((cmd.cmd[51] << 8) & 0xff00) | (cmd.cmd[52] & 0xff)), 
					                    			(short)(((cmd.cmd[53] << 8) & 0xff00) | (cmd.cmd[54] & 0xff)),
					                    			(short)cmd.cmd[55],
					                    			(short)cmd.cmd[56],
					                    			(short)cmd.cmd[57],
					                    			(short)cmd.cmd[58],
					                    			(short)cmd.cmd[59]);
					                    }
				                    }
				                    
									cmd.len = 0;
								}
							}
							else if (0 == _recieveStep)
							{
								if (cmd.len >= 27)
								{
									StringBuffer buf=new StringBuffer();
									for (int i=0;i<cmd.len;i++){
										buf.append(cmd.cmd[i]);
									}
//									Log.i("接收：27：",buf.toString()+"");
				                    int crc = Modbus.doCRC16(cmd.cmd, 0, 25);
				                    if (cmd.cmd[25] == (byte)(crc & 0xFF)
				                    	&& cmd.cmd[26] == (byte)((crc >> 8) & 0xFF))
				                    {
					                    if (null != _listener)
					                    {
					                    	_listener.onDustParticleDataRecieved((long)(((cmd.cmd[3] << 24) & 0xff000000) | ((cmd.cmd[4] << 16) & 0xff0000) | ((cmd.cmd[5] << 8) & 0xff00) | (cmd.cmd[6] & 0xff)),
					                    			(long)(((cmd.cmd[7] << 24) & 0xff000000) | ((cmd.cmd[8] << 16) & 0xff0000) | ((cmd.cmd[9] << 8) & 0xff00) | (cmd.cmd[10] & 0xff)));
					                    	
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_01, (short)(((cmd.cmd[11] << 8) & 0xff00) | (cmd.cmd[12] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_02, (short)(((cmd.cmd[13] << 8) & 0xff00) | (cmd.cmd[14] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_03, (short)(((cmd.cmd[15] << 8) & 0xff00) | (cmd.cmd[16] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_04, (short)(((cmd.cmd[17] << 8) & 0xff00) | (cmd.cmd[18] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_05, (short)(((cmd.cmd[19] << 8) & 0xff00) | (cmd.cmd[20] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_06, (short)(((cmd.cmd[21] << 8) & 0xff00) | (cmd.cmd[22] & 0xff)));
					                    	_listener.onDataRecieved(ADDR_AIR_PRESS_07, (short)(((cmd.cmd[23] << 8) & 0xff00) | (cmd.cmd[24] & 0xff)));
					                    }
				                    }
				                    
									cmd.len = 0;
								}
							}
		            	}
		            	else
		            	{
		            		cmd.len = 0;
//							Log.i("接收：：","null");
		            		if (queue.isEmpty())
		            		{
		            			if (0 == _recieveStep)
		            			{
		            				if (Consts.IS_READ_BY_TWO_STEP)
		            				{
		            				    _recieveStep = 1;
		            				}
									SerialSaunaThread.readCmdQueue(1, SerialSaunaThread.ADDR_AIR_TEMPERATURE, 29);

		            			}
		            			else
		            			{
		            				if (Consts.IS_READ_BY_TWO_STEP)
		            				{
		            				    _recieveStep = 0;
		            				}
		            				
		            			    SerialSaunaThread.readCmdQueue(1, SerialSaunaThread.ADDR_DUST_PARTICLE_01, 11);
		            			}
		            		}
		            	}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
						
						cmd.len = 0;
					}
		        }
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void setListener(SerialSaikeListener listener)
    {
    	_listener = listener;
	}
	
	public void SendSerial(SerialCommand cmd)
	{
		try
		{
			for (int i = 0; i < cmd.len; i++)
			{
				sendBuffer[i] = cmd.cmd[i];
			}
			
			UC.send(sendBuffer, cmd.len);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static SerialCommand cmdQueue(int slave, int cmd, byte[] value)
	{		
		SerialCommand command = new SerialCommand();
		command.cmd[0] = (byte)slave;
		command.cmd[1] = (byte)cmd;
		
		for (int i = 0; i < value.length; i++)
		{
			command.cmd[2+i] = value[i];
		}
		
		int crc = Modbus.doCRC16(command.cmd, 0, 2+value.length);
		command.cmd[2+value.length] = (byte)(crc & 0xFF);
		command.cmd[3+value.length] = (byte)((crc >> 8) & 0xFF);
		
		command.len = 4+value.length;
		queue.offer(command);
		
		return command;
	}
	
	public static void readCmdQueue(int slave, int addr, int len)
	{		
		addr = addr - 40001;
		
		byte[] value = { 0x00, 0x00, 0x00, 0x00 };
		value[0] = (byte)((addr >> 8) & 0xFF);
		value[1] = (byte)(addr & 0xFF);
		value[2] = (byte)((len >> 8) & 0xFF);
		value[3] = (byte)(len & 0xFF);
		
		cmdQueue(slave, 3, value);
	}
	
	public static void writeCmdQueue(int slave, int addr, int data)
	{		
		addr = addr - 40001;
		
		byte[] value = { 0x00, 0x00, 0x00, 0x00 };
		value[0] = (byte)((addr >> 8) & 0xFF);
		value[1] = (byte)(addr & 0xFF);
		value[2] = (byte)((data >> 8) & 0xFF);
		value[3] = (byte)(data & 0xFF);
		
		cmdQueue(slave, 6, value);
	}
}
