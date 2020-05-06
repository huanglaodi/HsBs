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

</style>

<body>
<h1 class="devname">${device.devname}</h1>
<input class="devid" type="hidden" value=${device.devid }>
<table class="table">

  <tr>
    <td>数据id</td>
    <td>用户id</td>
    <td>用户姓名</td>
    <td>指纹</td>
    <td>人脸</td>
  </tr>
    <c:forEach items="${maps }" var="usermap">
    <tr class="user">
	    <td class="usid">${usermap.usid}</td>
	    <td class="userid">${usermap.userid}</td>
	    <td class="name">${usermap.username}</td>
	    <td class="fps">
		     <c:if test="${usermap.fps=='0' }" >0</c:if>
		     <c:if test="${usermap.fps!='0'}"><span>${usermap.fps }</span><input class="fpsdownload" type="button" value="下载"></c:if>
	    </td>
	    <td class="face">
	         <c:if test="${usermap.face=='0' }" >0</c:if>
		     <c:if test="${usermap.face!='0'}"><span>${usermap.face }</span><input class="facedownload" type="button" value="下载"></c:if>
	    </td>
    </tr>
    </c:forEach>
</table>
<span>人脸转换</span>
<form class="faceform" action="${path}/user/changeface " method="post">
   <input type="hidden" class="facedata" name="facedata">
   <input type="hidden" class="facever" name="facever">
</form>
<div class="fcdiv">
  <input type="hidden" class="face_base">
  <input type="file" class="facefile" >
  <input type="button" class="face256" value="转换为256格式">
  <input type="button" class="face528" value="转换为528格式">
</div>
<span>指纹转换</span>
<div class="fpdiv">
  <input type="hidden" class="fps_base" >
  <input type="file"  class="fpsfile" >
  <input type="button" class="fps70" value="转换为70版">
  <input type="button" class="fps80" value="转换为80版">
  <input type="button" class="fps89" value="转换为89版">
</div>
</body>

<script type="text/javascript">
    // setInterval(selectusers,20000);

   function selectusers(){
	   var devid = $(".devid").val();
	   window.location.href= path+"/user/download?devid="+devid;
	   
   }
   
   $(function(){
	  $(".fpsdownload") .click(function(){
		  var usid = $(this).parents("tr").find("td").eq(0).text();
		  usid = parseInt(usid);
		  var faceorfps = 2;
		  window.location.href= path+"/user/down?usid="+usid+"&&faceorfps="+faceorfps;
		  
	  })
	  
	 
		  $(".facedownload") .click(function(){
			  var usid = $(this).parents(".user").children(".usid").eq(0).text();
			  usid = parseInt(usid);
			  var faceorfps = 1;
			  window.location.href= path+"/user/down?usid="+usid+"&&faceorfps="+faceorfps;
			  
		  })
		  
		  $(".facefile").change(function(){
			  var file = this.files[0];
			  var facebase = $(this).prev();
			  uploadFile(file,facebase);
		  })
		  
		   $(".fpsfile").change(function(){
			  var file = this.files[0];
			  var fpsbase = $(this).prev();
			  uploadFile(file,fpsbase);
		  })
		  
		  $(".face256").click(function(){
			  var face_base = $(".face_base").val();
			  if(face_base=="" || face_base==null){
				  alert("您还未选择人脸文件");
			  }else{
				  var facever = "256";
				  changeface(face_base,facever);
			  }
		  })
		  
		    $(".face528").click(function(){
			  var face_base = $(".face_base").val();
			  if(face_base=="" || face_base==null){
				  alert("您还未选择人脸文件");
			  }else{
				  var facever = "528";
				  changeface(face_base,facever);
			  }
		  })
		  
		    $(".fps70").click(function(){
			  var fps_base = $(".fps_base").val();
			  if(fps_base=="" || fps_base==null){
				  alert("您还未选择指纹文件");
			  }else{
				  //验证指纹
				  var isfps=[1];
				  var fpsver =[1];
				  checkfps(fps_base,isfps,fpsver);
				  if(isfps[0]=="yes"){
					  //转换
					  var ver= fpsver[0];
					  if(ver=="112"){
						  ver = "70版"
					  }else if(ver=="128"){
						  ver = "80版"
					  }else if(ver=="137"){
						  ver = "89版"
					  }
					  alert("指纹文件ok,类型:"+ver)
					  var getver = "70";
					  changefps(fps_base,getver)
				  }else{
					  alert("您选取的非指纹文件！");
				  }
				  
			  }
		  })
		  
		    $(".fps80").click(function(){
		    	  var fps_base = $(".fps_base").val();
				  if(fps_base=="" || fps_base==null){
					  alert("您还未选择指纹文件");
				  }else{
					  //验证指纹
					  var isfps=[1];
					  var fpsver =[1];
					  checkfps(fps_base,isfps,fpsver);
					  if(isfps[0]=="yes"){
						  //转换
						  var ver= fpsver[0];
						  if(ver=="112"){
							  ver = "70版"
						  }else if(ver=="128"){
							  ver = "80版"
						  }else if(ver=="137"){
							  ver = "89版"
						  }
						  alert("指纹文件ok,类型:"+ver)
						  var getver = "80";
						  changefps(fps_base,getver)
					  }else{
						  alert("您选取的非指纹文件！");
					  }
					  
				  }
		  })
		  
		    $(".fps89").click(function(){
		    	  var fps_base = $(".fps_base").val();
				  if(fps_base=="" || fps_base==null){
					  alert("您还未选择指纹文件");
				  }else{
					  //验证指纹
					  var isfps=[1];
					  var fpsver =[1];
					  checkfps(fps_base,isfps,fpsver);
					  if(isfps[0]=="yes"){
						  //转换
						  var ver= fpsver[0];
						  if(ver=="112"){
							  ver = "70版"
						  }else if(ver=="128"){
							  ver = "80版"
						  }else if(ver=="137"){
							  ver = "89版"
						  }
						  alert("指纹文件ok,类型:"+ver)
						  var getver = "89";
						  changefps(fps_base,getver)
					  }else{
						  alert("您选取的非指纹文件！");
					  }
					  
				  }
		  })
	
   })
   
   
function uploadFile(file, doms){
	var read = new FileReader();
	read.readAsDataURL(file);
	read.onload = function(e){
		var base_64 = this.result;
		var data = base_64.split(",");
		doms.val(data[1]);
	}
}
   
   //校验指纹数据是否正确
   function checkfps(data,isfps,fpsver){
		$.ajax({
			url : path + "/user/ajax_checkfps",
			data : {
				"fps" : data
			},
			type : "post",
			dataType : "json",
			async : false,
			success : function(res) {
			  isfps[0] = res["isok"];
			  fpsver[0] = res["fpsver"]
			}
			
		})
	}
   
   //转换指纹 并下载
   function changefps(data,fpsver){
	   window.location.href= path+"/user/changefps?fpsdata="+data+"&&fpsver="+fpsver;
			
	}
   
   //转换人脸
   function changeface(data,facever){
	   //get请求参数过长
	   //window.location.href= path+"/user/changeface?facedata="+data+"&&facever="+facever;
	   $(".facedata").val(data);
	   $(".facever").val(facever);
	   $(".faceform").submit();
	
   }
   
 </script>
</html>