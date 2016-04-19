package databeans;

import java.text.SimpleDateFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Comment {
	private int id;
	private String userName;
	private int photoId;
	private String comment;
	private long time;

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int id) {
		this.photoId = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMM/dd/yyyy");
		return sdf.format(time);
	}
}
