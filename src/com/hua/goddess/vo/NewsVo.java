package com.hua.goddess.vo;

public class NewsVo {
	private String newsId;
	private String title;
	private String content;
	private String videoAndriodURL;
	private String videoIphoneURL;
	private NewsImageVo image;
	private String date;
	
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVideoAndriodURL() {
		return videoAndriodURL;
	}
	public void setVideoAndriodURL(String videoAndriodURL) {
		this.videoAndriodURL = videoAndriodURL;
	}
	public String getVideoIphoneURL() {
		return videoIphoneURL;
	}
	public void setVideoIphoneURL(String videoIphoneURL) {
		this.videoIphoneURL = videoIphoneURL;
	}
	public NewsImageVo getImage() {
		return image;
	}
	public void setImage(NewsImageVo image) {
		this.image = image;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
	
}
