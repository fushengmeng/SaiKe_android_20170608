/*
 ============================================================================
 Name        : XMPPClientListener.java
 Version     : 1.0.0
 Copyright   : www.keruiyun.com
 Description : 
 ============================================================================
 */

package com.keruiyun.saike;


public interface MenuFragmentListener
{
	public void onMusicSelect(int[] ids, String[] titles, String[] artists, int position);
	public void onMusicPlay();
	public void onMusicStop();
	public void onMusicPlayOrder(int playOrder);
}
