package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	public static String currentTime() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(new Date());
	}

	public static void e(String tag, String msg) {
		System.out.println(Util.getString(currentTime(), " ", tag, "#", msg));
	}

	public static void d(String tag, String msg) {
		System.out.println(Util.getString(currentTime(), " ", tag, "#", msg));
	}

	public static void i(String tag, String msg) {
		System.out.println(Util.getString(currentTime(), " ", tag, "#", msg));
	}

}
