package databeans.instagram;

public class Token {
	public String access_token;
	public User user;

	public static class User {
		public String id;
		public String username;
		public String full_name;
		public String profile_picture;
	}
}
