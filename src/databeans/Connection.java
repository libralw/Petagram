package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Connection {
	private int id;
	private String followed;
	private String follower;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFollowed() {
		return followed;
	}

	public void setFollowed(String hero) {
		this.followed = hero;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

}
