package com.keruiyun.saike;

public class UartComm {
	static {
		System.loadLibrary("SerialAPI");
	}


    /*
     * 初始化串口 device为需要打开串口的名字，如/dev/ttyS1 /dev/ttyS3. RS485固定为/dev/ttyS3
     * */
    public native int uartInit(String device);
    /*
     * 释放串口资源
     * */
    public native int uartDestroy();
    /*
     * 设置串口属性
     * baud: 波特率
     * dataBits:数据位数
     * parity: 校验
     * stopBits:停止位
     * 如setOPt(9600,8,0,1)为设置9600波特率， 8N1属性
    */
    public native int setOpt(int baud, int dataBits, int parity, int stopBits);

    /**
     * 把数据发送出去
     * byteBuf:数据缓冲
     * length: 要发送数据的长度
     */
    public native int send(int[] val, int length);

    /**
     *读取串口数据
     * byteBuf： 数据缓冲
     * length: 要读取的数据长度
     * return :
     */
    public native int recv(int[] val, int length);
    /*
     * 把485设置为读模式或者写模式
     * read  1 : 设置为读模式
     * 		 0 : 设置为写模式
     * */
    public native int setRS485Read(int read);




}
