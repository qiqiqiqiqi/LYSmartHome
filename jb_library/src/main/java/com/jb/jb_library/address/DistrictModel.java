package com.jb.jb_library.address;

public class DistrictModel {
	private String name;
        private String districtId;
	
	public DistrictModel() {
		super();
	}

	public DistrictModel(String name, String districtId) {
	    super();
	    this.name = name;
	    this.districtId = districtId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistrictId() {
	    return districtId;
	}

	public void setDistrictId(String districtId) {
	    this.districtId = districtId;
	}

	@Override
	public String toString() {
	    return "DistrictModel [name=" + name + ", districtId=" + districtId
		    + "]";
	}


}
