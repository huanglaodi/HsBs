package com.hs.Device.utils.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static final String PROPERTIES_NAME = "setting.properties";
	public static final String ROOTPATH;
	private static Properties pro = new Properties();
	private static String path;
	static{
		ROOTPATH = new File(Config.class.getClassLoader().getResource(File.separator).getPath()).getParentFile().getParent();
		path = ROOTPATH + File.separator + "config" + File.separator + PROPERTIES_NAME;
		ProLoad(path);
	}

	private Config() {
	}

   	private static void ProLoad(String path) {
		InputStream in = null;	
		try {
//			in = Config.class.getClassLoader().getResourceAsStream(path);
			in = new FileInputStream(path);
			pro.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){try{in.close();in=null;}catch(Exception e){}}
		}
	}
	
	public static String getString(String key) {
		return pro.getProperty(key);
	}
	public static Integer getInt(String key) {
		return Integer.parseInt(pro.getProperty(key, "0"));
	}
	public static boolean getbool(String key) {
		return "1".equals(pro.getProperty(key));
	}
}
