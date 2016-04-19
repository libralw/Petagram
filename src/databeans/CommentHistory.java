package databeans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class CommentHistory {
	private int id;
	private int userId;
	private long date;
	private long comments;

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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getComments() {
		return comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}

	public String getDateString() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd");
		return df.format(c.getTime());
	}
}
