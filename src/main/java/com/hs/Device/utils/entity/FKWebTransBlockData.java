package com.hs.Device.utils.entity;

import java.util.Date;

public class FKWebTransBlockData {
	public int LastBlockNo;
	public Date TmLastModified;
    private byte[] abytBlkData;
    
    
	public FKWebTransBlockData() {
		LastBlockNo = 0;
		TmLastModified = new Date();
		abytBlkData = new byte[0];
	}
	public byte[] getAbytBlkData() {
		return abytBlkData;
	}
	public void setAbytBlkData(byte[] Data) {
		if (Data.length == 0)
			return;
		int len_dest = Data.length + abytBlkData.length;
        byte[] bytTmp = new byte[len_dest];
        //组装bytTmp,若abytBlkData长度不是0，将它的内容拷给byteTmp的前段，
        if(!(abytBlkData.length==0)){
        	System.arraycopy(abytBlkData, 0, bytTmp, 0, abytBlkData.length);
        }
        //将Date的内容拷给byteTmp的后段。若abytBlkData长度0，就是Data的内容。
        System.arraycopy(Data, 0, bytTmp, abytBlkData.length, Data.length);
        abytBlkData = bytTmp;
	}
}
