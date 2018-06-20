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
<title>websocket调试页面</title>
<style type="text/css">
  body {
	font-size: 12px;
}
</style>
</head>
<body>
	<div style="float: left; padding: 20px">
		<strong>location:</strong> <br /> 
		<input type="text" id="serverUrl" size="35" value="" /> <br />
		<button onclick="connect()">Connect</button>
		<button onclick="wsclose()">disConnect</button>
		<br /> <strong>message:</strong> <br /> <input id="txtMsg" type="text" size="50" />
		<br />
		<button onclick="sendEvent()">send</button>
	</div>
 
    <div style="float: left; margin-left: 20px; padding-left: 20px; width: 350px; border-left: solid 1px #cccccc;"> <strong>Log:</strong>
            <div style="border: solid 1px #999999;border-top-color: #CCCCCC;border-left-color: #CCCCCC; padding: 5px;width: 100%;height: 172px;overflow-y: scroll;" id="echo-log"></div>
            <button onclick="clearLog()" style="position: relative; top: 3px;">Clear log</button>
          </div>
      
    </div>
</body>
<!-- 下面是h5原生websocket js写法 -->
<script type="text/javascript">
   var output ;
   var websocket;
   function connect(){ //初始化连接
	   output = document.getElementById("echo-log")
	   var inputNode = document.getElementById("serverUrl");
	   var wsUri = inputNode.value;
	   try{
		   websocket = new WebSocket(wsUri);
	   }catch(ex){
		   alert("对不起websocket连接异常")
	   }
	   
	   connecting();
	   window.addEventListener("load", connecting, false);
   }
   
   
   function connecting()
   {
     websocket.onopen = function(evt) { onOpen(evt) };
     websocket.onclose = function(evt) { onClose(evt) };
     websocket.onmessage = function(evt) { onMessage(evt) };
     websocket.onerror = function(evt) { onError(evt) };
   }
   
   function sendEvent(){
	   var msg = document.getElementById("txtMsg").value
	   doSend(msg);
   }
   
   //连接上事件
   function onOpen(evt)
   {
     writeToScreen("CONNECTED");
     doSend("WebSocket rocks");
   }

   //关闭事件
   function onClose(evt)
   {
     writeToScreen("DISCONNECTED");
   }

   //后端推送事件
   function onMessage(evt)
   {
     writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
   }

   function onError(evt)
   {
     writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
   }

   function doSend(message)
   {
     writeToScreen("SENT: " + message);
     websocket.send(message);
   }

   //清除div的内容
   function clearLog(){
	   output.innerHTML = "";
   }
   
   //浏览器主动断开连接
   function wsclose(){
	   websocket.close();
   }
   
   function writeToScreen(message)
   {
     var pre = document.createElement("p");
     pre.style.wordWrap = "break-word";
     pre.innerHTML = message;
     output.appendChild(pre);
   }

 //  
</script>
</html>