package com.hs.Device.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hs.Device.Dllutil.FaceDataConv;
import com.hs.Device.Dllutil.FaceDataConvImpl;
import com.hs.Device.Dllutil.FpDataConv;
import com.hs.Device.Dllutil.FpDataConvImpl;
import com.hs.Device.mapper.DeviceEnrollArrayMapper;
import com.hs.Device.mapper.DeviceInfoMapper;
import com.hs.Device.mapper.DeviceLogMapper;
import com.hs.Device.mapper.DeviceMapper;
import com.hs.Device.mapper.DoorSettingMapper;
import com.hs.Device.mapper.DoorStatusMapper;
import com.hs.Device.mapper.FpsMapper;
import com.hs.Device.mapper.InstructMapper;
import com.hs.Device.mapper.TaskMapper;
import com.hs.Device.mapper.TimeZoneMapper;
import com.hs.Device.mapper.UserParseTimeMapper;
import com.hs.Device.mapper.UserSendMapper;
import com.hs.Device.mapper.UsersMapper;
import com.hs.Device.model.Device;
import com.hs.Device.model.DeviceEnrollArray;
import com.hs.Device.model.DeviceInfo;
import com.hs.Device.model.DeviceLog;
import com.hs.Device.model.DoorSetting;
import com.hs.Device.model.DoorStatus;
import com.hs.Device.model.Fps;
import com.hs.Device.model.Instruct;
import com.hs.Device.model.Task;
import com.hs.Device.model.TimeZone;
import com.hs.Device.model.UserParseTime;
import com.hs.Device.model.UserSend;
import com.hs.Device.model.Users;
import com.hs.Device.utils.Utils;

import sun.misc.BASE64Decoder;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	TaskMapper taskMapper;
	@Autowired
	UserSendMapper usersendMapper;
	@Autowired
	DeviceEnrollArrayMapper deviceEnrollMapper;
	@Autowired
	InstructMapper instructMapper;
    @Autowired
	DeviceMapper deviceMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    FpsMapper fpsMapper;
    @Autowired 
    DeviceLogMapper logMapper;
    @Autowired 
    DeviceInfoMapper dinfoMapper;
    @Autowired
    TimeZoneMapper timezoneMapper;
    @Autowired
    UserParseTimeMapper parsetimeMapper;
    @Autowired
    DoorSettingMapper doorsettingMapper;
    @Autowired
    DoorStatusMapper doorstatusMapper;
    
	// 用户登录，
	@RequestMapping("/login")
	public String login(HttpSession session) {
		//查询所有设备
		List<Device> listdv = deviceMapper.selectAllDevice();
		session.setAttribute("listdv", listdv);
		return "login";
	}
	
	

	//查询设备,修改在线状态
	@RequestMapping("/ajax_selectdevice")
	@ResponseBody
	public Device ajax_selectdevice(String devid) {
		Device device = deviceMapper.selectdevicebyid(devid);
		String devmodel = device.getModel();
		//v536心跳没有信息，加入名称
		if(devmodel !=null) {
			DeviceInfo din = dinfoMapper.selectbydevid(devid);	//此id设备最新插入的机器信息
			if(din != null) {
				device.setDevname(din.getDevname());
			}
		}
		Date lasttime = device.getLasttime();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    Long ltime = Long.parseLong(sdf.format(lasttime));
	    Date nowtime = new Date();
	    Long ntime = Long.parseLong(sdf.format(nowtime));
	    Long len = ntime-ltime;
	    if(len>5) {
	    	device.setIsonline(1);
	    }else {
	    	device.setIsonline(2);
	    }
	    deviceMapper.updatedevice(device);
	    return device;
	   
	}
	

	@RequestMapping("/ajax_userrequest")
	@ResponseBody
	public JSONObject ajax_userrequest(@RequestBody Task task) {
		task.setStarttime(new Timestamp(new Date().getTime()));
		task.setIssend(1);
		JSONObject json = new JSONObject();
		String devmodel = deviceMapper.selectdevicebyid(task.getDeviceid()).getModel();   //当前任务对应的机器的型号
		// 查询数据库是否有当前用户给相同设备发送的相同未完成指令，若有则不在插入重复指令，并返回信息
		List<Task> listTasks = taskMapper.selectTask(task);

		//有相同未完成指令
		if (listTasks.size() != 0) {
			Instruct instruct = instructMapper.selectinstructbyid(listTasks.get(0).getInstructid());
			json.put("message", "您给id为" + listTasks.get(0).getDeviceid() + "的设备发送有未执行的相同指令，请不要重复发送");
			json.put("state", listTasks.get(0).getState());
			json.put("starttime", listTasks.get(0).getStarttime());
			json.put("userid", listTasks.get(0).getUserid());
			json.put("instruct", instruct.getInstructname());
			return json;
		} else {
			// 插入新指令并返回信息
			taskMapper.insertTask(task);
			UserSend userSend = task.getUsersends().get(0);
			if(userSend==null) {
				userSend = new UserSend();
			}
			userSend.setTaskid(task.getTaskid());
			 BASE64Decoder decoder =new BASE64Decoder();
			if(userSend.getData_base64()!=null && userSend.getData_base64()!="") {

				byte[] byted;
				try {
					byted = decoder.decodeBuffer(userSend.getData_base64());
					userSend.setData(byted);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			usersendMapper.insertUsersend(userSend);
			List<DeviceEnrollArray> enrollarrays = userSend.getListenroll();
			if (enrollarrays != null) {
				for (DeviceEnrollArray enrollarray : enrollarrays) {
					enrollarray.setUsersendid(userSend.getUsersendid());
					deviceEnrollMapper.insertsendenroll(enrollarray);
				}
			}
			List<TimeZone> timezonelist = userSend.getListtimezone();
			if(timezonelist !=null) {
				for(TimeZone timezone:timezonelist) {
					timezone.setUsersendid(userSend.getUsersendid());
					timezoneMapper.inserttimezone(timezone);
				}
			}
			List<UserParseTime> userparsetimes = userSend.getListparsetime();
			if(userparsetimes !=null) {
				for(UserParseTime parsetime:userparsetimes) {
					parsetime.setUsersendid(userSend.getUsersendid());
					parsetimeMapper.insertuserparsetime(parsetime);
				}
				
			}
			List<DoorSetting> doorsettings = userSend.getListdoorsetting();
			if(doorsettings !=null) {
				for(DoorSetting doorsetting:doorsettings) {
					doorsetting.setUsersendid(userSend.getUsersendid());
					doorsettingMapper.insertdoorsetting(doorsetting);
				}
				
			}
			List<DoorStatus> doorstatus = userSend.getListdoorstatus();
			if(doorstatus !=null) {
				for(DoorStatus dsta:doorstatus) {
					dsta.setUsersendid(userSend.getUsersendid());
					doorstatusMapper.insertdoorst(dsta);
				}
				
			}
			List<Users> userslist = userSend.getListusers();
			if (userslist != null) {
				for (Users users : userslist) {
					try {
					if(users.getFace_base64()!=null && users.getFace_base64()!="") {
						
							byte[] byted =decoder.decodeBuffer(users.getFace_base64());
							users.setFace(byted);
						
					}
					if(users.getPhoto_base64()!=null && users.getPhoto_base64()!="") {
						
							//照片机器不显示
						    String photobase = users.getPhoto_base64();
							byte[] byted =decoder.decodeBuffer(users.getPhoto_base64());
							users.setPhoto(byted);
						
					}
					if(users.getPalm_base64()!=null && users.getPalm_base64()!="") {
						
							byte[] byted =decoder.decodeBuffer(users.getPalm_base64());
							users.setPalm(byted);
						
					   }
					users.setUsersendid(userSend.getUsersendid());
					usersMapper.insertUser(users);
					List<Fps> listfps = users.getListfps();
					if(listfps != null) {
					  for(Fps fps:listfps) {
						if(fps.getFpsdata_base64()!=null && fps.getFpsdata_base64()!="") {
						   fps.setUsid(users.getUsid());
						   
								byte[] byted =decoder.decodeBuffer(fps.getFpsdata_base64());
								fps.setFpsdata(byted);
						   
						   fpsMapper.insertfps(fps);
						}
						
					}
					}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			Instruct instruct = instructMapper.selectinstructbyid(task.getInstructid());
			json.put("message", "您给id为" + task.getDeviceid() + "的设备发送指令已经记录成功，请稍等");
			json.put("state", task.getState());
			json.put("starttime", task.getStarttime());
			json.put("instruct", instruct.getInstructname());
			return json;
		}
	}
	
	//定时查询反馈任务信息
	@RequestMapping("/ajax_selecttask")
	@ResponseBody
	public JSONObject ajax_selecttask(Task task) {
		task.setStarttime(new Timestamp(new Date().getTime()));
		task.setIssend(1);
		JSONObject json = new JSONObject();
		// 查询当前用户所有已发送指令是否有完成未推送给用户的
		List<Task> listta = taskMapper.selectTaskbyuserid(task.getUserid());
		
		if (listta.size() != 0) {
			json.put("ok", listta);
			// 修改是否发送
			for (Task tas : listta) {
				tas.setIssend(2);
				taskMapper.updateTask(tas);
			}
		} else {
			json.put("ok", "NO");

		}
		return json;
	}
	
	//定时查询所有设备
	@RequestMapping("/selectalldevice")
	@ResponseBody
	public JSONObject selectalldevice() {
		JSONObject json = new JSONObject();
		//查询所有设备
		List<Device> listdv = deviceMapper.selectAllDevice();
		json.put("listdv", listdv);
		return json;
	}
	
	@RequestMapping("/users")
	public String users(String devid,Model model){
		List<Users> userlist = usersMapper.selectallusers(devid);
		model.addAttribute("listusers", userlist);
		Device d = new Device();
		d = deviceMapper.selectdevicebyid(devid);
		model.addAttribute("device",d);
		return "users";
	}
	
	@RequestMapping("/logs")
	public String logs(String devid,Model model){
		List<DeviceLog> logs  = logMapper.loglist(devid);
		model.addAttribute("listlogs",logs);
		Device d = new Device();
		d = deviceMapper.selectdevicebyid(devid);
		model.addAttribute("device",d);
		return "logs";
	}

	
	@RequestMapping("/download")
	public String download(String devid,Model model){
	
		Device d = new Device();
		d = deviceMapper.selectdevicebyid(devid);
		List<Users> listuser = usersMapper.selectallusers(devid);
		model.addAttribute("device",d);
		List<Map> maps = new ArrayList<Map>();
		if(listuser != null && listuser.size()>0) {
		for(Users us:listuser) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("usid", us.getUsid());
			map.put("userid",us.getUserid());
			map.put("username", us.getName());
			byte[] face = us.getFace();
			if(face != null) {
				map.put("face", "1个");
			}else {
				map.put("face", "0");
			}
			List<Fps> fps = us.getListfps();
			if(fps!=null && fps.size()>0) {
				map.put("fps", fps.size()+"个");
			}else {
				map.put("fps", "0");
			}
			maps.add(map);
		}
		}
		model.addAttribute("maps",maps);
		return "download";
	}
	
	//指纹人脸下载
	@RequestMapping("/down")
	 public void down(HttpServletRequest request, HttpServletResponse response,Integer usid,Integer faceorfps){
		
		Users users = usersMapper.selectbyusid(usid);
		Device dev = deviceMapper.selectdevicebyid(users.getDevid());
		String facever = dev.getFacedataver();
		String fpver = dev.getFpdataver();
		//下载人脸
		if(faceorfps == 1) {
			byte[] face = users.getFace();
			ByteArrayInputStream in = new ByteArrayInputStream(face);
			String type = "face"+facever;
			Utils.downfaceorfps(response, in, type);
		}
		//下载指纹,这里只下载用户的第一个指纹
		else {
			byte[] fps = users.getListfps().get(0).getFpsdata();
			ByteArrayInputStream in = new ByteArrayInputStream(fps);
			String type = "fps"+fpver;
			Utils.downfaceorfps(response, in, type);
		}
		
}
	
	//验证指纹
	@RequestMapping("/ajax_checkfps")
	@ResponseBody
	public JSONObject ajax_checkfps(String fps) {
		JSONObject json = new JSONObject();
		FpDataConvImpl fpc = new FpDataConvImpl();
		try {
		//base64转成data
		BASE64Decoder decoder =new BASE64Decoder();
		byte[] byted =decoder.decodeBuffer(fps);
		long a = fpc.GetFpDataValidity(byted);
		if(a!=0) {
			json.put("isok", "no");
			json.put("fpsver", "error");
		}else {
		long[] version = new long[1];
		long[] size = new long[1];
		long b = fpc.GetVersionAndSize(byted, version, size);
		json.put("isok", "yes");
		json.put("fpsver", version[0]);
		}
		}catch(	Exception e) {
			e.printStackTrace();
		}
		return json;
		
	}
	
	//指纹转换
	@RequestMapping("/changefps")
	public void changefps(HttpServletRequest request, HttpServletResponse response,String fpsver,String fpsdata) {
		FpDataConvImpl fpc = new FpDataConvImpl();
		try {
			//base64转成data
			BASE64Decoder decoder =new BASE64Decoder();
			byte[] byted =decoder.decodeBuffer(fpsdata);
			long[] version = new long[1];
			long[] size = new long[1];
			//获取原数据版本与长度
			long aa = fpc.GetVersionAndSize(byted, version, size);
			if(aa==0) {
			//要转换得版本
			long getver=0;
			if(fpsver.equals("70")) {
				getver  = FpDataConv.DATA_VER_70;
			}else if(fpsver.equals("80")) {
				getver  = FpDataConv.DATA_VER_80;
			}else if(fpsver.equals("89")) {
				getver  = FpDataConv.DATA_VER_80;
			}
			//获取要转换的版本长度
			long[] getlong =new long[1];
			long bb = fpc.GetSize(getver, getlong);
			if(bb==0) {
			//转换数据版本
			byte[] getdata = new byte[(int)getlong[0]];
			long cc = fpc.changefp(version[0], byted, getver, getdata);
			if(cc == 0) {
				ByteArrayInputStream in = new ByteArrayInputStream(getdata);
				String type = "fps"+getver;
				Utils.downfaceorfps(response, in, type);
			}
			
			}
			}
			}catch(	Exception e) {
				e.printStackTrace();
			}
	}
	
	
	//人脸转换
		@RequestMapping("/changeface")
		public void changeface(HttpServletRequest request, HttpServletResponse response,String facever,String facedata) {
			FpDataConvImpl fpc = new FpDataConvImpl();
			try {
				//base64转成data
				BASE64Decoder decoder =new BASE64Decoder();
				byte[] byted =decoder.decodeBuffer(facedata);
				long ver = 0;
				if(facever.equals("256")){
					ver = FaceDataConv.face1;
				}else if(facever.equals("528")){
					ver = FaceDataConv.face2;
				}
				FaceDataConvImpl fc = new FaceDataConvImpl();
				long a = fc.changeface(byted, byted.length, ver);
				ByteArrayInputStream in = new ByteArrayInputStream(byted);
				String type = "face"+ver;
				Utils.downfaceorfps(response, in, type);
				}catch(	Exception e) {
					e.printStackTrace();
				}
		}
	
	//验证人脸   第三方接口没提供
	/*
	 * @RequestMapping("/ajax_checkface")
	 * 
	 * @ResponseBody public JSONObject ajax_checkface(String fps) { JSONObject json
	 * = new JSONObject(); json.put("ok", "ok"); json.put("type", "xxxx"); return
	 * json;
	 * 
	 * }
	 */
}
