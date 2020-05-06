package com.hs.Device.utils.tools;

import java.util.Hashtable;
import java.util.Map;

import com.hs.Device.utils.entity.FKWebTransBlockData;

public class Constant {
	public static Map<String,FKWebTransBlockData> Application = new Hashtable<String, FKWebTransBlockData>();

	public final static String REQ_CODE_RECV_CMD = "receive_cmd";
	public final static String REQ_CODE_SEND_CMD_RESULT = "send_cmd_result";
	public final static String REQ_CODE_REALTIME_GLOG = "realtime_glog";
	public final static String REQ_CODE_REALTIME_ENROLL = "realtime_enroll_data";
	
	public final static String LAST_UPDATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String OFFLINE_TIME = "offlineTime";
	public final static String OFFLINE_CHECK_TIME = "offlineCheckTime";
}
