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

public class YouBikeItem {
	int id = 0;
	String sno = null;
	String sna = null;
	String tot = null;
	String sbi = null;
	String sarea = null;
	String mday = null;
	float lat = 0.0f;
	float lng = 0.0f;
	String ar = null;
	String sareaen = null;
	String snaen = null;
	String aren = null;
	String bemp = null;
	String act = null;	
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSna() {
		return sna;
	}
	public void setSna(String sna) {
		this.sna = sna;
	}
	public String getTot() {
		return tot;
	}
	public void setTot(String tot) {
		this.tot = tot;
	}
	public String getSbi() {
		return sbi;
	}
	public void setSbi(String sbi) {
		this.sbi = sbi;
	}
	public String getSarea() {
		return sarea;
	}
	public void setSarea(String sarea) {
		this.sarea = sarea;
	}
	public String getMday() {
		return mday;
	}
	public void setMday(String mday) {
		this.mday = mday;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public String getAr() {
		return ar;
	}
	public void setAr(String ar) {
		this.ar = ar;
	}
	public String getSareaen() {
		return sareaen;
	}
	public void setSareaen(String sareaen) {
		this.sareaen = sareaen;
	}
	public String getSnaen() {
		return snaen;
	}
	public void setSnaen(String snaen) {
		this.snaen = snaen;
	}
	public String getAren() {
		return aren;
	}
	public void setAren(String aren) {
		this.aren = aren;
	}
	public String getBemp() {
		return bemp;
	}
	public void setBemp(String bemp) {
		this.bemp = bemp;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}		
}