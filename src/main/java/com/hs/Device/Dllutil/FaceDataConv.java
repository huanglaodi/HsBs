package com.hs.Device.Dllutil;

import com.sun.jna.Library;
import com.sun.jna.Native;

//FaceDataConv-x64.dll  face库接口
public interface FaceDataConv extends Library {
	static long face1 = (0x100); //256
	static long face2 = (0x210);  //528
	
	FaceDataConv faceDll  = (FaceDataConv)Native.loadLibrary("FaceDataConv-x64",FaceDataConv.class);  
	
	long _FcConvFaceEnrollData(byte[] data,long nLength, long nConvType); 
    

}
