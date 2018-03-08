package com.keruiyun.saike;

import java.util.ArrayList;

import com.keruiyun.saike.model.PeerModel;


public class AppData 
{
	private static ArrayList<PeerModel> _peerArray = new ArrayList<PeerModel>();

	public static ArrayList<PeerModel> getPeerArray()
	{
		return _peerArray;
	}
	
	public static boolean updatePeer(PeerModel peer) 
	{
		boolean ret = false;
		
		boolean isFound = false;
		
		for (PeerModel tempPeer : _peerArray)
		{
			if (0 == tempPeer.ipAddress.compareToIgnoreCase(peer.ipAddress))
			{
				if (0 != tempPeer.name.compareToIgnoreCase(peer.name))
				{
					ret = true;
				}
				
				tempPeer.name = peer.name;
				tempPeer.timestamp = peer.timestamp;
				
				isFound = true;
				
				break;
			}
		}
		
		if (!isFound)
		{
		    _peerArray.add(peer);
		    
		    ret = true;
		}
		
		return ret;
	}

	public static boolean refreshPeerList()
	{
		boolean ret = false;
		
		ArrayList<PeerModel> tempPeerArray = new ArrayList<PeerModel>();
		
		long timestamp = System.currentTimeMillis();
		
		for (PeerModel tempPeer : _peerArray)
		{
			if ((timestamp-tempPeer.timestamp)/1000 <= 5)
			{
				tempPeerArray.add(tempPeer);
			}
		}
		
		ret = (tempPeerArray.size() == _peerArray.size() ? false : true);
		
		_peerArray.clear();
		_peerArray.addAll(tempPeerArray);
		
		return ret;
	}

}
