package com.keruiyun.saike.serialservice;



import com.keruiyun.saike.UartComm;


public class SerialSaunaService
{
	private UartComm UC;
	SerialSaunaThread serialthread;
	Thread _thread;
	boolean isStop = false;

	public void start()
	{
		try
        { 
			UC = new UartComm();
			
			UC.uartInit("/dev/ttyS3");
    		UC.setOpt(9600, 8, 0, 1);
        	
            if (null == serialthread) 
            {
            	serialthread = new SerialSaunaThread(this, UC);
            }
            
            serialthread.start();
            
            /*
            _thread = new Thread()
            {
                @Override  
                public void run() 
                {  
                	while (true)
                	{
                        if (isStop)
                        {
                        	break;
                        }
                        
	                    try
	                    {
	                    	sleep(1000);
	                    	
	                    	SerialSaunaThread.readCmdQueue(1, SerialSaunaThread.ADDR_AIR_TEMPERATURE, 19);
	                    }
	                    catch (Exception ex)
	                    {
	                    	ex.printStackTrace();
	                    }
                	}
                }  
            };
            _thread.start();
            */
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	public void setListener(SerialSaikeListener listener)
    {
        if (null != serialthread) 
        {
        	serialthread.setListener(listener);
        }
	}


}
