package databeans.twitter;

import java.util.List;

import com.google.gson.Gson;

public class SearchResponse {

	public List<Status> statuses;

	public static class Status {
		public String created_at;
		public String text;
		public Entities entities;

		@Override
		public String toString() {
			return new Gson().toJson(this);
		}
	}

	public static class Entities {
		public List<Media> media;
	}

	public static class Media {
		public String media_url;
	}

}
