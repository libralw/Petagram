package util;

public class Constants {
	public static String[] DEFAULT_INSTAGRAM_ACCOUNTS = new String[] { "SuperDoggy" };
	public static String[] DEFAULT_INSTAGRAM_ACCOUNT_TAGS = new String[] { "dog" };

	public static String[] DEFAULT_TWITTER_ACCOUNTS = new String[] { "SoftKitty" };
	public static String[] DEFAULT_TWITTER_ACCOUNT_TAGS = new String[] { "cat" };

	public static long UPDATE_INTERVAL = 1 * 60 * 1000;// in milliseconds
	public static String RESULT_JSP = "template-result.jsp";
	public static int PHOTO_NUMBER_PER_PAGE = 21;

	public static String[] IMAGE_SUFFIXES = new String[] { ".jpg", ".png", ".gif", ".bmp" };

	public static boolean ENABLE_WORKER = true;
}
