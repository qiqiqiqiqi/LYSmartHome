package com.jb.jb_library.address;

import java.util.List;

public class ProvinceModel {
	private String name;
	private String proviceId;
	private List<CityModel> cityList;

	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name, List<CityModel> cityList) {
		super();
		this.name = name;
		this.cityList = cityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getProviceId() {
	    return proviceId;
	}

	public void setProviceId(String proviceId) {
	    this.proviceId = proviceId;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
	    return "ProvinceModel [name=" + name + ", proviceId=" + proviceId
		    + ", cityList=" + cityList + "]";
	}

}