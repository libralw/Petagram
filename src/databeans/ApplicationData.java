package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class ApplicationData {
	private int id;
	private long nextUpdateTime;

	public long getNextUpdateTime() {
		return nextUpdateTime;
	}

	public void setNextUpdateTime(long nextUpdateTime) {
		this.nextUpdateTime = nextUpdateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
