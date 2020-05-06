package com.hs.Device.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.FileCopyUtils;

import com.hs.Device.utils.tools.MyUtil;

public class Utils {
	
	public static byte[] CreateBSCommBufferFromString(byte[] cmdbyte, String sCmdParam) {
		try {
			if (sCmdParam.length() == 0) {
				return new byte[0];
			} else {
				byte[] bytText = sCmdParam.getBytes("UTF-8");
				byte[] bytTextLen = int2byte(bytText.length + 1);

				cmdbyte = new byte[4 + bytText.length + 1];
				System.arraycopy(bytTextLen, 0, cmdbyte, 0, bytTextLen.length);
				System.arraycopy(bytText, 0, cmdbyte, 4, bytText.length);
				cmdbyte[4 + bytText.length] = 0;
				return cmdbyte;
			}
		} catch (Exception e) {
			cmdbyte = new byte[0];
			return new byte[0];
		}
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// ���λ
		targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
		targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
		targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
		return targets;
	}

	
	 //获取request输入流byte[]
	public static byte[] GetRequestStreamBytes(HttpServletRequest request) {
		int lenContent = request.getContentLength();
		byte[] byteRequestBin = new byte[0];
		if (lenContent < 1) {
			return byteRequestBin;
		}
		InputStream streamIn;
		byte[] bytRecv = new byte[lenContent];
		try {
			streamIn = request.getInputStream();
			int lenRead = 0, readlen = 0;

			while ((readlen = streamIn.read(bytRecv, lenRead, 1024)) != -1) {
				lenRead += readlen;
			}
			// lenRead = streamIn.read(bytRecv, 0, lenContent);
			if (lenRead != lenContent) {
				return byteRequestBin;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteRequestBin = bytRecv;
	//	return lenContent;
		return byteRequestBin;
		
	}

	// request输入流byte[]转String，不包含指纹等信息
	public static String GetStringFromBSCommBuffer(byte[] abytBSComm) {
		if (abytBSComm.length < 4)
			return "";
		try {
			String sRet;

			int lenText = MyUtil.byte2int(abytBSComm);
			if (lenText > abytBSComm.length - 4){
				return "";
			}
			if (lenText == 0){
				return "";
			}
			if (abytBSComm[4 + lenText - 1] == 0){ // if last value of string
													// buufer is 0x0
				sRet = MyUtil.byte2String(abytBSComm, 4, lenText - 1);
			}
			else{
				sRet = MyUtil.byte2String(abytBSComm, 4, lenText);
			}
			return sRet;
		} catch (Exception e) {
			return "";
		}
	}
	
	// request输入流byte[]转map，包含指纹等信息
	public static Map GetStringAnd1stBinaryFromBSCommBuffer(byte[] abytBSComm) {
		Map<String ,Object> map = new HashedMap();
		if (abytBSComm.length < 4){
			return map;
		}
		try
		{
			String sRet;
			int lenText = MyUtil.byte2int(abytBSComm);
			if (lenText > abytBSComm.length - 4){
				return map;
			}
			if (lenText == 0)
			{
				return map;
			}
			else
			{
				if (abytBSComm[4 + lenText - 1] == 0) // if last value of string buufer is 0x0
					map.put("jsonstr", MyUtil.byte2String(abytBSComm, 4, lenText - 1));
				else
					map.put("jsonstr",MyUtil.byte2String(abytBSComm, 4, lenText));
			}
			int posBin = 4 + lenText;  //json加上自己的长度
			int lenBin = MyUtil.byte2int(abytBSComm, posBin);   //data第一个数据的长度
			if (lenBin < 1){
				return map;
			}
			if (lenBin > abytBSComm.length - posBin - 4){    //第一个数据长度若大于data真实总长
				return map;
			}
			byte[] by = new byte[abytBSComm.length-posBin];
			System.arraycopy(abytBSComm, posBin, by, 0, by.length);
			map.put("data", by);
			return map;
		}
		catch(Exception e)
		{
			return map;
		}
	}
	
	//组装data,在后面追加每个人脸或指纹等数据时，带上4个字节的数据长度再带上数据。以此类推。
	public static byte[] AppendBinData(byte[] fbyte, byte[] abytToAdd) {
		try
		{
			
			byte[] bytToAdd = abytToAdd;

			if (bytToAdd.length == 0) {
				return fbyte;
			}

			int len_dest = fbyte.length + 4 + bytToAdd.length;
			byte[] bytRet = new byte[len_dest];
			byte[] bytAddLen = MyUtil.int2byte(bytToAdd.length);
			System.arraycopy(fbyte, 0, bytRet, 0, fbyte.length);
			System.arraycopy(bytAddLen, 0, bytRet, fbyte.length, 4);
			System.arraycopy(bytToAdd, 0, bytRet, fbyte.length + 4, bytToAdd.length);
			
			return bytRet;
		}
		catch (Exception e)
		{
			return fbyte;
		}
	}
	
	
	public static byte[] intToByteArray(int i){  
	   byte[] result = new byte[4];  
		
		result[0] = (byte) ((i >> 24) & 0xFF);  
		result[1] = (byte) ((i >> 16) & 0xFF);  
		result[2] = (byte) ((i >> 8) & 0xFF);  
		result[3] = (byte) (i & 0xFF);  
		return result;  
		}
	
	//数组排序
	public static int[] arrayint(int[] a) {
		for(int i=0;i<a.length-1;i++) {
			for(int u=i+1;u<a.length;u++) {
				int num = 0;
				if(a[i]>a[u]) {
					num = a[i];
					a[i]= a[u];
					a[u]=num;
					
				}
			}
		}
		return a;
		
	}
	
	//查询元素在数组中的位置
	public static int getindex(int[] a,int item) {
		int idex =-2;
		for(int i=0;i<a.length;i++) {
			if(item == a[i]) {
				idex = i;
				return idex;
			}else {
				
			}
		}
		if(idex>=0) {
			return idex;
		}else {
			return -1;
		}
		
	}
	
	public static int byte2int(byte[] res) {   
		byte[] res0 = Arrays.copyOf(res, 4);
		int targets = (res0[0] & 0xff) | ((res0[1] << 8) & 0xff00) 
				| ((res0[2] << 24) >>> 8) | (res0[3] << 24);
		return targets;   
	}
	
	public static byte[] upload() {
		InputStream in =null;
		byte[] bytes = new byte[0];
		try {
			in = new FileInputStream("C:/Users/Admin/Desktop/1.jpg");
			bytes = FileCopyUtils.copyToByteArray(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	//copy
	public static void copydatatofile(byte[] data,String filename) {
		InputStream in =null;
		OutputStream out = null;
		
		try {
			in = new ByteArrayInputStream(data);
			out = new BufferedOutputStream(new FileOutputStream("C:/Users/Admin/Desktop/"+filename));
			byte[] bytes = new byte[1024];
			int num = 0;
			while(true) {
				num = in.read(bytes);
				if(num == -1) {
					break;
				}
				out.write(bytes,0,num);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(out!= null) {
					out.close();
				}if(in!=null) {
					in.close();
				}
				
			}catch(Exception e) {
				e.printStackTrace();
		}
		
	}
	}
	
	//指纹人脸下载
	public static void downfaceorfps(HttpServletResponse response,InputStream in,String type) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			String filename = sdf.format(date);
			filename = URLEncoder.encode(filename,"UTF-8");
			    response.setHeader("Content-Disposition","attachment;filename="+filename+"_"+type);
	            response.setContentType("application/octet-stream");
	            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
	           
	            byte[] by = new byte[1024];
	            while(true){
	            	int num = in.read(by);
	            	if(num == -1) {
	            		break;
	            	}
	                out.write(by,0,num);
	                out.flush();
	            }
	            out.close();
	        } catch (Exception e) {
	            System.out.println("下载出错");
	        }
		}
		
	
}
