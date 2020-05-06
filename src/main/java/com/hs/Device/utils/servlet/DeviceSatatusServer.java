package com.hs.Device.utils.servlet;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.hs.Device.utils.tools.Config;
import com.hs.Device.utils.tools.Constant;
import com.hs.Device.utils.tools.DBUtil;

public class DeviceSatatusServer extends Thread{
	private boolean stopRunning = true;
	private SimpleDateFormat sdf = new SimpleDateFormat(Constant.LAST_UPDATE_TIME_FORMAT);
	private static DeviceSatatusServer instance = null;
	private long offlineCheckTime = Config.getInt(Constant.OFFLINE_CHECK_TIME);
	private long offlineTime = Config.getInt(Constant.OFFLINE_TIME);
	
	@Override
	public void run() {
		System.out.println("[DeviceSatatusServer]:Start");
		stopRunning = false;
		while(!stopRunning){
			try {
				Thread.sleep(offlineCheckTime);
			} catch (InterruptedException e) {
				System.out.println("[DeviceSatatusServer]:Error");
				e.printStackTrace();
			}
			checkDeviceStatus();
		}
	}
	
	private DeviceSatatusServer(){
		if(stopRunning == true)
			this.start();
	}
	
	public static DeviceSatatusServer getInstance(){
		if(instance == null){
			instance = new DeviceSatatusServer();
		}
		return instance;
	}
	
	private void checkDeviceStatus() {
		String sSql = "select device_id,connected,last_update_time from tbl_fkdevice_status";
		List<Map<String, Object>> list = DBUtil.select(sSql);
		for (int i = 0; i < list.size(); i++) {
			if("1".equals(list.get(i).get("connected").toString()))
				updateDeviceStatus(list.get(i).get("device_id").toString(),list.get(i).get("last_update_time").toString());
		}
	}

	private boolean updateDeviceStatus(String deviceId, String lastUpdateTime){
		try {
			long time = System.currentTimeMillis() - sdf.parse(lastUpdateTime).getTime();
			if(time > offlineTime ){
				String sSql = "UPDATE tbl_fkdevice_status SET connected='0' where device_id='" + deviceId + "'";
				DBUtil.update(sSql, null);
			}
		} catch (Exception e) {
			System.out.println("[DeviceSatatusServer]:Error");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close(){
		this.stopRunning = true;
	}
}
