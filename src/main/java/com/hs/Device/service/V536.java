package com.hs.Device.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hs.Device.mapper.DeleteUserMapper;
import com.hs.Device.mapper.DeviceEnrollArrayMapper;
import com.hs.Device.mapper.DeviceInfoMapper;
import com.hs.Device.mapper.DeviceLogMapper;
import com.hs.Device.mapper.DeviceMapper;
import com.hs.Device.mapper.DeviceSettingMapper;
import com.hs.Device.mapper.FpsMapper;
import com.hs.Device.mapper.PackagesMapper;
import com.hs.Device.mapper.UserGetMapper;
import com.hs.Device.mapper.UserIdListMapper;
import com.hs.Device.mapper.UsersMapper;
import com.hs.Device.model.DeleteUser;
import com.hs.Device.model.Device;
import com.hs.Device.model.DeviceInfo;
import com.hs.Device.model.DeviceLog;
import com.hs.Device.model.DeviceSetting;
import com.hs.Device.model.DoorStatus;
import com.hs.Device.model.Fps;
import com.hs.Device.model.Packages;
import com.hs.Device.model.Task;
import com.hs.Device.model.UserGet;
import com.hs.Device.model.UserIdList;
import com.hs.Device.model.UserSend;
import com.hs.Device.model.Users;
import com.hs.Device.utils.Utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class V536 {

	@Autowired
	UsersMapper usersMapper;
	@Autowired
	DeviceEnrollArrayMapper deviceEnrollMapper;
	@Autowired
	FpsMapper fpsMapper;
	@Autowired
	DeviceLogMapper devicelogMapper;
	@Autowired
	UserGetMapper usergetMapper;
	@Autowired
	UserIdListMapper useridlistMapper;
	@Autowired
	PackagesMapper packagesMapper;
	@Autowired
	DeviceInfoMapper deviceinfoMapper;
	@Autowired
	DeleteUserMapper deleteuserMapper;
	@Autowired
	DeviceSettingMapper devstMapper;
	@Autowired
	DeviceMapper deviceMapper;
	@Autowired
	UserIdListMapper userIdListMapper;
	

	// 同步更新时间 V536
	public void updatetime_V536(Task task, HttpServletRequest request,HttpServletResponse response) {
		try {
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeString = sdf.format(time);
			// body
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("syncTime", timeString);
			String jsonstr = jsonObject.toString();

			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("更新时间指令下达");
	}

	// 重启设备，注意byeEmpty长度必须是0-4？成功 V536
	public void restart_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.addHeader("response_code", task.getInstruct().getInstructname());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("重启指令下达");
	}
	
	// 恢复出厂，注意byeEmpty长度必须是0-4？成功 V536
	public void returnold(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("恢复出厂指令下达");
	}
	
	
	// 设置门控状态
	public void setdoorstatus_v536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			DoorStatus doorstatus = usersend.getListdoorstatus().get(0);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("doorStatus", doorstatus.getDoorstatus());
			String jsonstr = jsonObject.toString();

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("设置门控指令下达");
	}

	// 删除人员/V536
	public void deleteuser_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		UserSend usersend = new UserSend();
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			usersend = usersends.get(0);
			String usersid = usersend.getUsersid();
			String[] usids;
			if(usersid!=null && !usersid.equals("")) {		
				usids = usersid.split(",");
				jsonObject.put("usersId", usids);
				
				//删除数据库
				if(usids.length>0) {
					for(int i=0;i<usids.length;i++) {
						Users u = new Users();
						u.setUserid(usids[i]);
						u.setDevid(task.getDeviceid());
						usersMapper.deleteuserbyuserid(u);
					}
				}
			}else {
				usersMapper.deletealluser(task.getDeviceid());
			}
			jsonObject.put("usersCount", usersend.getUserscount());
			String jsonstr = jsonObject.toString();

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write(jsonstr);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("删除人员指令下达");
		

	}
	
	//清除管理员 V536
		public void clearmanager_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
			try {
				response.addHeader("response_code", "OK");
				response.setCharacterEncoding("UTF-8");
				response.addHeader("trans_id", task.getTaskid() + "");
				response.addHeader("cmd_code", task.getInstruct().getInstructname());
				response.getWriter().write("");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("清除管理员指令下达");
		}

	// 获得人员列表 V536
	public void getuserlist_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取人员列表指令下达");
	}

	// 获取记录数据 V536
	public void getloglist_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			Date begintime = usersend.getBegintime();
			Date endtime = usersend.getEndtime();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("beginTime", sdf.format(begintime));
			jsonObject.put("endTime", sdf.format(endtime));
			String jsonstr = jsonObject.toString();

			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取记录数据指令下达");
	}
	
	// 获取考勤机信息 v536
	public void getdeviceinfo_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
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
	
	//获取考勤机设置参数
	public void getdevicesetting_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
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
		System.out.println("获取考勤机设置参数指令下达");
	}
	
	//设置考勤机参数
	public void setdevicesetting_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			String paramskey = usersend.getParamskey();
			String paramsval = usersend.getParamsval();

			JSONObject jsonObject = new JSONObject();
			jsonObject.put(paramskey, paramsval);
		
			String jsonstr = jsonObject.toString();
			
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("设置参数指令下达");
	}

	// 删除所有记录数据 V536
	public void clearlogdata_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getWriter().write("");

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("删除所有记录指令下达");
		// 同时删除数据库,机器反馈后执行
		 devicelogMapper.deletealllog(task.getDeviceid());

	}

	// 向考勤机插入用户注册 V536
	public void insrtpeople_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			List<Users> listusers = usersend.getListusers();
			List<Users> userslist = new ArrayList<Users>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(Users users: listusers) {
				Timestamp start = users.getVaildstart();
				Timestamp end = users.getVaildstart();
				if(start !=null) {
				users.setStarttime(sdf.format(start.getTime()));
				}
				if(end!=null) {
					users.setEndtime(sdf.format(end.getTime()));
				}
				if(users.getPrivilege().equals("USER")) {
					users.setPri_v536(0);
				}if(users.getPrivilege().equals("MANAGER")) {
					users.setPri_v536(1);
				}
				BASE64Encoder encoder = new BASE64Encoder();
				if(users.getFace()!=null && users.getFace().length>0) {
					String face_base = encoder.encode(users.getFace()).replaceAll("\r\n|\r|\n","");
					users.setFace_base64(face_base);
				}
				if(users.getPhoto()!=null && users.getPhoto().length>0) {
					String photo_base = encoder.encode(users.getPhoto()).replaceAll("\r\n|\r|\n","");
					users.setPhoto_base64(photo_base);
				}
				if(users.getPalm()!= null && users.getPalm().length>0) {
					String palm_base = encoder.encode(users.getPalm()).replaceAll("\r\n|\r|\n","");
					users.setPalm_base64(palm_base);
				}
				if(users.getListfps().size()>0) {
					List<String> fpslist = new ArrayList<String>();
					for(Fps fps:users.getListfps()) {
						fpslist.add(encoder.encode(fps.getFpsdata()).replaceAll("\r\n|\r|\n",""));
					}
					users.setFps(fpslist);
					
				}
				   userslist.add(users);
				   //写入数据库
				   users.setDevid(task.getDeviceid());
				   usersMapper.updateuser(users);
				   
			}
			// 组装body的json部分
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("users", JSONArray.parseArray(JSONObject.toJSONString(userslist)));
			String jsonstr = jsonObject.toJSONString();
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.addHeader("Content-Length", ""+jsonstr.getBytes("UTF-8").length);
			
			
			/*
			 * ByteArrayInputStream t = new ByteArrayInputStream(jsonstr.getBytes());
			 * BufferedReader br = new BufferedReader(new InputStreamReader(t)); PrintWriter
			 * pw = response.getWriter();
			 * 
			 * String line = null; while((line =br.readLine())!=null) { pw.println(line);
			 * pw.flush(); } pw.flush(); pw.close(); br.close();
			 */
			InputStream in = new ByteArrayInputStream(jsonstr.getBytes("utf-8"));
			byte[] bytEmpty = new byte[jsonstr.getBytes("utf-8").length];
			int len = 0;
			while ((len = in.read(bytEmpty)) > 0) {
				response.getOutputStream().write(bytEmpty, 0, bytEmpty.length);
			}
			
			in.close();
			System.out.println(jsonstr.length()+"---"+jsonstr.getBytes("utf-8").length+"---"+jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("写入用户指令下达");
	}

	// 获取考勤机用户信息 V536
	//@RequestMapping("/getuserinfo_V536")
	public void getuserinfo_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			String[] userids = usersend.getUsersid().split(",");
			jsonObject.put("usersId", userids);
			String jsonstr = jsonObject.toString();
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write(jsonstr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取用户信息指令下发");
	}

	// 更新固件，V536
	//@RequestMapping("/updatefirmware_V536")
	public void updatefirmware_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("firmwareFileURL", usersend.getFirmwarefileurl());
			String jsonstr = jsonObject.toString();
			// head
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());

			response.getWriter().write("jsonstr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("更新固件指令下发");
	}

	// 实时登记传输 V536
	//@RequestMapping("/dengji_V536")
	public void dengji_V536(String deviceid, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			if(requestdata!=null || requestdata.length>0) {
			String jsonstr = new String(requestdata,"UTF-8");
			JSONObject json = JSONObject.parseObject(jsonstr);
			// 写入数据库
			Users users = new Users();
			users.setDevid(deviceid);
			users.setUserid(json.getString("userId"));
			users.setName(json.getString("name"));
			Integer privi = json.getInteger("privilege");
			if(privi==0) {
				users.setPrivilege("USER");
			}else if(privi==1) {
				users.setPrivilege("MANAGER");
			}
			users.setCard(json.getString("card"));
			users.setPwd(json.getString("pwd"));
			BASE64Decoder decoder =new BASE64Decoder();
			if(json.getString("face")!=null) {
				users.setFace(decoder.decodeBuffer(json.getString("face")));
			}
			if(json.getString("palm")!=null) {
				users.setPalm(decoder.decodeBuffer(json.getString("palm")));
			}
			if(json.getString("photo")!=null) {
				users.setPhoto(decoder.decodeBuffer(json.getString("photo")));
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if(json.getString("vaildStart")!=null) {
			users.setVaildstart(new Timestamp(sdf.parse(json.getString("vaildStart")).getTime()));
			}
			if(json.getString("vaildEnd")!=null) {
			users.setVaildend(new Timestamp(sdf.parse(json.getString("vaildEnd")).getTime()));
			}
			usersMapper.insertUser(users);

			JSONArray stfps = (JSONArray)json.get("fps");
			if(stfps!=null && stfps.size()>0) {
			List<String> listfps = stfps.toJavaList(String.class);
			for (int i = 0; i < listfps.size(); i++) {
				Fps fps = new Fps();
				fps.setUsid(users.getUsid());
				fps.setFpsdata(decoder.decodeBuffer(listfps.get(i)));
				fpsMapper.insertfps(fps);
			}
			}
			}
			response.addHeader("response_code", "OK");
			response.getWriter().write("");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("实时登记人员成功");
	}
	
	//远程注册
	public void enterenroll_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// body
			JSONObject jsonObject = new JSONObject();
			List<UserSend> usersends = task.getUsersends();
			UserSend usersend = usersends.get(0);
			jsonObject.put("userId", usersend.getUserid());
			jsonObject.put("feature", usersend.getFeature());
			String jsonstr = jsonObject.toString();
			
			response.addHeader("response_code", "OK");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", ""+jsonstr.length());
			response.addHeader("trans_id", task.getTaskid() + "");
			response.addHeader("cmd_code", task.getInstruct().getInstructname());
			response.getWriter().write(jsonstr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("远程注册指令下达");
	}

	// 实时出入传输,数据保存 V536
	public void churu_V536(String deviceid, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			JSONObject json = JSONObject.parseObject(jsonstr);
			// 写入数据库
			if(json !=null) {
			DeviceLog devicelog = new DeviceLog();
			devicelog.setDevid(deviceid);
			devicelog.setUserid(json.getString("userId"));
			devicelog.setIotime(json.getString("date"));
			devicelog.setVerifymode(json.getString("verifyMode"));
			devicelog.setIomode(json.getString("ioMode"));
			devicelog.setInouts(json.getString("inOut"));
			if(json.getString("logPhoto")!=null) {
				devicelog.setLogphoto(json.getString("logPhoto").getBytes());
			}
			devicelogMapper.insertlog(devicelog);
			}
			response.addHeader("response_code", "OK");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("实时记录成功");
	}

	// 上传注册人员列表/V536 (GET_USER_ID_LIST)
	public void insertuseridlist_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			if(jsonstr!=null && !jsonstr.equals("")) {
			JSONObject json = JSONObject.parseObject(jsonstr);

			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);
			
			Packages pk = new Packages();
			pk.setUsergetid(userget.getUsergetid());
			pk.setPackageid(json.getInteger("packageId"));
			pk.setUserscount(json.getInteger("usersCount"));
			packagesMapper.insertpackage(pk);
			
			JSONArray js = (JSONArray)json.get("usersId");
			List<UserIdList> lsuids = js.toJavaList(UserIdList.class);
			for(UserIdList uids: lsuids) {
				uids.setPkid(pk.getPkid());
				userIdListMapper.insertuserids(uids);
			}

			}
			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的人员列表已保存");
	}

	// 上传记录数据 V536
	//@RequestMapping("/insertlogdata_V536")
	public void insertlogdata_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			JSONObject json = JSONObject.parseObject(jsonstr);
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			Packages pkge = new Packages();
			pkge.setUsergetid(userget.getUsergetid());
			pkge.setPackageid(Integer.parseInt(json.getString("packageId")));
			pkge.setAlllogcount(Integer.parseInt(json.getString("allLogCount")));
			pkge.setLogscount(Integer.parseInt(json.getString("logCount")));
			packagesMapper.insertpackage(pkge);

			JSONArray listlogs = (JSONArray) json.get("logs");
			for (int i=0;i<listlogs.size();i++) {
				JSONObject jo = listlogs.getJSONObject(i);
				DeviceLog log = new DeviceLog();
				log.setPkid(pkge.getPkid());
				log.setUserid(jo.getString("userId"));
				log.setIotime(jo.getString("time"));
				log.setVerifymode(jo.getString("verifyMode"));
				log.setIomode(jo.getInteger("ioMode")+"");
				log.setInouts(jo.getString("inOut"));
				log.setDoorMode(jo.getString("doorMode"));
				String base_photo = jo.getString("logPhoto");
				if(base_photo!=null) {
				BASE64Decoder decoder =new BASE64Decoder();
				byte[] logphoto =decoder.decodeBuffer(jo.getString("logPhoto"));
				log.setLogphoto(logphoto);
				}
				devicelogMapper.insertlog(log);
			}

			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的记录数据已保存");
	}

	// 上传设备基本信息 V536
	public void insertdeviceinfo_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			System.out.println(jsonstr);
			JSONObject json = JSONObject.parseObject(jsonstr);
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			DeviceInfo device = new DeviceInfo();
			device.setUsergetid(userget.getUsergetid());
			device.setDevid(request.getHeader("dev_id"));
			device.setDevmodel(request.getHeader("dev_model"));
			device.setToken(request.getHeader("token"));
			device.setDevname(json.getString("name"));
			device.setFirmware(json.getString("firmware"));
			device.setFpver(json.getString("fpVer"));
			device.setFacever(json.getString("faceVer"));
			device.setPvver(json.getString("pvVer"));
			device.setMaxbufferlen(json.getInteger("maxBufferLen"));
			device.setUserlimit(json.getInteger("userLimit"));
			device.setFplimit(json.getInteger("fpLimit"));
			device.setFacelimit(json.getInteger("faceLimit"));
			device.setPwdlimit(json.getInteger("pwdLimit"));
			device.setCardlimit(json.getInteger("cardLimit"));
			device.setLoglimit(json.getInteger("logLimit"));
			device.setUsercount(json.getInteger("userCount"));
			device.setManagercount(json.getInteger("managerCount"));
			device.setFpcount(json.getInteger("fpCount"));
			device.setFacecount(json.getInteger("faceCount"));
			device.setPwdcount(json.getInteger("pwdCount"));
			device.setCardcount(json.getInteger("cardCount"));
			device.setLogcount(json.getInteger("logCount"));
			device.setAllLogCount(json.getInteger("allLogCount"));
			device.setIsonline(2);
			device.setLasttime(new Timestamp(new Date().getTime()));
			deviceinfoMapper.insertdevice(device);
			
			Device dev = new Device();
			dev.setDevid(device.getDevid());
			dev.setDevname(device.getDevname());
			deviceMapper.updatedevice(dev);

			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的考勤机状况信息已保存");
	}
	
	// 上传设备设置参数
	public void insertdevicesetting_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			System.out.println(jsonstr);
			// 写入数据库
			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			DeviceSetting devst = JSONObject.parseObject(jsonstr,DeviceSetting.class);
			devst.setUsergetid(userget.getUsergetid());
			devstMapper.insertdevst(devst);
			
			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的考勤机设置信息已保存");
	}


	// 上传用户信息/V536
	public void insertuserinfo_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			JSONObject json = JSONObject.parseObject(jsonstr);

			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			Packages pk = new Packages();
			pk.setUsergetid(userget.getUsergetid());
			pk.setPackageid(json.getInteger("packageId"));
			pk.setUserscount(json.getInteger("usersCount"));
			packagesMapper.insertpackage(pk);

			JSONArray jsonusers = (JSONArray) json.get("users");
			if(jsonusers!=null && jsonusers.size()>0) {
				for(int i=0;i<jsonusers.size();i++) {
					JSONObject juser = jsonusers.getJSONObject(i);
					System.out.println(juser.toJSONString());
				    Users users = new Users();
					users.setPkid(pk.getPkid());
					users.setUserid(juser.getString("userId"));
					users.setName(juser.getString("name"));
					Integer privi = juser.getInteger("privilege");
					if(privi==0) {
						users.setPrivilege("USER");
					}else if(privi==1) {
						users.setPrivilege("MANAGER");
					}
					users.setCard(juser.getString("card"));
					users.setPwd(juser.getString("pwd"));
					BASE64Decoder decoder =new BASE64Decoder();
					if(juser.getString("face")!=null) {
						users.setFace(decoder.decodeBuffer(juser.getString("face")));
					}
					if(juser.getString("palm")!=null) {
						users.setPalm(decoder.decodeBuffer(juser.getString("palm")));
					}
					if(juser.getString("photo")!=null) {
						users.setPhoto(decoder.decodeBuffer(juser.getString("photo")));
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					if(juser.getString("vaildStart")!=null) {
					users.setVaildstart(new Timestamp(sdf.parse(juser.getString("vaildStart")).getTime()));
					}
					if(juser.getString("vaildEnd")!=null) {
					users.setVaildend(new Timestamp(sdf.parse(juser.getString("vaildEnd")).getTime()));
					}
					usersMapper.insertUser(users);

					JSONArray stfps = (JSONArray)juser.get("fps");
					if(stfps!=null && stfps.size()>0) {
					List<String> listfps = stfps.toJavaList(String.class);
					for (int y = 0; y < listfps.size(); y++) {
						Fps fps = new Fps();
						fps.setUsid(users.getUsid());
						fps.setFpsdata(decoder.decodeBuffer(listfps.get(y)));
						fpsMapper.insertfps(fps);
					}
					}
			}
			}
			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("获取的人员信息已保存");
	}

	// 上传删除失败的人员/V536
	//@RequestMapping("/insertdlerror_V536")
	public void insertdlerror_V536(Task task, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			String jsonstr = new String(requestdata,"UTF-8");
			if(jsonstr!=null && !jsonstr.equals("")) {
			JSONObject json = JSONObject.parseObject(jsonstr);

			UserGet userget = new UserGet();
			userget.setTaskid(task.getTaskid());
			usergetMapper.insertuserget(userget);

			DeleteUser dluser = new DeleteUser();
			dluser.setUsergetid(userget.getUsergetid());
			JSONArray js = (JSONArray)json.get("usersId");
			List<String> ids = js.toJavaList(String.class);
			String userid = "";
			for(int i=0;i<ids.size();i++) {
				if(i<ids.size()-1) {
					userid += ids.get(i)+",";
				}else {
					userid += ids.get(i);
				}
			}
			dluser.setUsersid(userid);
			deleteuserMapper.insertdluser(dluser);
			}
			response.addHeader("response_code", "OK");
			response.addHeader("trans_id", task.getTaskid() + "");
			response.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("删除失败人员id已保存");
	}

}
