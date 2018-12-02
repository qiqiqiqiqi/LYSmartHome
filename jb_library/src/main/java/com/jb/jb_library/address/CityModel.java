package com.jb.jb_library.address;

import java.util.List;

public class CityModel {
	private String name;
	private String cityId;
	private List<com.jb.jb_library.address.DistrictModel> districtList;

	public CityModel() {
		super();
	}

	public CityModel(String name, List<com.jb.jb_library.address.DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String getCityId() {
	    return cityId;
	}

	public void setCityId(String cityId) {
	    this.cityId = cityId;
	}

	public List<com.jb.jb_library.address.DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<com.jb.jb_library.address.DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
	    return "CityModel [name=" + name + ", cityId=" + cityId
		    + ", districtList=" + districtList + "]";
	}


}
