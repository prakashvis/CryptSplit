package com.mini.bean;

//POJO conataining elements of a User

public class User {

	int user_id;
	String userName, password, language;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
        
        public String getLanguage(){
            return language;
        }
        
        public void setLanguage(String language){
            this.language = language;
        }
}
