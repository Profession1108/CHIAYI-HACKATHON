package json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import net.sf.jsi.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class EpItem {
	int id = 0;
	
	//common
	//站名
	String Name = "";
	//測站名稱
	String SiteName = "";
	//縣市
	String County = "";
	
	//AQXSite
	String SiteEngName = "";
	String AreaName = "";
	String Township = "";
	String SiteAddress = "";
	float TWD97Lat = 0.0f;
	float TWD97Lon = 0.0f;
	String SiteType = "";
	
	//AQX
	//空氣汙染指標物
	String MajorPollutant  = "";
	//狀態
	String Status = "";
	String SO2 = "";
	String CO = "";
	String O3 = "";
	//懸浮微粒
	String PM10 = "";
	String PM2d5 = "";
	String NO2 = "";
	//氮氧化物
	String NOx = "";
	String NO = "";
	//風速
	String WindSpeed = "";
	//風向
	String WindDirec = "";
	//資料建置日期
	String PublishTime = "";
	//細懸浮微粒指標 
	String FPMI = "";
	//空氣污染指標
	String PSI = "";
	
	//距離
	float distance = 0.0f;
	
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getMajorPollutant() {
		return MajorPollutant;
	}
	public void setMajorPollutant(String majorPollutant) {
		MajorPollutant = majorPollutant;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSO2() {
		return SO2;
	}
	public void setSO2(String sO2) {
		SO2 = sO2;
	}
	public String getCO() {
		return CO;
	}
	public void setCO(String cO) {
		CO = cO;
	}
	public String getO3() {
		return O3;
	}
	public void setO3(String o3) {
		O3 = o3;
	}
	public String getPM10() {
		return PM10;
	}
	public void setPM10(String pM10) {
		PM10 = pM10;
	}
	public String getPM2d5() {
		return PM2d5;
	}
	public void setPM2d5(String pM2d5) {
		PM2d5 = pM2d5;
	}
	public String getNO2() {
		return NO2;
	}
	public void setNO2(String nO2) {
		NO2 = nO2;
	}
	public String getNOx() {
		return NOx;
	}
	public void setNOx(String nOx) {
		NOx = nOx;
	}
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	public String getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		WindSpeed = windSpeed;
	}
	public String getWindDirec() {
		return WindDirec;
	}
	public void setWindDirec(String windDirec) {
		WindDirec = windDirec;
	}
	public String getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}
	public String getFPMI() {
		return FPMI;
	}
	public void setFPMI(String fPMI) {
		FPMI = fPMI;
	}
	public String getPSI() {
		return PSI;
	}
	public void setPSI(String pSI) {
		PSI = pSI;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteName() {
		return SiteName;
	}
	public void setSiteName(String siteName) {
		SiteName = siteName;
	}
	public String getSiteEngName() {
		return SiteEngName;
	}
	public void setSiteEngName(String siteEngName) {
		SiteEngName = siteEngName;
	}
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public String getCounty() {
		return County;
	}
	public void setCounty(String county) {
		County = county;
	}
	public String getTownship() {
		return Township;
	}
	public void setTownship(String township) {
		Township = township;
	}
	public String getSiteAddress() {
		return SiteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		SiteAddress = siteAddress;
	}
	public float getTWD97Lat() {
		return TWD97Lat;
	}
	public void setTWD97Lat(float tWD97Lat) {
		TWD97Lat = tWD97Lat;
	}
	public float getTWD97Lon() {
		return TWD97Lon;
	}
	public void setTWD97Lon(float tWD97Lon) {
		TWD97Lon = tWD97Lon;
	}
	public String getSiteType() {
		return SiteType;
	}
	public void setSiteType(String siteType) {
		SiteType = siteType;
	}
	
	
}