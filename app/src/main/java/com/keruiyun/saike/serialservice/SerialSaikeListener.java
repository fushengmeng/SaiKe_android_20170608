/*
 ============================================================================
 Name        : SerialSaikeListener.java
 Version     : 1.0.0
 Copyright   : www.keruiyun.com
 Description : 
 ============================================================================
 */

package com.keruiyun.saike.serialservice;


public interface SerialSaikeListener
{
	public void onDataRecieved(int addr, short data);
	public void onGPSDateRecieved(short enabled, short year, short month, short day,
                                  short hour, short minute, short second);
	public void onDustParticleDataRecieved(long data1, long data2);
}
