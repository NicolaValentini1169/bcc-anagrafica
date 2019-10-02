package eu.winwinit.bcc.model;

import java.util.HashMap;

public class MarkAsEditedRequest {

	private Integer id;
	private Boolean telefono;
	private Boolean email;
	private Boolean p1;
	private Boolean p2;
	private Boolean p3;
	private Boolean p4;
	private Boolean p5;
	private Boolean p6;
	private Boolean firma;
	
	public HashMap<String, Boolean> fieldsToHashMap(MarkAsEditedRequest markAsEditedRequest){
		HashMap<String, Boolean> valueHashMap = new HashMap<String, Boolean>();
		valueHashMap.put("telefono", markAsEditedRequest.getTelefono());
		valueHashMap.put("email", markAsEditedRequest.getEmail());
		valueHashMap.put("p1", markAsEditedRequest.getP1());
		valueHashMap.put("p2", markAsEditedRequest.getP2());
		valueHashMap.put("p3", markAsEditedRequest.getP3());
		valueHashMap.put("p4", markAsEditedRequest.getP4());
		valueHashMap.put("p5", markAsEditedRequest.getP5());
		valueHashMap.put("p6", markAsEditedRequest.getP6());
		valueHashMap.put("firma", markAsEditedRequest.getFirma());
		return valueHashMap;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getTelefono() {
		return telefono;
	}
	
	public void setTelefono(Boolean telefono) {
		this.telefono = telefono;
	}
	
	public Boolean getEmail() {
		return email;
	}
	
	public void setEmail(Boolean email) {
		this.email = email;
	}
	
	public Boolean getP1() {
		return p1;
	}
	
	public void setP1(Boolean p1) {
		this.p1 = p1;
	}
	
	public Boolean getP2() {
		return p2;
	}
	
	public void setP2(Boolean p2) {
		this.p2 = p2;
	}
	
	public Boolean getP3() {
		return p3;
	}
	
	public void setP3(Boolean p3) {
		this.p3 = p3;
	}
	
	public Boolean getP4() {
		return p4;
	}
	
	public void setP4(Boolean p4) {
		this.p4 = p4;
	}
	
	public Boolean getP5() {
		return p5;
	}
	
	public void setP5(Boolean p5) {
		this.p5 = p5;
	}
	
	public Boolean getP6() {
		return p6;
	}
	
	public void setP6(Boolean p6) {
		this.p6 = p6;
	}
	
	public Boolean getFirma() {
		return firma;
	}
	
	public void setFirma(Boolean firma) {
		this.firma = firma;
	}	
	
}
