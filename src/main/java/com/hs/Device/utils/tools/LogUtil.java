package com.hs.Device.utils.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LogUtil {
	private static LogUtil log = null;
	private boolean isDebugMsg = false;
	private LogUtil(boolean isDebugMsg){
		this.isDebugMsg = isDebugMsg;
	}
	public static LogUtil getInstance(boolean isDebugMsg){
		synchronized (LogUtil.class) {
			if(log == null){
				log = new LogUtil(isDebugMsg);
			}
			return log;
		}
	}
	
	public void e(String msg){
		if(isDebugMsg)
			System.out.println("[Error]:"+msg);
	}
	public void e(String type,String msg){
		if(isDebugMsg)
			System.out.println("[Error]:"+type+"---->"+msg);
	}
	public void i(String msg){
		if(isDebugMsg)
			System.out.println("[info]:"+msg);
	}
	public void i(String type,String msg){
		printlogtotxt(type,msg);
		if(isDebugMsg)
			System.out.println("[info]:"+type+"---->"+msg);
	}
	public void t(String tar,String msg){
		if(isDebugMsg)
			System.out.println("[" + tar + "]:" + msg);
	}
	
	private void printlogtotxt(String type,String msg) {
		try { // 闃叉鏂囦欢寤虹珛鎴栬鍙栧け璐ワ紝鐢╟atch鎹曟崏閿欒骞舵墦鍗帮紝涔熷彲浠hrow



			/* 鍐欏叆Txt鏂囦欢 */
			File writename = new File("C:\\Users\\AdminHysoon\\Desktop\\srcproj\\output.txt"); // 鐩稿璺緞锛屽鏋滄病鏈夊垯瑕佸缓绔嬩竴涓柊鐨刼utput銆倀xt鏂囦欢
			if(!writename.exists()) {
				writename.createNewFile(); // 鍒涘缓鏂版枃浠�
			}
			FileWriter fw = new FileWriter(writename,true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println( "[info]:"+type+"---->"+	msg+"\r\n"); // \r\n鍗充负鎹㈣
			pw.flush(); // 鎶婄紦瀛樺尯鍐呭鍘嬪叆鏂囦欢
			fw.flush();
			pw.close(); // 鏈�鍚庤寰楀叧闂枃浠�
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
