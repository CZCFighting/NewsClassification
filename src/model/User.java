package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
	//看过 1  收藏4
	//
	private String username;
	private List<String> keywords;
	private List<String> viewList;
	public User(String uername){
		this.setUsername(uername);
		this.setKeywords(new ArrayList<>());
		this.setViewList(new ArrayList<>());
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public List<String> getViewList() {
		return viewList;
	}
	public void setViewList(List<String> viewList) {
		this.viewList = viewList;
	}
}
