package com.mini.bean;

//POJO containing elements of a file

public class FileDetails {

	int file_id, user_id;
	String fileName;
        long file_size;

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
        
        public long getFileSize(){
            return file_size;
        }
        
        public void setFileSize(long file_size){
            this.file_size = file_size;
        }
}
