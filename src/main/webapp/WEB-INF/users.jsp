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
  setInterval(selectusers,10000);

   function selectusers(){
	   var devid = $(".devid").val();
	   window.location.href= path+"/user/users?devid="+devid;
	   
   }
</script>

<body>
<h1 class="devname">${device.devname}</h1>
<input class="devid" type="hidden" value=${device.devid }>
<table class="table">

  <tr>
    <td>用户id</td>
    <td>用户姓名</td>
    <td>用户权限</td>
    <td><input type="button" value="修改"><input type="button" value="删除"></td>
    <c:forEach items="${listusers }" var="users">
    <tr>
	    <td class="userid">${users.userid}</td>
	    <td class="name">${users.name}</td>
	    <td class="privilege">${users.privilege }</td>
	    <td><input class="update" type="button" value="修改"><input class="delete" type="button" value="删除"></td>
    </tr>
    </c:forEach>
  </tr>
</table>

</body>




</html>