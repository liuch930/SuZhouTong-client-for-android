package com.hua.goddess.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeautyMainVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String col;
	private String tag;
	private String tag3;
	private int sort;
	private int totalNum;
	private int startIndex;
	private int returnNumber;
	private ArrayList<Imgs> imgs;

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag3() {
		return tag3;
	}

	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(int returnNumber) {
		this.returnNumber = returnNumber;
	}

	public ArrayList<Imgs> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<Imgs> imgs) {
		this.imgs = imgs;
	}

	public static class Imgs implements Serializable{
		private String id;
		private String desc;
		private List<String> tags;
		private Owner owner;
		private String fromPageTitle;
		private String column;
		private String parentTag;
		private String date;
		private String downloadUrl;
		private String imageUrl;
		private int imageWidth;
		private int imageHeight;
		private String thumbnailUrl;
		private int thumbnailWidth;
		private int thumbnailHeight;
		private int thumbLargeWidth;
		private int thumbLargeHeight;
		private String thumbLargeUrl;
		private int thumbLargeTnWidth;
		private int thumbLargeTnHeight;
		private String thumbLargeTnUrl;
		private String siteName;
		private String siteLogo;
		private String siteUrl;
		private String fromUrl;
		private String isBook;
		private String bookId;
		private String objUrl;
		private String shareUrl;
		private String setId;
		private String albumId;
		private int isAlbum;
		private String albumName;
		private int albumNum;
		private String userId;
		private int isVip;
		private int isDapei;
		private String dressId;
		private String dressBuyLink;
		private int dressPrice;
		private int dressDiscount;
		private String dressExtInfo;
		private String dressTag;
		private int dressNum;
		private String objTag;
		private int dressImgNum;
		private String hostName;
		private String pictureId;
		private String pictureSign;
		private String dataSrc;
		private String contentSign;
		private String albumDi;
		private String canAlbumId;
		private String albumObjNum;
		private String appId;
		private String photoId;
		private int fromName;
		private String fashion;
		private String title;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public List<String> getTags() {
			return tags;
		}

		public void setTags(List<String> tags) {
			this.tags = tags;
		}

		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}

		public String getFromPageTitle() {
			return fromPageTitle;
		}

		public void setFromPageTitle(String fromPageTitle) {
			this.fromPageTitle = fromPageTitle;
		}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getParentTag() {
			return parentTag;
		}

		public void setParentTag(String parentTag) {
			this.parentTag = parentTag;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getDownloadUrl() {
			return downloadUrl;
		}

		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public int getImageWidth() {
			return imageWidth;
		}

		public void setImageWidth(int imageWidth) {
			this.imageWidth = imageWidth;
		}

		public int getImageHeight() {
			return imageHeight;
		}

		public void setImageHeight(int imageHeight) {
			this.imageHeight = imageHeight;
		}

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public int getThumbnailWidth() {
			return thumbnailWidth;
		}

		public void setThumbnailWidth(int thumbnailWidth) {
			this.thumbnailWidth = thumbnailWidth;
		}

		public int getThumbnailHeight() {
			return thumbnailHeight;
		}

		public void setThumbnailHeight(int thumbnailHeight) {
			this.thumbnailHeight = thumbnailHeight;
		}

		public int getThumbLargeWidth() {
			return thumbLargeWidth;
		}

		public void setThumbLargeWidth(int thumbLargeWidth) {
			this.thumbLargeWidth = thumbLargeWidth;
		}

		public int getThumbLargeHeight() {
			return thumbLargeHeight;
		}

		public void setThumbLargeHeight(int thumbLargeHeight) {
			this.thumbLargeHeight = thumbLargeHeight;
		}

		public String getThumbLargeUrl() {
			return thumbLargeUrl;
		}

		public void setThumbLargeUrl(String thumbLargeUrl) {
			this.thumbLargeUrl = thumbLargeUrl;
		}

		public int getThumbLargeTnWidth() {
			return thumbLargeTnWidth;
		}

		public void setThumbLargeTnWidth(int thumbLargeTnWidth) {
			this.thumbLargeTnWidth = thumbLargeTnWidth;
		}

		public int getThumbLargeTnHeight() {
			return thumbLargeTnHeight;
		}

		public void setThumbLargeTnHeight(int thumbLargeTnHeight) {
			this.thumbLargeTnHeight = thumbLargeTnHeight;
		}

		public String getThumbLargeTnUrl() {
			return thumbLargeTnUrl;
		}

		public void setThumbLargeTnUrl(String thumbLargeTnUrl) {
			this.thumbLargeTnUrl = thumbLargeTnUrl;
		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public String getSiteLogo() {
			return siteLogo;
		}

		public void setSiteLogo(String siteLogo) {
			this.siteLogo = siteLogo;
		}

		public String getSiteUrl() {
			return siteUrl;
		}

		public void setSiteUrl(String siteUrl) {
			this.siteUrl = siteUrl;
		}

		public String getFromUrl() {
			return fromUrl;
		}

		public void setFromUrl(String fromUrl) {
			this.fromUrl = fromUrl;
		}

		public String getIsBook() {
			return isBook;
		}

		public void setIsBook(String isBook) {
			this.isBook = isBook;
		}

		public String getBookId() {
			return bookId;
		}

		public void setBookId(String bookId) {
			this.bookId = bookId;
		}

		public String getObjUrl() {
			return objUrl;
		}

		public void setObjUrl(String objUrl) {
			this.objUrl = objUrl;
		}

		public String getShareUrl() {
			return shareUrl;
		}

		public void setShareUrl(String shareUrl) {
			this.shareUrl = shareUrl;
		}

		public String getSetId() {
			return setId;
		}

		public void setSetId(String setId) {
			this.setId = setId;
		}

		public String getAlbumId() {
			return albumId;
		}

		public void setAlbumId(String albumId) {
			this.albumId = albumId;
		}

		public int getIsAlbum() {
			return isAlbum;
		}

		public void setIsAlbum(int isAlbum) {
			this.isAlbum = isAlbum;
		}

		public String getAlbumName() {
			return albumName;
		}

		public void setAlbumName(String albumName) {
			this.albumName = albumName;
		}

		public int getAlbumNum() {
			return albumNum;
		}

		public void setAlbumNum(int albumNum) {
			this.albumNum = albumNum;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public int getIsVip() {
			return isVip;
		}

		public void setIsVip(int isVip) {
			this.isVip = isVip;
		}

		public int getIsDapei() {
			return isDapei;
		}

		public void setIsDapei(int isDapei) {
			this.isDapei = isDapei;
		}

		public String getDressId() {
			return dressId;
		}

		public void setDressId(String dressId) {
			this.dressId = dressId;
		}

		public String getDressBuyLink() {
			return dressBuyLink;
		}

		public void setDressBuyLink(String dressBuyLink) {
			this.dressBuyLink = dressBuyLink;
		}

		public int getDressPrice() {
			return dressPrice;
		}

		public void setDressPrice(int dressPrice) {
			this.dressPrice = dressPrice;
		}

		public int getDressDiscount() {
			return dressDiscount;
		}

		public void setDressDiscount(int dressDiscount) {
			this.dressDiscount = dressDiscount;
		}

		public String getDressExtInfo() {
			return dressExtInfo;
		}

		public void setDressExtInfo(String dressExtInfo) {
			this.dressExtInfo = dressExtInfo;
		}

		public String getDressTag() {
			return dressTag;
		}

		public void setDressTag(String dressTag) {
			this.dressTag = dressTag;
		}

		public int getDressNum() {
			return dressNum;
		}

		public void setDressNum(int dressNum) {
			this.dressNum = dressNum;
		}

		public String getObjTag() {
			return objTag;
		}

		public void setObjTag(String objTag) {
			this.objTag = objTag;
		}

		public int getDressImgNum() {
			return dressImgNum;
		}

		public void setDressImgNum(int dressImgNum) {
			this.dressImgNum = dressImgNum;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public String getPictureId() {
			return pictureId;
		}

		public void setPictureId(String pictureId) {
			this.pictureId = pictureId;
		}

		public String getPictureSign() {
			return pictureSign;
		}

		public void setPictureSign(String pictureSign) {
			this.pictureSign = pictureSign;
		}

		public String getDataSrc() {
			return dataSrc;
		}

		public void setDataSrc(String dataSrc) {
			this.dataSrc = dataSrc;
		}

		public String getContentSign() {
			return contentSign;
		}

		public void setContentSign(String contentSign) {
			this.contentSign = contentSign;
		}

		public String getAlbumDi() {
			return albumDi;
		}

		public void setAlbumDi(String albumDi) {
			this.albumDi = albumDi;
		}

		public String getCanAlbumId() {
			return canAlbumId;
		}

		public void setCanAlbumId(String canAlbumId) {
			this.canAlbumId = canAlbumId;
		}

		public String getAlbumObjNum() {
			return albumObjNum;
		}

		public void setAlbumObjNum(String albumObjNum) {
			this.albumObjNum = albumObjNum;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getPhotoId() {
			return photoId;
		}

		public void setPhotoId(String photoId) {
			this.photoId = photoId;
		}

		public int getFromName() {
			return fromName;
		}

		public void setFromName(int fromName) {
			this.fromName = fromName;
		}

		public String getFashion() {
			return fashion;
		}

		public void setFashion(String fashion) {
			this.fashion = fashion;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static class Owner implements Serializable{
		private String userName;
		private String userId;
		private String userSign;
		private String isSelf;
		private String portrait;
		private String isVip;
		private String isLanv;
		private String isJiaju;
		private String isHunjia;
		private String orgName;
		private String resUrl;
		private String cert;
		private String budgetNum;
		private String lanvName;
		private String contactName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserSign() {
			return userSign;
		}

		public void setUserSign(String userSign) {
			this.userSign = userSign;
		}

		public String getIsSelf() {
			return isSelf;
		}

		public void setIsSelf(String isSelf) {
			this.isSelf = isSelf;
		}

		public String getPortrait() {
			return portrait;
		}

		public void setPortrait(String portrait) {
			this.portrait = portrait;
		}

		public String getIsVip() {
			return isVip;
		}

		public void setIsVip(String isVip) {
			this.isVip = isVip;
		}

		public String getIsLanv() {
			return isLanv;
		}

		public void setIsLanv(String isLanv) {
			this.isLanv = isLanv;
		}

		public String getIsJiaju() {
			return isJiaju;
		}

		public void setIsJiaju(String isJiaju) {
			this.isJiaju = isJiaju;
		}

		public String getIsHunjia() {
			return isHunjia;
		}

		public void setIsHunjia(String isHunjia) {
			this.isHunjia = isHunjia;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getResUrl() {
			return resUrl;
		}

		public void setResUrl(String resUrl) {
			this.resUrl = resUrl;
		}

		public String getCert() {
			return cert;
		}

		public void setCert(String cert) {
			this.cert = cert;
		}

		public String getBudgetNum() {
			return budgetNum;
		}

		public void setBudgetNum(String budgetNum) {
			this.budgetNum = budgetNum;
		}

		public String getLanvName() {
			return lanvName;
		}

		public void setLanvName(String lanvName) {
			this.lanvName = lanvName;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

	}
}