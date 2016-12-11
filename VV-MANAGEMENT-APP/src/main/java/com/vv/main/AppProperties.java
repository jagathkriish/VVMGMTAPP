package com.vv.main;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="conference")
public class AppProperties {
	
	private String name;
	
	private String location;
	
	private String Filelocation = "upload-dir";

	public String getFilelocation() {
		return Filelocation;
	}

	public void setFilelocation(String filelocation) {
		Filelocation = filelocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
 	