package com.hs.Device.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hs.Device.mapper.DeviceEnrollArrayMapper;
import com.hs.Device.mapper.DeviceLogMapper;
import com.hs.Device.mapper.DeviceMapper;
import com.hs.Device.mapper.DeviceStateMapper;
import com.hs.Device.mapper.DoorSettingMapper;
import com.hs.Device.mapper.FpsMapper;
import com.hs.Device.mapper.TimeZoneMapper;
import com.hs.Device.mapper.UserGetMapper;
import com.hs.Device.mapper.UserIdListMapper;
import com.hs.Device.mapper.UserParseTimeMapper;
import com.hs.Device.mapper.UsersMapper;
import com.hs.Device.model.DeviceEnrollArray;
import com.hs.Device.model.DeviceLog;
import com.hs.Device.model.DeviceState;
import com.hs.Device.model.DoorSetting;
import com.hs.Device.model.Fps;
import com.hs.Device.model.Task;
import com.hs.Device.model.TimeZone;
import com.hs.Device.model.UserGet;
import com.hs.Device.model.UserIdList;
import com.hs.Device.model.UserParseTime;
import com.hs.Device.model.UserSend;
import com.hs.Device.model.Users;
import com.hs.Device.utils.Utils;

@Service
public class X1000 {

	@Autowired
	UsersMapper usersMapper;
	@Autowired
	DeviceEnrollArrayMapper deviceEnrollMapper;
	@Autowired
	DeviceLogMapper devicelogMapper;
	@Autowired
	UserGetMapper usergetMapper;
	@Autowired
	UserIdListMapper useridlistMapper;
	@Autowired
	DeviceStateMapper devicestateMapper;
	@Autowired
    DeviceMapper deviceMapper;
	@Autowired
    FpsMapper fpsMapper;
	@Autowired
	TimeZoneMapper timezoneMapper;
	@Autowired
	UserParseTimeMapper parsetimeMapper;
	@Autowired
	DoorSettingMapper doorsettingMapper;
	
	// 同步更新时间 x1000
	public void updatetime_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeString = sdf.format(time);
			// body
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("time", timeString);
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);

			byte[] bytEmpty = new byte[strtobyte.length];
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("更新时间指令下达");
	}

	// 重启设备，注意byeEmpty长度必须是0-4？成功 X1000
	public void restart_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.setCharacterEncoding("UTF-8");
			response.addHeader("response_code", task.getInstruct().getInstructname());
			response.setContentType("application/octet-stream");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("重启指令下达");
	}

	// 删除人员/X1000
	public void deleteuser_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			jsonObject.put("user_id", usersend.getUserid());
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
			Users users = new Users();
			users.setDevid(task.getDeviceid());
			users.setUserid(usersend.getUserid());
			usersMapper.deleteuserbyuserid(users);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("删除人员指令下达");

	}
	
	
	// 清除管理员/X1000
	public void clearmanager_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", task.getInstruct().getInstructname());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("清除管理员指令下达");

	}
	
	//远程注册  /X1000
	public void enterenroll_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2 = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			jsonObject2.put("user_id", usersend.getUserid());
			jsonObject2.put("backup_number", usersend.getBackupnumber());
			
			jsonObject.put("cmd", "enter_enroll");
			jsonObject.put("param", jsonObject2);
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			//Utils.copydatatofile(strtobyte);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("远程注册指令下达");

	}
	
	//设置门禁时间组  /X1000
	public void settimezone_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			List<TimeZone> timezones = usersend.getListtimezone();
			if(timezones != null && timezones.size()>0) {
				TimeZone timezone = timezones.get(0);
				JSONObject jsont1 = new JSONObject();
				jsont1.put("start", timezone.getT1().split(",")[0]);
				jsont1.put("end", timezone.getT1().split(",")[1]);
				JSONObject jsont2 = new JSONObject();
				jsont2.put("start", timezone.getT2().split(",")[0]);
				jsont2.put("end", timezone.getT2().split(",")[1]);
				JSONObject jsont3 = new JSONObject();
				jsont3.put("start", timezone.getT3().split(",")[0]);
				jsont3.put("end", timezone.getT3().split(",")[1]);
				JSONObject jsont4 = new JSONObject();
				jsont4.put("start", timezone.getT4().split(",")[0]);
				jsont4.put("end", timezone.getT4().split(",")[1]);
				JSONObject jsont5 = new JSONObject();
				jsont5.put("start", timezone.getT5().split(",")[0]);
				jsont5.put("end", timezone.getT5().split(",")[1]);
				JSONObject jsont6 = new JSONObject();
				jsont6.put("start", timezone.getT6().split(",")[0]);
				jsont6.put("end", timezone.getT6().split(",")[1]);
				
				jsonObject.put("TimeZone_No", timezone.getTimezoneno());
				jsonObject.put("T1", jsont1);
				jsonObject.put("T2", jsont2);
				jsonObject.put("T3", jsont3);
				jsonObject.put("T4", jsont4);
				jsonObject.put("T5", jsont5);
				jsonObject.put("T6", jsont6);
				
			}
			String jsonstr = jsonObject.toString();
			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			//Utils.copydatatofile(strtobyte);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("设置时间组指令下达");

	}
	
	// 获取设备时间组 X1000
	public void gettimezone_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			jsonObject.put("TimeZone_No", usersend.getTimezoneno());
			
			String jsonstr = jsonObject.toString();
			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			//Utils.copydatatofile(strtobyte);
			byte[] bytEmpty = new byte[strtobyte.length];
			
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			
			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取时间组指令下达");
	}
	
	
	//设置用户门禁时间段  /X1000
	public void setuserparsetime_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			List<UserParseTime> parsetimes = usersend.getListparsetime();
			if(parsetimes != null && parsetimes.size()>0) {
				UserParseTime parsetime = parsetimes.get(0);
				
				jsonObject.put("user_id", parsetime.getUserid());
				jsonObject.put("Valide_Date_start", parsetime.getValidedatestart());
				jsonObject.put("Valide_Date_end", parsetime.getValidedateend());
				String[] weeknos = parsetime.getWeekno().split(",");
				List<String> listweekno = new ArrayList<String>(weeknos.length);
				for(String no:weeknos) {
					listweekno.add(no);
				}
				jsonObject.put("Week_TimeZone_No", listweekno);
				
			}
			String jsonstr = jsonObject.toString();
			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			//Utils.copydatatofile(strtobyte);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("设置用户门禁时间段指令下达");

	}
	
	// 获取用户门禁时间段 X1000
		public void getuserparsetime_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
			UserSend usersend = new UserSend();
			try {
				// body
				JSONObject jsonObject = new JSONObject();
				List<UserSend> usersends = task.getUsersends();
				usersend = usersends.get(0);
				jsonObject.put("user_id", usersend.getUserid());
				
				String jsonstr = jsonObject.toString();
				byte[] strtobyte = new byte[0];
				strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
				//Utils.copydatatofile(strtobyte);
				byte[] bytEmpty = new byte[strtobyte.length];
				
				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Length", ""+strtobyte.length);
				response.addHeader("trans_id", task.getTaskid() + "");
				response.addHeader("cmd_code", task.getInstruct().getInstructname());
				
				InputStream in = new ByteArrayInputStream(strtobyte);
				int len = 0;
				while ((len = in.read(bytEmpty)) > 0) {
					response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取用户门禁时间段指令下达");
		}
		
		
		//设置门禁参数  /X1000
		public void setdoorsetting_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
			UserSend usersend = new UserSend();
			try {
				// body
				JSONObject jsonObject = new JSONObject();
				List<UserSend> usersends = task.getUsersends();
				usersend = usersends.get(0);
				List<DoorSetting> doorsettings = usersend.getListdoorsetting();
				if(doorsettings != null && doorsettings.size()>0) {
					DoorSetting doorsetting = doorsettings.get(0);
					jsonObject = (JSONObject)JSONObject.toJSON(doorsetting);
				}
				String jsonstr = jsonObject.toString();
				byte[] strtobyte = new byte[0];
				strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
				//Utils.copydatatofile(strtobyte);
				byte[] bytEmpty = new byte[strtobyte.length];

				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Length", ""+strtobyte.length);
				response.addHeader("trans_id", task.getTaskid() + "");
				response.addHeader("cmd_code", task.getInstruct().getInstructname());

				InputStream in = new ByteArrayInputStream(strtobyte);
				int len = 0;
				while ((len = in.read(bytEmpty)) > 0) {
					response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
				}
				in.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("设置门禁参数指令下达");

		}
		
		// 获取门禁参数 X1000
		public void getdoorsetting_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
			UserSend usersend = new UserSend();
			try {
				// body
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("dev_id", task.getDeviceid());
				
				String jsonstr = jsonObject.toString();
				byte[] strtobyte = new byte[0];
				strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
				//Utils.copydatatofile(strtobyte);
				byte[] bytEmpty = new byte[strtobyte.length];
				
				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Length", ""+strtobyte.length);
				response.addHeader("trans_id", task.getTaskid() + "");
				response.addHeader("cmd_code", task.getInstruct().getInstructname());
				
				InputStream in = new ByteArrayInputStream(strtobyte);
				int len = 0;
				while ((len = in.read(bytEmpty)) > 0) {
					response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取门禁参数指令下达");
		}
		

	// 获得人员列表 X1000
	public void getuserlist_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取人员列表指令下达");
	}

	// 获取记录数据 X1000
	public void getloglist_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			Date begintime = usersend.getBegintime();
			Date endtime = usersend.getEndtime();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("begin_time", sdf.format(begintime));
			jsonObject.put("end_time", sdf.format(endtime));

			String jsonstr = jsonObject.toString();
			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("获取记录数据指令下达");
	}

	// 变更考勤机名称 X1000
	public void setfkname_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("fk_name", usersend.getFkname());

			String jsonstr = jsonObject.toString();
			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			byte[] bytEmpty = new byte[strtobyte.length];

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", strtobyte.length+"");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("变更考勤机名称指令下达");
	}

	// 删除所有记录数据 X1000
	public void clearlogdata_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("删除所有记录指令下达");
		// 同时删除数据库,机器反馈后执行
		 devicelogMapper.deletealllog(task.getDeviceid());

	}

	// 删除所有注册数据 X1000
	public void clearenrolldata_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("删除所有注册人员指令下达");
		// 同时删除数据库,应在机器反馈后执行
		 usersMapper.deletealluser(task.getDeviceid());
		 deviceEnrollMapper.deleteallenroll();
	}

	// 获取考勤机状况信息 X1000
	public void getdevicestatus_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取考勤机状况指令下达");
	}
	
	// 获取考勤机信息 X1000
		public void getdeviceinfo_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
			try {
				byte[] bytEmpty = new byte[0];
				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.addHeader("trans_id", task.getTaskid() + "");
				response.addHeader("cmd_code", task.getInstruct().getInstructname());
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取考勤机信息指令下达");
		}

	// 向考勤机插入用户注册 X1000
	public void insrtpeople_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			
			List<Users> userlist = usersend.getListusers();
			Users users = userlist.get(0);

			// 组装body的json部分
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", users.getUserid());
			jsonObject.put("user_name", users.getName());
			jsonObject.put("user_privilege", users.getPrivilege());
			
			String pwd = users.getPwd();
			String card = users.getCard();
			byte[] face = users.getFace();
			List<byte[]> fpslist = new ArrayList<byte[]>();
			if( users.getListfps().size()>0) {
			   byte[] fps = users.getListfps().get(0).getFpsdata();
			   fpslist.add(fps);
			}
			List<DeviceEnrollArray> enrolls = new ArrayList<DeviceEnrollArray>();
			int num =1;
			 if(users.getPhoto() != null) {
	            	jsonObject.put("user_photo", "BIN_"+num++);
	            }
			if(pwd != null && pwd != "") {
				DeviceEnrollArray enrollpwd = new DeviceEnrollArray();
				enrollpwd.setBackupnumber(10);
				enrollpwd.setEnrolldata("BIN_"+num++);
				enrolls.add(enrollpwd);
			}
			if(card != null && card!="") {
				DeviceEnrollArray enrollcard = new DeviceEnrollArray();
				enrollcard.setBackupnumber(11);
				enrollcard.setEnrolldata("BIN_"+num++);
				enrolls.add(enrollcard);
			}
			if(face != null && face.length>0) {
				DeviceEnrollArray enrollface = new DeviceEnrollArray();
				enrollface.setBackupnumber(12);
				enrollface.setEnrolldata("BIN_"+num++);
				enrolls.add(enrollface);
			}
			if(fpslist.size()>0 ) {
				DeviceEnrollArray enrollfps = new DeviceEnrollArray();
				enrollfps.setBackupnumber(0);
				enrollfps.setEnrolldata("BIN_"+num++);
				enrolls.add(enrollfps);
			}
			jsonObject.put("enroll_data_array", JSONArray.parseArray(JSONObject.toJSONString(enrolls)));
           
			String jsonstr = jsonObject.toString();
			jsonstr = jsonstr + "\n";
			byte[] strtobyte = new byte[0];
			// json字符串前面加上长度处理
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			//照片
			if(users.getPhoto() != null ) {
				strtobyte =Utils.AppendBinData(strtobyte, users.getPhoto());
			}
			
			// 加上密码
			if(pwd != null && pwd != "") {
				strtobyte = Utils.AppendBinData(strtobyte, pwd.getBytes());
			}
			//卡号
			if(card !=null && card !="") {
				strtobyte =Utils.AppendBinData(strtobyte, card.getBytes());
			}
			//人脸
			if(face !=null && face.length>0 ) {
				strtobyte =Utils.AppendBinData(strtobyte, face);
			}
			//指纹
			if(fpslist.size()>0 ) {
				strtobyte =Utils.AppendBinData(strtobyte, fpslist.get(0));
			}
			
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);

			byte[] bytEmpty = new byte[strtobyte.length];
			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			users.setDevid(task.getDeviceid());
			usersMapper.updateuser(users);
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("写入用户指令下达");
		
	}

	// 获取考勤机用户信息 X1000
	//@RequestMapping("/clearlogdata_X1000")
	public void getuserinfo_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("user_id", usersend.getUserid());
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			byte[] bytEmpty = new byte[strtobyte.length];

			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取用户信息指令下发");
	}
	
	//获取所有用户信息
	public void getalluserinfo_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取所有用户信息指令已下发");
	}


	// 服务器ip及端口变更 X1000
	public void setwebserverinfo_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("server_ip", usersend.getServerip());
			jsonObject.put("server_port", usersend.getServerport());
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);
			byte[] bytEmpty = new byte[strtobyte.length];

			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			InputStream in = new ByteArrayInputStream(strtobyte);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("变更服务器IP端口指令下发");
	}

	// 更新固件，X1000
	public void updatefirmware_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("firmware_file_name", usersend.getFirmwarefilename());
			jsonObject.put("firmware_bin_data", usersend.getFirmwarebindata());
			String jsonstr = jsonObject.toString();

			byte[] strtobyte = new byte[0];
			strtobyte = Utils.CreateBSCommBufferFromString(strtobyte, jsonstr);

			// 加入固件data
			byte[] data = usersend.getData();
			byte[] by = Utils.AppendBinData(strtobyte, data);

			byte[] bytEmpty = new byte[by.length];
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+strtobyte.length);
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			InputStream in = new ByteArrayInputStream(by);
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("更新固件指令下发");
	}

	// 实时登记传输 X1000
	//@RequestMapping("/dengji_X1000")
	public void dengji_X1000(String deviceid, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			
			// 写入数据库
			Users users = new Users();
			JSONObject json = JSONObject.parseObject(jsonstr);
			users.setDevid(deviceid);
			users.setUserid(json.getString("user_id"));
			users.setName(json.getString("user_name"));
			users.setPrivilege(json.getString("user_privilege"));
			String userphoto = json.getString("user_photo");
		//	users.setData(data);
			//设备有时会重复上传，同设备同id人员则删除覆盖
			Users us = usersMapper.selectbydvidanduid(users);
			if(us != null) {
			usersMapper.deleteuserbyuid(us);
			deviceEnrollMapper.deletebyid(us.getUsid());
			fpsMapper.deletebyusid(us.getUsid());
			}
			usersMapper.insertUser(users);

			byte[] face=new byte[0];
			byte[] card = new byte[0];
			byte[]  pwd = new byte[0];
			byte[] photo = new byte[0];
			byte[] palm = new byte[0];
			
			JSONArray enrolldata = (JSONArray) json.get("enroll_data_array");
			int[] numbers ;
			int[] arraydata;
			int[] arraypx;
			int[] numberpx;
			if(enrolldata!=null) {
			List<DeviceEnrollArray> list = enrolldata.toJavaList(DeviceEnrollArray.class);
			if(list !=null && list.size()>0) {
				if(userphoto!=null) {
					numbers = new int[list.size()+1];
					arraydata = new int[list.size()+1];
					for (int i = 0;i<list.size();i++) {
						DeviceEnrollArray deviceenroll = list.get(i);
						deviceenroll.setUsid(users.getUsid());
						deviceEnrollMapper.insertEnroll(deviceenroll);
						int backupnumber = deviceenroll.getBackupnumber();
						String endata = deviceenroll.getEnrolldata().split("_")[1];
						numbers[i] = backupnumber;
					    arraydata[i] = Integer.parseInt(endata);
					}
					//加入照片
					numbers[list.size()] = -1;
					arraydata[list.size()] = Integer.parseInt(userphoto.split("_")[1]);
				}else {
					numbers = new int[list.size()];
					arraydata = new int[list.size()];
					for (int i = 0;i<list.size();i++) {
						DeviceEnrollArray deviceenroll = list.get(i);
						deviceenroll.setUsid(users.getUsid());
						deviceEnrollMapper.insertEnroll(deviceenroll);
						int backupnumber = deviceenroll.getBackupnumber();
						String endata = deviceenroll.getEnrolldata().split("_")[1];
						numbers[i] = backupnumber;
					    arraydata[i] = Integer.parseInt(endata);
					}
				}
			arraypx =new int[arraydata.length];
			numberpx = new int[arraydata.length];
			System.arraycopy(arraydata, 0, arraypx, 0, arraypx.length);
			arraydata = Utils.arrayint(arraydata);
			for(int k =0;k<arraypx.length;k++) {
				int dex = Utils.getindex(arraypx, arraydata[k]);
				numberpx[k] = numbers[dex];
			}
			
			//若没有enrollarray,照片也可能存在？
			
			//解析data
			if(data != null) {
			int datalength = data.length;
			int datalen = 0;  //每个数据真实长度
			int alllen = 0;    //每个数据长度在data中的下标
			for(int p =0;p<numberpx.length;p++) {
				//每个数据长度的二进制数据4个字节
				byte[] da = new byte[4];
				System.arraycopy(data, 0+alllen, da, 0, da.length);
				datalen =  Utils.byte2int(da);
				byte[] datas = new byte[datalen];
				System.arraycopy(data, da.length+alllen, datas, 0, datas.length);
				alllen += 4+datalen;
				if(numberpx[p]>-1 && numberpx[p]<10) {
					//指纹
					Fps fps = new Fps();
					fps.setFpsdata(datas);
					fps.setUsid(users.getUsid());
					fpsMapper.insertfps(fps);
				}else if(numberpx[p]==10) {
					pwd = datas;
					users.setPwd(new String(pwd,"UTF-8"));
				}else if(numberpx[p]==11) {
					card = datas;
					users.setCard(new String(card,"UTF-8"));
				}else if(numberpx[p]==12) {
					face = datas;
					users.setFace(face);
				}else if(numberpx[p]>12) {
					palm = datas;
					users.setPalm(palm);
				}else if(numberpx[p]==-1) {
					photo = datas;
					users.setPhoto(photo);
				}
			}
			usersMapper.updateuser(users);
			
			}
			}
			}
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("实时登记人员成功");
	}

	// 实时出入传输,数据保存 X1000
	//@RequestMapping("/churu_X1000")
	public void churu_X1000(String deviceid, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			if(requestdata.length>0) {
			// 写入数据库
			DeviceLog devicelog = new DeviceLog();
			JSONObject json = JSONObject.parseObject(jsonstr);
			devicelog.setDevid(deviceid);
			devicelog.setUserid(json.getString("user_id"));
			devicelog.setVerifymode(json.getInteger("verify_mode")+"");
			devicelog.setFkbindatalib(json.getString("fk_bin_data_lib"));
			devicelog.setIomode(json.getString("io_mode"));
			devicelog.setIotime(json.getString("io_time"));
			devicelog.setLogimage(json.getString("iog_image"));
			devicelog.setData(data);
			devicelogMapper.insertlog(devicelog);
			}
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("实时记录成功");
	}

	// 上传注册人员列表/X1000 (GET_USER_ID_LIST)
	//@RequestMapping("/insertuseridlist_X1000")
	public void insertuseridlist_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			JSONObject json = JSONObject.parseObject(jsonstr);
			UserIdList useridlist = new UserIdList();
			useridlist.setUsergetid(userget.getUsergetid());
			useridlist.setUserscount(Integer.parseInt(json.getString("user_id_count")));
			useridlist.setOneuseridsize(json.getString("one_user_id_size"));
			useridlist.setUseridarray(json.getString("user_id_array"));
			useridlist.setData(data);
			useridlistMapper.insertuserids(useridlist);

			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的人员列表已保存");
	}

	// 上传记录数据 X1000
	public void insertlogdata_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			JSONObject json = JSONObject.parseObject(jsonstr);
			DeviceLog devicelog = new DeviceLog();
			devicelog.setUsergetid(userget.getUsergetid());
			devicelog.setLogcount(Integer.parseInt(json.getString("log_count")));
			devicelog.setOnelogsize(json.getString("one_log_size"));
			devicelog.setLogarray(json.getString("log_array"));

			devicelog.setData(data);
			devicelogMapper.insertlog(devicelog);

			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的记录数据已保存");
	}

	// 上传考勤机状况信息 X1000
	//@RequestMapping("/insertdevicestatus_X1000")
	public void insertdevicestatus_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			JSONObject json = JSONObject.parseObject(jsonstr);
			DeviceState devicestate = new DeviceState();
			devicestate.setUsergetid(userget.getUsergetid());
			devicestate.setAlllogcount(json.getInteger("all_log_count"));
			devicestate.setFacecount(json.getInteger("face_count"));
			devicestate.setFacemax(json.getInteger("face_max"));
			devicestate.setFpcount(json.getInteger("fp_count"));
			devicestate.setFpmax(json.getInteger("fp_max"));
			devicestate.setIdcardcount(json.getInteger("idcard_count"));
			devicestate.setIdcardmax(json.getInteger("idcard_max"));
			devicestate.setManagercount(json.getInteger("manager_count"));
			devicestate.setManagermax(json.getInteger("manager_max"));
			devicestate.setPasswordcount(json.getInteger("password_count"));
			devicestate.setPasswordmax(json.getInteger("password_max"));
			devicestate.setPvcount(json.getInteger("pv_count"));
			devicestate.setPvmax(json.getInteger("pv_max"));
			devicestate.setTotallogcount(json.getInteger("total_log_count"));
			devicestate.setTotallogmax(json.getInteger("total_log_max"));
			devicestate.setUsercount(json.getInteger("user_count"));
			devicestate.setUsermax(json.getInteger("user_max"));
			devicestate.setIsonline(2);
			devicestate.setLasttime(new Timestamp(new Date().getTime()));
			devicestateMapper.insertdvstate(devicestate);

			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的考勤机状况信息已保存");
	}

	// 上传用户信息/X1000  与实时上传一样
	public void insertuserinfo_X1000(Task task, byte[] requestdata, HttpServletResponse response) {
		try {
			
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			byte[] data = (byte[]) map.get("data");
			//将data存入本地文件
			//Utils.copydatatofile(data);
			int datalength = data.length;
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);
			
			Users users = new Users();
			JSONObject json = JSONObject.parseObject(jsonstr);
			users.setUsergetid(userget.getUsergetid());
			users.setUserid(json.getString("user_id"));
			users.setName(json.getString("user_name"));
			users.setPrivilege(json.getString("user_privilege"));
			String userphoto = json.getString("user_photo");
		//	users.setData(data);
			//设备有时会重复上传，同设备同id人员则删除覆盖
			Users us = usersMapper.selectbydvidanduid(users);
			if(us != null) {
			usersMapper.deleteuserbyuid(us);
			deviceEnrollMapper.deletebyid(us.getUsid());
			}
			usersMapper.insertUser(users);

			byte[] face=new byte[0];
			byte[] card = new byte[0];
			byte[]  pwd = new byte[0];
			byte[] photo = new byte[0];
			byte[] palm = new byte[0];
			
			JSONArray enrolldata = (JSONArray) json.get("enroll_data_array");
			int[] numbers ;
			int[] arraydata;
			int[] arraypx;
			int[] numberpx;
			List<DeviceEnrollArray> list = enrolldata.toJavaList(DeviceEnrollArray.class);
			if(list !=null && list.size()>0) {
				if(userphoto!=null) {
					numbers = new int[list.size()+1];
					arraydata = new int[list.size()+1];
					for (int i = 0;i<list.size();i++) {
						DeviceEnrollArray deviceenroll = list.get(i);
						deviceenroll.setUsid(users.getUsid());
						deviceEnrollMapper.insertEnroll(deviceenroll);
						int backupnumber = deviceenroll.getBackupnumber();
						String endata = deviceenroll.getEnrolldata().split("_")[1];
						numbers[i] = backupnumber;
					    arraydata[i] = Integer.parseInt(endata);
					}
					//加入照片
					numbers[list.size()] = -1;
					arraydata[list.size()] = Integer.parseInt(userphoto.split("_")[1]);
				}else {
					numbers = new int[list.size()];
					arraydata = new int[list.size()];
					for (int i = 0;i<list.size();i++) {
						DeviceEnrollArray deviceenroll = list.get(i);
						deviceenroll.setUsid(users.getUsid());
						deviceEnrollMapper.insertEnroll(deviceenroll);
						int backupnumber = deviceenroll.getBackupnumber();
						String endata = deviceenroll.getEnrolldata().split("_")[1];
						numbers[i] = backupnumber;
					    arraydata[i] = Integer.parseInt(endata);
					}
				}
			arraypx =new int[arraydata.length];
			numberpx = new int[arraydata.length];
			System.arraycopy(arraydata, 0, arraypx, 0, arraypx.length);
			arraydata = Utils.arrayint(arraydata);
			for(int k =0;k<arraypx.length;k++) {
				int dex = Utils.getindex(arraypx, arraydata[k]);
				numberpx[k] = numbers[dex];
			}
			//若没有enrollarray,照片也可能存在？
			
			//解析data
			int datalen = 0;  //每个数据真实长度
			int alllen = 0;    //每个数据长度在data中的下标
			for(int p =0;p<numberpx.length;p++) {
				//每个数据长度的二进制数据4个字节
				byte[] da = new byte[4];
				System.arraycopy(data, alllen, da, 0, da.length);
				datalen =  Utils.byte2int(da);
				byte[] datas = new byte[datalen];
				System.arraycopy(data, da.length+alllen, datas, 0, datas.length);
				alllen += 4+datalen;
				if(numberpx[p]>-1 && numberpx[p]<10) {
					//指纹
					Fps fps = new Fps();
					fps.setFpsdata(datas);
					fps.setUsid(users.getUsid());
					fpsMapper.insertfps(fps);
				}else if(numberpx[p]==10) {
					pwd = datas;
					users.setPwd(new String(pwd,"UTF-8"));
				}else if(numberpx[p]==11) {
					card = datas;
					users.setCard(new String(card,"UTF-8"));
				}else if(numberpx[p]==12) {
					face = datas;
					users.setFace(face);
				}else if(numberpx[p]>12) {
					palm = datas;
					users.setPalm(palm);
				}else if(numberpx[p]==-1) {
					photo = datas;
					users.setPhoto(photo);
				}
			}
			usersMapper.updateuser(users);
			
			}
			
			byte[] bytEmpty = new byte[0];
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取人员信息已保存");
}
	
	// 上传时间组 X1000
		public void inserttimezone_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
			try {
				// 解析request输入流
				byte[] requestdata = Utils.GetRequestStreamBytes(request);
				Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
				String jsonstr = (String) map.get("jsonstr");
				// 写入数据库
				UserGet userget = new UserGet();
				userget.setTaskid(task.getTaskid());
				usergetMapper.insertuserget(userget);

				if(jsonstr != null) {
				JSONObject json = JSONObject.parseObject(jsonstr);
				TimeZone timezone = new TimeZone();
				timezone.setUsergetid(userget.getUsergetid());
				timezone.setTimezoneno(json.getString("TimeZone_No"));
				timezone.setT1(json.getJSONObject("T1").getString("start")+","+json.getJSONObject("T1").getString("end"));
				timezone.setT2(json.getJSONObject("T2").getString("start")+","+json.getJSONObject("T2").getString("end"));
				timezone.setT3(json.getJSONObject("T3").getString("start")+","+json.getJSONObject("T3").getString("end"));
				timezone.setT4(json.getJSONObject("T4").getString("start")+","+json.getJSONObject("T4").getString("end"));
				timezone.setT5(json.getJSONObject("T5").getString("start")+","+json.getJSONObject("T5").getString("end"));
				timezone.setT6(json.getJSONObject("T6").getString("start")+","+json.getJSONObject("T6").getString("end"));
			    timezoneMapper.inserttimezone(timezone);
				}
				
				byte[] bytEmpty = new byte[0];
				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.addHeader("trans_id", task.getTaskid() + "");
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("获取的时间组数据已保存");
		}
		
		// 上传用户时间段 X1000
				public void insertparsetime_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
					try {
						// 解析request输入流
						byte[] requestdata = Utils.GetRequestStreamBytes(request);
						Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
						String jsonstr = (String) map.get("jsonstr");
						// 写入数据库
						UserGet userget = new UserGet();
						userget.setTaskid(task.getTaskid());
						usergetMapper.insertuserget(userget);

						if(jsonstr != null) {
						JSONObject json = JSONObject.parseObject(jsonstr);
						UserParseTime parsetime = new UserParseTime();
						parsetime.setUsergetid(userget.getUsergetid());
						parsetime.setUserid(json.getString("user_id"));
						parsetime.setValidedatestart(json.getString("Valide_Date_start"));
						parsetime.setValidedateend(json.getString("Valide_Date_end"));
						JSONArray weekno = json.getJSONArray("Week_TimeZone_No");
						List<String> listno = weekno.toJavaList(String.class);
						String wno="";
						if(listno !=null && listno.size()>0){
							for(int i=0;i<listno.size();i++) {
								if(i<listno.size()-1) {
									wno += listno.get(i)+",";
								}else {
									wno += listno.get(i);
								}
							}
						}
						parsetime.setWeekno(wno);
						parsetimeMapper.insertuserparsetime(parsetime);
						}
						
						byte[] bytEmpty = new byte[0];
						response.addHeader("response_code", "OK");
						response.setCharacterEncoding("UTF-8");
						response.addHeader("trans_id", task.getTaskid() + "");
						response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("获取的时间组数据已保存");
				}
				
				
				// 上传门禁参数 X1000
				public void insertdoorsetting_X1000(Task task, HttpServletRequest request, HttpServletResponse response) {
					try {
						// 解析request输入流
						byte[] requestdata = Utils.GetRequestStreamBytes(request);
						Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
						String jsonstr = (String) map.get("jsonstr");
						// 写入数据库
						UserGet userget = new UserGet();
						userget.setTaskid(task.getTaskid());
						usergetMapper.insertuserget(userget);

						if(jsonstr != null) {
						JSONObject json = JSONObject.parseObject(jsonstr);
						DoorSetting doorsetting = new DoorSetting();
						doorsetting = JSONObject.toJavaObject(json, DoorSetting.class);
						doorsetting.setUsergetid(userget.getUsergetid());
						doorsetting.setAnti_back(json.getString("Anti-back"));
						doorsettingMapper.insertdoorsetting(doorsetting);
						}
						
						byte[] bytEmpty = new byte[0];
						response.addHeader("response_code", "OK");
						response.setCharacterEncoding("UTF-8");
						response.addHeader("trans_id", task.getTaskid() + "");
						response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("获取的门禁参数已保存");
				}
	
}