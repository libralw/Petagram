package databeans;

import org.genericdao.PrimaryKey;

import util.Util;

@PrimaryKey("id")
public class Photo {
	private int id;
	private String url;
	private int userId;
	private String tag;
	private String text;
	private long time;
	private long likes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTime() {
		return time;
	}

	public void setTimeString(String timeString) {
		try {
			this.time = Long.valueOf(timeString);
		} catch (Exception e) {
			Util.e(e);
			this.time = System.currentTimeMillis() / 1000;
		}
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
