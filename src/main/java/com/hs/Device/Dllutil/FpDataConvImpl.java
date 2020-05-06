package com.hs.Device.Dllutil;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import com.hs.Device.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FpDataConvImpl{
	
	
	public void init() {
		FpDataConv.fpDll.FPCONV_Init();
		
	}

	public long GetConvDLLModel(String model) {
		long a = FpDataConv.fpDll.FPCONV_GetConvDLLModel(model);
		return 0;
	}

	
	public long GetFpDataValidity(byte[] apFpDataBuffer) {
		long a = FpDataConv.fpDll.FPCONV_GetFpDataValidity(apFpDataBuffer);
		return a;
	}

	
	public long GetVersionAndSize(byte[] apFpDataBuffer, long[] apnVersion, long[] apnSize) {
		long a = FpDataConv.fpDll.FPCONV_GetFpDataVersionAndSize(apFpDataBuffer, apnVersion, apnSize);
		return a;
	}

	
	public long GetSize(long anFpDataVersion, long[] apnFpDataSize) {
		long a = FpDataConv.fpDll.FPCONV_GetFpDataSize(anFpDataVersion, apnFpDataSize);
		return a;
	}

	public long changefp(long anSrcVer, byte[] apSrcFpData, long anDestVer, byte[] apDestFpData) {
		long a = FpDataConv.fpDll.FPCONV_Convert(anSrcVer, apSrcFpData, anDestVer, apDestFpData);
		return 0;
	}

	public long ISOToPEFIS(byte[] apSrcFpData, int nSrcDataSize, byte[] apDestFpData, int DestDataSize) {
		
		return 0;
	}

	public long PEFISToISO(byte[] apSrcFpData, int nSrcDataSize, byte[] apDestFpData, int DestDataSize) {
		
		return 0;
	}
	
	@Test
	public void aa() {
		InputStream in =null;
		try {
		 in = new FileInputStream("C:/Users/Admin/Desktop/fps1.a");
		byte[] bytes = FileCopyUtils.copyToByteArray(in);
		FpDataConvImpl fp = new FpDataConvImpl();
		
		long isfp = fp.GetFpDataValidity(bytes);
		System.out.println("数据是否正确："+isfp);
		
		long[] ver = new long[2];
		long[] size = new long[2];
		long a = fp.GetVersionAndSize(bytes, ver, size);
		System.out.println("获取数据版本与长度：执行结果:"+a+"  版本："+ver[0]+"  长度"+size[0]);
		
		long[] si = new long[1];
		long tt = fp.GetSize(ver[0], si);
		System.out.println("获取版本长度：执行结果:"+tt+"  版本："+ver[0]+"  长度"+si[0]);
		
		long ver2 =  FpDataConv.DATA_VER_70;
		long[] size2 = new long[1];
		long aa = fp.GetSize(ver2, size2);
		System.out.println("获取要改变版本长度：执行结果:"+aa+"  版本："+ver2+"  长度"+size2[0]);
		
		byte[] bytes2 = new byte[(int)size2[0]];
		long b =fp.changefp(ver[0], bytes, ver2, bytes2);
		Utils.copydatatofile(bytes2, "fps2");
		System.out.println("改变数据：执行结果:"+b+"  版本："+ver2+"  长度"+bytes2.length);
		
		long[] ver3 = new long[1];
		long[] size3 = new long[1];
		long c = fp.GetVersionAndSize(bytes2, ver3, size3);
		System.out.println("改变后的数据长度与版本：执行结果:"+c+"  版本："+ver3[0]+"  长度"+size3[0]);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			in.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void bb() {
		InputStream in =null;
		try {
		 in = new FileInputStream("C:/Users/Admin/Desktop/face");
		byte[] bytes = FileCopyUtils.copyToByteArray(in);
		FaceDataConvImpl fc = new FaceDataConvImpl();
		long a = fc.changeface(bytes, bytes.length, FaceDataConv.face1);
		System.out.println(a);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			in.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testlength() {
		String aa = "gegewgw";
		int len = aa.length();
		byte[] bb = aa.getBytes();
		int len2 = bb.length;
		System.out.println(len+"----"+len2);
		
	}
	
}
