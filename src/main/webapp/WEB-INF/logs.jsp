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

<script >
  setInterval(selectlogs,10000);

   function selectlogs(){
	   var devid = $(".devid").val();
	   window.location.href= path+"/user/logs?devid="+devid;
	   
   }
</script>

<body>
<h1 class="devname">${device.devname}</h1>
<input class="devid" type="hidden" value=${device.devid }>
<table class="table">
  <tr>
    <td>用户id</td>
    <td>识别方式</td>
    <td>出入目的</td>
    <td>出入时间</td>
    <c:forEach items="${listlogs }" var="logs">
    <tr>
        <input class="devid" type="hidden" value=${logs.devid }>
	    <td class="logid">${logs.userid}</td>
	    <td >${logs.verifymode}</td>
	    <td >${logs.iomode }</td>
	    <td >${logs.iotime }</td>
    </tr>
    </c:forEach>
  </tr>
</table>

</body>




</html>