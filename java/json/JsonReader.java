package json;

import gnu.trove.procedure.TIntProcedure;

import java.net.*;
import java.io.*;
import java.util.*;

import json.EpItem;
import net.sf.jsi.Point;
import net.sf.jsi.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class JsonReader {	
  String sna = "";
  String sarea = "";     

  public String getSna() {
	return sna;
  }

  public void setSna(String sna) {
	this.sna = sna;
  }

  public String getSarea() {
	return sarea;
  }
  
  public void setSarea(String sarea) {
	this.sarea = sarea;
  }
  
  public static String readFile(String filename) {
	  String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }	       
	        result = sb.toString();	   
	        br.close(); 	    	    
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
  }

  public static String getText(String url) {
	  URL website;
	  
	  try {
		website = new URL(url);
		URLConnection connection = website.openConnection();
	      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

	      StringBuilder response = new StringBuilder();
	      String inputLine;

	      while ((inputLine = in.readLine()) != null) 
	          response.append(inputLine);

	      in.close();
	      
	      return response.toString();
	  } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  
      return "";
  }

  public ArrayList<EpItem> FromUrl(String[] filename,final Point p) throws IOException, JSONException {
	  
	  final ArrayList<EpItem> EPList = new ArrayList<EpItem>();
	  
	  SpatialIndex si = new RTree();
      si.init(null);  
      final Rectangle[] rects = new Rectangle[10000];
      int id = 0;
	  
	  for(int j=0; j<filename.length; j++){
		  String jsonStr = readFile(filename[j]);
	
		  //System.out.println(jsonStr);
		  //資料JSON型態
		  //"{\"status\": \"OK\",\"origin_addresses\": [ \"Vancouver, BC, Canada\", \"Seattle, État de Washington, États-Unis\" ],\"destination_addresses\": [ \"San Francisco, Californie, États-Unis\", \"Victoria, BC, Canada\" ],\"rows\": [ {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 340110,\"text\": \"3 jours 22 heures\"},\"distance\": {\"value\": 1734542,\"text\": \"1 735 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 24487,\"text\": \"6 heures 48 minutes\"},\"distance\": {\"value\": 129324,\"text\": \"129 km\"}} ]}, {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 288834,\"text\": \"3 jours 8 heures\"},\"distance\": {\"value\": 1489604,\"text\": \"1 490 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 14388,\"text\": \"4 heures 0 minutes\"},\"distance\": {\"value\": 135822,\"text\": \"136 km\"}} ]} ]}";
		  
	      try {   	      	  
	    	  JSONArray rootObject = new JSONArray(jsonStr); // Parse the JSON to a JSONObject
	          //JSONObject result = rootObject.getJSONObject("features"); // Get all JSONArray rows     

	          for(int i=0; i < rootObject.length(); i++) {
	        	  EpItem EpItem = new EpItem();
	        	  		         
		          JSONObject geometryrows = rootObject.getJSONObject(i);
		          JSONObject propertiesrows = rootObject.getJSONObject(i);	
		          
		          JSONArray geometryrow = new JSONArray("[" + geometryrows.get("geometry").toString() + "]");
		          JSONArray propertiesrow = new JSONArray("[" + propertiesrows.get("properties").toString() + "]");	
		          
		          for(int k=0; k < geometryrow.length(); k++) {
		        	  JSONObject geometry = geometryrow.getJSONObject(k);
			          JSONObject properties = propertiesrow.getJSONObject(k);	

			          if( properties.has("Name") == true){
			        	  EpItem.setName(properties.getString("Name"));
			        	  //System.out.println("Name" + properties.getString("Name"));
			          }
			          
			          if( properties.has("INCINER_NA") == true){
			        	  EpItem.setName(properties.getString("INCINER_NA"));
			        	  //System.out.println("INCINER_NA" + properties.getString("INCINER_NA"));
			          }
			          
			          if( geometry.has("coordinates") == true){
			        	  String[] strArray = geometry.get("coordinates").toString().substring(1,geometry.get("coordinates").toString().length()-1).split(",");
			        	  
			        	  EpItem.setId(id);
			        	  EpItem.setTWD97Lon(Float.parseFloat(strArray[0]));
			        	  EpItem.setTWD97Lat(Float.parseFloat(strArray[1]));		
			        	  
			        	  Float[] SWNE = ConvertPosition(Float.parseFloat(strArray[1]),Float.parseFloat(strArray[0]),0.05f);
			              rects[id] = new Rectangle(SWNE[0],SWNE[1],SWNE[2],SWNE[3]); //將經緯度放入              
			              si.add(rects[id], id); //放入Rtree中
			              id++;
			          }
			          
			          EPList.add(EpItem);       			          
		          }		          
	          }  
	          
	          final ArrayList<EpItem> NearestEPList = new ArrayList<EpItem>();
              
	          si.nearestN(p, new TIntProcedure() {
	            public boolean execute(int i) {        	 
	              EpItem NearestList = (EpItem) EPList.get(i);  
	              
	              NearestList.setDistance(rects[i].distance(p)*100);
	              
	        	  NearestEPList.add(NearestList);
	              
	              System.out.println("Name " + NearestList.getName());
	              //System.out.println("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p));
	              return true;
	            }
	          }, 5 , Float.MAX_VALUE );
	          
	          return NearestEPList;
	      } catch (JSONException e) {
	          // JSON Parsing error
	          e.printStackTrace();
	      }
	  }
      
      return null;
  }
  
 public EpItem NearOne(final String AQXSite,final String AQX,final Point p) throws IOException, JSONException {
	  
	  String AQXSitejsonStr = readFile(AQXSite);

	  //System.out.println("jsonStr: " + jsonStr);
	  //資料JSON型態
	  //"{\"status\": \"OK\",\"origin_addresses\": [ \"Vancouver, BC, Canada\", \"Seattle, État de Washington, États-Unis\" ],\"destination_addresses\": [ \"San Francisco, Californie, États-Unis\", \"Victoria, BC, Canada\" ],\"rows\": [ {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 340110,\"text\": \"3 jours 22 heures\"},\"distance\": {\"value\": 1734542,\"text\": \"1 735 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 24487,\"text\": \"6 heures 48 minutes\"},\"distance\": {\"value\": 129324,\"text\": \"129 km\"}} ]}, {\"elements\": [ {\"status\": \"OK\",\"duration\": {\"value\": 288834,\"text\": \"3 jours 8 heures\"},\"distance\": {\"value\": 1489604,\"text\": \"1 490 km\"}}, {\"status\": \"OK\",\"duration\": {\"value\": 14388,\"text\": \"4 heures 0 minutes\"},\"distance\": {\"value\": 135822,\"text\": \"136 km\"}} ]} ]}";
	  
      try {   	      	  
    	  JSONArray AQXSiterows = new JSONArray(AQXSitejsonStr); // Parse the JSON to a JSONObject
    	  
          final ArrayList<EpItem> AQXSiteList = new ArrayList<EpItem>();
          
          SpatialIndex si = new RTree();
          si.init(null);          
          final Rectangle[] rects = new Rectangle[AQXSiterows.length()];
          int id = 0;
          
          for(int i=0; i < AQXSiterows.length(); i++) {
        	  EpItem EpItem = new EpItem();
              JSONObject row = AQXSiterows.getJSONObject(i);        
              
              EpItem.setId(id);
              EpItem.setSiteName(row.getString("SiteName"));
              EpItem.setSiteEngName(row.getString("SiteEngName"));
              EpItem.setAreaName(row.getString("AreaName"));
              EpItem.setCounty(row.getString("County"));
              EpItem.setTownship(row.getString("Township"));
              EpItem.setSiteAddress(row.getString("SiteAddress"));
              EpItem.setTWD97Lat(Float.parseFloat(row.getString("TWD97Lat")));
              EpItem.setTWD97Lon(Float.parseFloat(row.getString("TWD97Lon")));
              EpItem.setSiteType(row.getString("SiteType"));
              
              Float[] SWNE = ConvertPosition(Float.parseFloat(row.getString("TWD97Lat")),Float.parseFloat(row.getString("TWD97Lon")),0.05f);
              rects[id] = new Rectangle(SWNE[0],SWNE[1],SWNE[2],SWNE[3]); //將經緯度放入              
              si.add(rects[id], id); //放入Rtree中
              id++;
              
              AQXSiteList.add(EpItem);              
          }             
          
          final ArrayList<EpItem> NearestEPList = new ArrayList<EpItem>();
                   
          si.nearestN(p, new TIntProcedure() {
            public boolean execute(int i) {        	 
              EpItem NearestList = (EpItem) AQXSiteList.get(i);  
              
              String AQXjsonStr = readFile(AQX);
              JSONArray AQXrows;
			  try {
				  AQXrows = new JSONArray(AQXjsonStr);
				  
				  for(int j=0; j < AQXrows.length(); j++) {
	            	  EpItem EpItem = new EpItem();
	                  JSONObject row = AQXrows.getJSONObject(j);  
	                  
	                  if(NearestList.getSiteName().equals(row.getString("SiteName")) && NearestList.getCounty().equals(row.getString("County"))){
	                	  NearestList.setMajorPollutant(row.getString("MajorPollutant"));
	                	  NearestList.setStatus(row.getString("Status"));
	                	  NearestList.setSO2(row.getString("SO2"));
	                	  NearestList.setCO(row.getString("CO"));
	                	  NearestList.setO3(row.getString("O3"));
	                	  NearestList.setPM10(row.getString("PM10"));
	                	  NearestList.setPM2d5(row.getString("PM2.5"));
	                	  NearestList.setNO2(row.getString("NO2"));
	                	  NearestList.setNOx(row.getString("NOx"));
	                	  NearestList.setNO(row.getString("NO"));
	                	  NearestList.setWindSpeed(row.getString("WindSpeed"));
	                	  NearestList.setWindDirec(row.getString("WindDirec"));
	                	  NearestList.setPublishTime(row.getString("PublishTime"));
	                	  NearestList.setFPMI(row.getString("FPMI"));
	                	  NearestList.setPSI(row.getString("PSI")); 
	                	  
	                	  break;
	                  }
	              }  
				
			  } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
                     
        	  NearestEPList.add(NearestList);
              
              //System.out.println("NearestList " + NearestList.getLat());
              //System.out.println("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p));
              return true;
            }
          }, 1 , Float.MAX_VALUE );
          
          return NearestEPList.get(0);
      } catch (JSONException e) {
          // JSON Parsing error
          e.printStackTrace();
      }
      return null;
  }
  
  public Float[] ConvertPosition(float latitude,float longitude,float c){	
  	float lat_diff= c/110.574f;  //利用距離的比例來算出緯度上的比例
  	float lon_distance=(float)(111.320f * Math.cos(latitude*Math.PI/180)); //算出該緯度上的經度長度
  	float lon_diff= c/lon_distance; //利用距離的比例來算出經度上的比例

  	float N = latitude + Math.abs(lat_diff), //右上 緯度
  	S = latitude - Math.abs(lat_diff), //左下 緯度
  	E = longitude+ Math.abs(lon_diff), //右上 經度
  	W = longitude- Math.abs(lon_diff); //左下 經度
  	
  	Float[] SWNE = {S,W,N,E};
  	
  	//System.out.print("S: " + S);
  	//System.out.print(" W: " + W);
  	//System.out.print(" N: " + N);
  	//System.out.println(" E: " + E);
  	
  	return SWNE;
  }

}