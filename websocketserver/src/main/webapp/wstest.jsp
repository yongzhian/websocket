<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta charset="UTF-8">
<title>WebSoket Demo</title>
<script type="text/javascript" src="./jquery-1.12.1.js"></script>
<script type="text/javascript">
	 
            //验证浏览器是否支持WebSocket协议
	if (!window.WebSocket) {
		alert("WebSocket not supported by this browser!");
	}

	var ws;
	var heartbeat_timer;
	function keepalive(ws) {
		if (document.getElementById("enableping").checked==true) {
			var json = '{"cmd":"send","msg":"ping"}';
			ws.send(json);
		}
	}
	function display() {
		//var valueLabel = document.getElementById("valueLabel"); 
		//valueLabel.innerHTML = ""; 
		var wsaddID = document.getElementById("wsaddID");
		log("开始建立连接: " + wsaddID.value);
		ws = new WebSocket(wsaddID.value);
		//监听消息
		ws.onmessage = function(event) { 
			log("收到服务器消息 " + CurentTime() + " :" + event.data);
		};
		// 打开WebSocket 
		ws.onclose = function(event) {
			log('连接关闭 ：Socket Closed!');
			clearInterval(heartbeat_timer);//取消第一个
			//WebSocket Status:: Socket Closed
		};
		// 打开WebSocket
		ws.onopen = function(event) {
			log('成功连接. ')
		};
		ws.onerror = function(event) {
			//WebSocket Status:: Error was reported
			log('连接错误 ： Socket Error!');
		};
	}
	var log = function(s) {
		if (document.readyState !== "complete") {
			log.buffer.push(s);
		} else {
			document.getElementById("contentId").innerHTML += (s + "\n");
		}
	}
	function sendMsg() {
		var msg = document.getElementById("messageId");
		document.getElementById("contentId").innerHTML += ("发送消息:" + msg.value + "\n");

		ws.send(msg.value);
		if (msg.value.indexOf("register") != -1) {
			heartbeat_timer = setInterval(function() {
				keepalive(ws)
			}, 5000);
		}
	}
	function disConnWS() {
		if (ws != null) {
			ws.close();
			ws = null;
		} else {
			alert("连接未建立!");
		}
	}
	function clearcontent() {
		document.getElementById("contentId").innerHTML = "";
	}
	function changeImg() {
		var imgurl = document.getElementById("imgurl");
		document.getElementById("myimg").src = imgurl.value;
	} 

	//得到当前时间字符串，格式为：YYYY-MM-DD HH:MM:SS  
	function CurentTime()
	{ 
		var now = new Date();
		   
		var year = now.getFullYear();       //年
		var month = now.getMonth() + 1;     //月
		var day = now.getDate();            //日
		   
		var hh = now.getHours();            //时
		var mm = now.getMinutes();          //分
		var ss=now.getSeconds();			//秒
		   
		var clock = year + "-";
		   
		if(month < 10) clock += "0";       
		clock += month + "-";
		   
		if(day < 10) clock += "0"; 
		clock += day + " ";
		   
		if(hh < 10) clock += "0";
		clock += hh + ":";

		if (mm < 10) clock += '0'; 
		clock += mm+ ":";
			
		if (ss < 10) clock += '0'; 
		clock += ss;

		return(clock); 
	}

	function login(){ 
		alert("Data Loaded");
	   $.post("https://ts243.flwrobot.com/community_v2000/community/login.do", { name: "John", time: "2pm" },
          function(data){
          alert("Data Loaded: " + data);
          });

	}
	       
</script>
</head>
<body style="margin-left: 30; font-size: 20">
	 
	<h3>websocket测试工具</h3>
	状态查看
	<a target="blank" href="https://rtc.flwrobot.com/collider/status">
		https://rtc.flwrobot.com/collider/status</a>  
	<br />     
	<br />ws服务器地址：
	<input name="wsadd" id="wsaddID" size="50" style="font-size: 15"
		value="wss://rtc.flwrobot.com/collider/ws" placeholder="请输入ws服务器地址" />
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button id="conn" style="font-size: 17" onClick="javascript:display()">连接</button>
	&nbsp;&nbsp;
	<button id="disConnWS" style="font-size: 17"
		onClick="javascript:disConnWS()">断开连接</button>
	是否ping
	<input id="enableping" type="checkbox" value="true" checked="checked" />
	<br />
	<br />

	<div id="valueLabel"></div>
	         
	<textarea rows="20" cols="100" id="contentId"></textarea>
	       
	<br />
	<br />
	<button id="clearcontentID" style="font-size: 17"
		onClick="javascript:clearcontent()">清空</button>
	发送内容：        
	<input name="message" id="messageId" size="60" style="font-size: 15"
		placeholder="输入请求数据" />        
	<button id="sendButton" style="font-size: 17"
		onClick="javascript:sendMsg()">发送</button>
	<br />

	<br />

	<br />
	<br /> rtc请求数据
	：{"clientid":"10891284","roomid":"1234567896","cmd":"register"}
	<br /> 云计算中心机房采用地址 ：wss://rtc2.flwrobot.com/collider/ws
	<br />
	<br /> 图片查看 ：
	<img id="myimg"></img>
	<br />
	<br />
	<input name="imgurl" id="imgurl" size="80" style="font-size: 17"
		value="http://192.168.1.243/headicon/20160219/170928424421455844356861.png" />
	<button style="font-size: 20" onClick="javascript:changeImg()">更新图片</button>
	<br />    

<div style="border: 2px red solid;height: 200px" >
js测试区
<input type="button" onclick="login()" value="登陆">
</div>

</div>
<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> 
</body>
</html>
