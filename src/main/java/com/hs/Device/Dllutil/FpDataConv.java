package com.hs.Device.Dllutil;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface FpDataConv extends Library{
	
	    int FPOK = 0;
		int DATA_VER_70 = 112;   //版本1
		int DATA_VER_80 = 128;   //版本2
		int DATA_VAER_89 = 137;   //版本3
		int MAX_FIR_LEN = 2048;   //数据最大长度
		
		FpDataConv fpDll  = (FpDataConv)Native.loadLibrary("FpDataConv-x64",FpDataConv.class); 
	
	    void FPCONV_Init();
	    
	  //获取dll库版本
	    long FPCONV_GetConvDLLModel(String model);   
	    
	    //验证数据正确否？
	    long FPCONV_GetFpDataValidity(byte[] apFpDataBuffer);   
	    
	  //获取指纹版本和长度 /参数:(1.原始byte[]数据 2.数据版本  3.长度)
	    long FPCONV_GetFpDataVersionAndSize(byte[] apFpDataBuffer,long[] apnVersion,long[] apnSize);  
	    
	  //获取版本长度  /参数:(1.版本号 2.数据长度 )
	    long FPCONV_GetFpDataSize(long anFpDataVersion,long[] apnFpDataSize);     
	      
	    //数据转换为不同版本  /参数:(1.原始byte[]版本 2.原始数据 3.要转成的版本 4.转成后的数据 )
	    long FPCONV_Convert(long anSrcVer, byte[] apSrcFpData,long anDestVer,byte[] apDestFpData);
	    
	    long FPCONV_ISOToPEFIS(byte[] apSrcFpData, int nSrcDataSize, byte[] apDestFpData, int DestDataSize ) ;
	    
	    long FPCONV_PEFISToISO(byte[] apSrcFpData, int nSrcDataSize, byte[] apDestFpData, int DestDataSize ) ;

}
