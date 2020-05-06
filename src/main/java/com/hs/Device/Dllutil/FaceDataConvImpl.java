package com.hs.Device.Dllutil;

public class FaceDataConvImpl{
	

	public long changeface(byte[] data, long nLength, long nConvType) {
		long a= FaceDataConv.faceDll._FcConvFaceEnrollData(data, nLength,nConvType);
		return a;
	}

}
