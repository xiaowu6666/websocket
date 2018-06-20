<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!-- stomp 需要下面两个包 -->
<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.js"></script>
<script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
<title>stomp调试页面</title>
<style type="text/css">
body {
	font-size: 12px;
}

.content-echo {
	float: left;
	margin-left: 20px;
	padding-left: 20px;
	width: 350px;
	border-left: solid 1px #cccccc;
}

.console-echo {
	border: solid 1px #999999;
	border-top-color: #CCCCCC;
	border-left-color: #CCCCCC;
	padding: 5px;
	width: 100%;
	height: 172px;
	overflow-y: scroll;
}
</style>
</head>
<body>
	<div style="float: left; padding: 20px">
		<strong>endpoint:</strong> <br /> <input type="text" id="serverUrl"
			size="35" value="http://localhost:8080/endpoint" /> <br />
		<button onclick="connect()">Connect</button>
		<button onclick="wsclose()">disConnect</button>
		<br /> <strong>broadcase message:</strong> <br /> 
		<input id="txtMsg" type="text" size="50" /> <br />
		<button onclick="sendEvent()">send</button>
		<br/> <strong>personal message:</strong><br/>
		<input id="personMsg" type="text" size="50"/> 
		</br> <button onclick="sendPersonMsg()">send</button>
	</div>

	<div class="content-echo">
		<strong>broadcase:</strong>
		<div class="console-echo" id="echo-log"></div>
		<button onclick="clearLog()" style="position: relative; top: 3px;">Clear
			log</button>
	</div>

	</div>

	<div class="content-echo">
		<strong>One to One:</strong>
		<div id="sendToUser" class="console-echo"></div>
		<button onclick="clearPer()" style="position: relative; top: 3px;">Clear
			log</button>
	</div>

	</div>
</body>
<script type="text/javascript">
   var broadcastout ;
   var client;
   var output;
   function connect(){ //初始化连接
	   broadcastout = document.getElementById("sendToUser")
	   output = document.getElementById("echo-log")
	   var inputNode = document.getElementById("serverUrl");
	   var stompURL = inputNode.value;
	   var socket = new SockJS(stompURL);
	   client = Stomp.over(socket)
	   client.connect({},function successCallback(frame){
		   console.log("连接成功了。。。。。。。。。。。");
		   writeToScreen("CONNECTED");
	   },function errorCallback(frame){
		   console.log("连接失败------>");
	   })
   }
   
   function sendPersonMsg(){
	  var txt = document.getElementById("personMsg").value;
	  client.send("/app/only",{},txt); //发送信息
	  client.subscribe("/user/topic/myself",function(result){
		  subscribeText('<span style="color: blue;">RESPONSE: ' + result.body+'</span>');
	  })
   }
   
   //send message method
   function sendEvent(){
	   msg = document.getElementById("txtMsg").value;
	   client.send("/app/hello",{},msg);
	   client.subscribe("/queue/echo",function(result){
		   console.log("result",result)
		   writeToScreen('<span style="color: blue;">RESPONSE: ' + result.body+'</span>');
	   })
   }
   
   function  writeToScreen(message){
	   var pre = document.createElement("p");
	     pre.style.wordWrap = "break-word";
	     pre.innerHTML = message;
	     output.appendChild(pre);
   }
   
   function clearLog(){
	   output.innerHTML = "";
   }
   
   function clearPer(){
	   broadcastout.innerHTML= "";
   }
   
   function subscribeText(message)
   {
     var pre = document.createElement("p");
     pre.style.wordWrap = "break-word";
     pre.innerHTML = message;
     broadcastout.appendChild(pre);
   }
   
  

 //  
</script>
</html>