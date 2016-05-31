<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
	String  path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>WEBRTC视频通话</title>
<meta charset="utf-8" />
<meta name="keywords" content="ACGIST的视频应用,webrtc" />
<meta name="description" content="使用webrtc实现的网页视频通话。" />

<!-- 
			注意：1.本功能暂时只支持google chrome浏览器；
				 2.本功能暂时只支持两人视频聊天；
				 3.由于本功能可以让游客使用，使用session的ID区分不同用户，所以请不要使用同一个浏览器两个窗口之间通话；
				 4.由于宽带有限所以对同事在线通话人数有限制，每天晚上0点将会销毁所有通话，所以该时刻可能出现异常情况。
				 5.注意如果2分钟对方未进入聊天室，将会关闭；
				 5.手机可以使用chrome移动版也可以视频聊天；
				 6.如果你不能访问google，也不能使用本功能；
			以上。
			
			参考文章：http://blog.csdn.net/leecho571/article/details/8146525
		 -->

</head>
<body  >
<a href="/webrtc/rtc/req.do"> 开始进行rtc 通信<%=path %>rtc/req.do</a><br>
<a href="/webrtc/wstest.jsp">rtc 测试页面</a>

</body>
</html>
