package com.example.astrohackathon.model;

public class NavDrawerItem {
	
	String title;
	int icon;
	String count = "0";
	Boolean isCounterVisible = false;
	
	public NavDrawerItem(){}
	
	public NavDrawerItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
	}
	
	public NavDrawerItem(String title, int icon,
			boolean isCounterVisible, String count)
	{
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = this.isCounterVisible;
		this.count = count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Boolean getCounterVisibility() {
		return isCounterVisible;
	}

	public void setCounterVisibility(Boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}

	
}
