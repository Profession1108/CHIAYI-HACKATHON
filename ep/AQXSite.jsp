<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>
<%@ page import="net.sf.jsi.examples.*"%>
<%@ page import="json.*"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="net.sf.jsi.Point"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.google.gson.*"%>
<%@ page import="org.apache.commons.lang3.*"%>

<%	
	float positionlatitude = Float.parseFloat(StringUtils.isEmpty(request.getParameter("positionlatitude"))?"0":request.getParameter("positionlatitude"));
	float positionlongitude = Float.parseFloat(StringUtils.isEmpty(request.getParameter("positionlongitude"))?"0":request.getParameter("positionlongitude"));
	
	//System.out.println("positionlatitude: " + positionlatitude);
	//System.out.println("positionlongitude: " + positionlongitude);
	
	final Point p = new Point(positionlatitude, positionlongitude);
	//final String AQX = "http://opendata.epa.gov.tw/ws/Data/AQX/?%24format=json";
	//final String AQXSite = "http://opendata.epa.gov.tw/ws/Data/AQXSite/?%24format=json";
	
	final String AQX = "C:/ketaifeng/EP_project/maven/Maven/src/main/webapp/data/AQX.json";
	final String AQXSite = "C:/ketaifeng/EP_project/maven/Maven/src/main/webapp/data/AQXSite.json";

	final String[] filename = new String[]{"C:/ketaifeng/EP_project/maven/Maven/src/main/webapp/data/1.json","C:/ketaifeng/EP_project/maven/Maven/src/main/webapp/data/2.json"};
	
	JsonReader read = new JsonReader();
/* 	read.setSna(keyword);
	read.setSarea(sarea); */
	EpItem EPItem = read.NearOne(AQXSite,AQX,p);
	ArrayList<EpItem> FactoryList = null;
	ArrayList<EpItem> SUMList = new ArrayList();

	final Point NeraP = new Point(EPItem.getTWD97Lat(), EPItem.getTWD97Lon());
	FactoryList = read.FromUrl(filename,NeraP);

	if(FactoryList.size() > 0){
		for(int i=0; i<FactoryList.size(); i++){
			EpItem sumItem = FactoryList.get(i);
			if(i==0){
				EPItem.setName(sumItem.getName());
				EPItem.setDistance(sumItem.getDistance());
				SUMList.add(EPItem);
			}else{
				SUMList.add(sumItem);				
			}
		}
	}else{
		SUMList.add(EPItem);
	}
	
	//System.out.println("SUMList " + SUMList.size());
	
	String json = new Gson().toJson(SUMList);
	
	response.getWriter().write(json);
	response.getWriter().flush();
	response.getWriter().close();
%>
