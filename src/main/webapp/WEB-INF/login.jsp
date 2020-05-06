<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.Date ,java.text.SimpleDateFormat" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	 var path = "${path}";
</script>
<script src="${path}/js/jquery.js"></script>
<script src="${path}/js/jquery-form.js"></script>
<title>用户登录</title>
</head>

<style>
  td{
    width: 100px;
  }
  

  
ul input {
    background-color:#A9B3AB;
    list-style: none;
    margin:0 18px 0 0;
    float: left;
}
table{
 display:none;
}


</style>

<script src="${path}/js/login.js">
   
</script>

<body>
<h1 class="devname">无设备</h1>

   设备:<select class="device" >
           <option value="-1">---请选择设备---</option>
           <c:forEach items="${listdv}" var="device">
             <option value="${device.devid }">${device.devname }</option>
           </c:forEach>
        </select>  
      <div>  
     <div id="dev" style="background-color: #A9B3AB; width:700px;height: 90px">
      <label>设备id:<span class=devid>无</span></label>
      <label>设备型号:<span class="model">无</span></label>
      <label>最近上线时间:<span class="lasttime">无</span></label>
      <label>在线状态:<span class="isonline">无</span></label>
      <label>机器时间:<span class="fktime">无</span></label>
      <label class="la">facedataver:<span class="facedataver">无</span></label>
      <label class="la">firmware:<span class="firmware">无</span></label>
      <label class="la">firmwarefilename:<span class="firmwarefilename">无</span></label>
      <label class="la">fkbindatalib:<span class="fkbindatalib">无</span></label>
      <label class="la">fpdataver:<span class="fpdataver">无</span></label>
      <label class="la">support:<span class="support">无</span></label>
     </div>
     
    
	     <ul id="uls">
	       <input style="width:100px;height:50px" id="devset" type="button" value="设备管理">
	       <input style="width:100px;height:50px" id="peoset" type="button" value="人员管理">
	       <input style="width:100px;height:50px" id="logset" type="button" value="考勤管理">
	       <input style="width:100px;height:50px" id="enset" type="button" value="实时查看">
	       <input style="width:100px;height:50px" id="dorset" type="button" value="门禁管理">
	       <input style="width:100px;height:50px" id="download" type="button" value="指纹人脸下载">
	     </ul>
     
     
     <div class="na1" style="width:200px;height:150px ">
     <table id="t1" class="table" style="width:200px; margin:0 auto" >
       <tr >
         <td><input type="button" value="同步时间"></td>
         <td><input type="button" value="重启设备"></td>
         <td><input id="changeip" type="button" value="更改服务器ip"></td>
         <td><input id="changedname" type="button" value="更改设备名称"></td>
       </tr>
       <tr>
         <td><input id="getdsta" type="button" value="获取设备状况"></td>
         <td><input id="getdinf" type="button" value="获取设备信息"></td>
         <td><input id="getdpro" type="button" value="获取设备参数"></td>
         <td><input id="setdpro" type="button" value="设置设备参数"></td>
       </tr>
       <tr>
         <td><input type="button" value="更新固件"></td>
         <td><input id ="returnold" type="button" value="恢复出厂设置"></td>
       </tr>
     </table>
      <table id="t2" class="table" style="width:200px; margin:0 auto" >
       <tr >
         <td><input type="button" value="删除人员"></td>
         <td><input id="clusers" type="button" value="清除注册人员"></td>
         <td><input type="button" value="写入人员"></td>
         <td><input id="clmana" type="button" value="清除管理员"></td>
       </tr>
       <tr>
         <td><input type="button" value="获取人员列表"></td>
         <td><input type="button" value="获取人员信息"></td>
         <td><input id="getalluserinfo" type="button" value="获取所有人员信息"></td>
         <td><input id="longrange" type="button" value="远程注册"></td>
       </tr>
     </table>
      <table id="t3" class="table" style="width:200px; margin:0 auto" >
       <tr >
         <td><input type="button" value="清除记录数据"></td>
         <td><input type="button" value="获取记录信息"></td>
       </tr>
     </table>
     <table id="t4" class="table" style="width:200px; margin:0 auto" >
       <tr >
         <td><input type="button" value="实时登记"></td>
         <td><input type="button" value="实时出入"></td>
       </tr>
     </table>
     <table id="t5" class="table" style="width:200px; margin:0 auto" >
       <tr >
         <td><input type="button" value="设置门禁时间组"></td>
         <td><input type="button" value="获取门禁时间组"></td>
         <td><input type="button" value="设置用户门禁时间段"></td>
         <td><input type="button" value="获取用户门禁时间段"></td>
       </tr>
       <tr>
         <td><input type="button" value="设置设备门禁参数"></td>
         <td><input type="button" value="获取设备门禁参数"></td>
         <td><input id="doorsta" type="button" value="设置门控"></td>
       
       </tr>
       
     </table>
     </div>
     <div class="na2">
     <form  class="form1">
      
     </form>
    </div>
  
	<div class="na3">
		<textarea cols=60 rows=15 class="result1"></textarea>
		<textarea cols=60 rows=15 class="result2"></textarea>
	</div>
 </div>   
 
  
</body>

<script type="text/javascript">
      


</script>


</html>