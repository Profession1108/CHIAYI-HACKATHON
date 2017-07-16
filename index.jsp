<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
	<title>EP</title>
	<link href="css/common.css" rel="stylesheet" media="screen">
	<link href="../js/twbs-bootstrap-9b7d140/dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="../js/twbs-bootstrap-9b7d140/dist/css/bootstrap.css" rel="stylesheet" media="screen">
	
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCfU3lA88R45G_p3OLnzA5xYxos-blGCww&libraries=places&callback=initialize" type="text/javascript"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.1.1.min.js"></script>
	<script src="../js/twbs-bootstrap-9b7d140/dist/js/bootstrap.min.js"></script>
	<script src="../js/twbs-bootstrap-9b7d140/dist/js/bootstrap.js"></script>
	</head>
	
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	
	<script>	
		function handleNoGeolocation(errorFlag) {
		    if (errorFlag == true) {
		      alert("地圖定位失敗");
		    } else {
		      alert("您的瀏覽器不支援定位服務");
		    }
			initialLocation = taipei;
		    map.setCenter(initialLocation);  
		}
	
		function initialize() {		
			var directionsService = new google.maps.DirectionsService;  
			var directionsDisplay = new google.maps.DirectionsRenderer;

	        if(navigator.geolocation) {
	          browserSupportFlag = true;
	          navigator.geolocation.getCurrentPosition(function(position) {          	            
	            alert(position.coords.latitude + " " + position.coords.longitude);
	            
	            $.ajax({
	            	type: "POST",
	            	url: "ep/AQXSite.jsp", 
	            	data: {             	
	             		positionlatitude:  position.coords.latitude,
	             		positionlongitude: position.coords.longitude
	             	},
	            	success: function(response){     
	           		  alert(response);       		  
	           		  var obj = JSON.parse(response);
	           		  
	           		  for(var i=0; i < obj.length ; i++){
	           			 if(i == 0){
	           			 /* var template = 
	           				 "測站名稱: " + obj[i].SiteName
	           				+"</br>空氣汙染指標物: " + obj[i].MajorPollutant
	           				+"</br>狀態: " + obj[i].Status
	           				+"</br>SO2: " + obj[i].SO2
	           				+"</br>CO: " + obj[i].CO
	           				+"</br>O3: " + obj[i].O3
	           				+"</br>PM10: " + obj[i].PM10
	           				+"</br>PM2.5: " + obj[i].PM2d5
	           				+"</br>NO2: " + obj[i].NO2
	           				+"</br>NOx: " + obj[i].NOx
	           				+"</br>NO: " + obj[i].NO
	           				+"</br>細懸浮微粒指標 : " + obj[i].FPMI
	           				+"</br>空氣污染指標: " + obj[i].PSI
	           				+"</br>資料更新時間: " + obj[i].PublishTime; */
	           			 
	           			 var template = "";
	           				
	           			if(obj[i].PSI <= 50){
	           				template += 
	           					"對健康的影響: 良好。"
	           				   +"</br>請民眾戴一般用口罩或活性碳口罩。";   				   
	           			 }else if(obj[i].PSI <= 100 && obj[i].PSI > 50){
	           				template += 
	           					"對健康的影響: 普通。"
	           				   +"</br>請民眾戴醫療用口罩。";   
	           			 }else if(obj[i].PSI <= 199 && obj[i].PSI > 101){
	           				template += 
	           					"對健康的影響: 不良。";
	           				   +"</br>請民眾戴N95口罩或其以上。";   
	           			 }else if(obj[i].PSI <= 299 && obj[i].PSI > 200){
		           				template += 
		           				"對健康的影響: 非常不良。"
		           			   +"</br>請民眾盡量不要出門或待在室內。"; 
		           		 }else if(obj[i].PSI > 300){
	           				template += 
	           					"對健康的影響: 有害。";
	           				   +"</br>請民眾盡量不要出門或待在室內。"; 
	           			 }	 
	           			
	           			 template += "</br>";
	           				
	           			 if(obj[i].FPMI <= 35){
	           				template += 
	           					"一般民眾: 正常戶外活動。"
	           				   +"</br>敏感性族群: 正常戶外活動。";
	           			 }else if(obj[i].FPMI <= 53 && obj[i].FPMI > 35){
	           				template += 
	           					"一般民眾: 正常戶外活動。"
	           				   +"</br>敏感性族群: 有心臟、呼吸道及心血管疾病的成人與孩童感受到癥狀時，應考慮減少體力消耗，特別是減少戶外活動。";
	           			 }else if(obj[i].FPMI <= 70 && obj[i].FPMI > 53){
	           				template += 
	           					"一般民眾: 任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應該考慮減少戶外活動。"
	           				   +"</br>敏感性族群: "
	           				   +"</br>1. 有心臟、呼吸道及心血管疾病的成人與孩童，應減少體力消耗，特別是減少戶外活動。"
	           				   +"</br>2. 老年人應減少體力消耗。" 
	           				   +"</br>3. 具有氣喘的人可能需增加使用吸入劑的頻率。";
	           			 }else if(obj[i].FPMI > 70){
	           				template += 
	           					"一般民眾: 任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應減少體力消耗，特別是減少戶外活動。"
	           				   +"</br>敏感性族群: "
	           				   +"</br>1. 有心臟、呼吸道及心血管疾病的成人與孩童，以及老年人應避免體力消耗，特別是避免戶外活動。"
	           				   +"</br>2. 具有氣喘的人可能需增加使用吸入劑的頻率。";
	           			 }	       
	           			 
	           			template += 
	           				 "</br>測站名稱: " + obj[i].SiteName
	           				+"</br>空氣汙染指標物: " + obj[i].MajorPollutant
	           				+"</br>SO2: " + obj[i].SO2
	           				+"</br>CO: " + obj[i].CO
	           				+"</br>O3: " + obj[i].O3
	           				+"</br>PM10: " + obj[i].PM10
	           				+"</br>PM2.5: " + obj[i].PM2d5
	           				+"</br>NO2: " + obj[i].NO2
	           				+"</br>NOx: " + obj[i].NOx
	           				+"</br>NO: " + obj[i].NO
	           				+"</br>細懸浮微粒指標 : " + obj[i].FPMI
	           				+"</br>空氣污染指標: " + obj[i].PSI
	           				+"</br>資料更新時間: " + obj[i].PublishTime
	           				+"</br>離我最近的可能汙染源: </br>" + obj[i].Name + "距離: " + obj[i].distance;
	           			 }else{
	           				template += 
	           				"</br>" + obj[i].Name + "距離: " + obj[i].distance;
	           			 }	           			  
	           		  }
	           		  
	           		  $("#middle").html(template);
	                },
	                error: function(xhr, ajaxOptions, thrownError)
	                { 
	                  alert(xhr.status); 
	                  alert(thrownError.Error); 
	                }
	            });
	            
	          }, function() {
	            handleNoGeolocation(browserSupportFlag);
	          });
	        }
	        else {
	          browserSupportFlag = false;
	          handleNoGeolocation(browserSupportFlag);
	        }     	       
	    }
	
	    google.maps.event.addDomListener(window, 'load', initialize);
	</script>

<body>
  <div id="outerbackground">
  	<div id="top">
  		<div id="logo">
  		 	<%@include file="include/logo.jsp" %>
  		</div>
  		<div id="title">
  			<div id="content_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;關於YouBike</div>
  			<div id="share">
  				<a target="_blank" href="http://www.facebook.com/share.php?u=http://localhost:8080/Maven/index.jsp">FB分享</a>
			</div>
  		</div>
  	</div>
  	<div id="middle">
  		
  	</div>	
  	<div id="bottom">
  		 <%@include file="include/footer.jsp" %>
  	</div>
  </div>
</body>
</html>		