package thirdPartyAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.TwitterConfig;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import util.Constants;
import util.Http;
import util.Util;

import com.google.gson.Gson;

import databeans.Photo;
import databeans.twitter.SearchResponse;
import databeans.twitter.SearchResponse.Media;
import databeans.twitter.SearchResponse.Status;
import databeans.twitter.VerifyCredentials;

public class Twitter {

	private static String doGet(TwitterConfig twitterConfig, Token accessToken, String url) {
		Util.i(url);
		OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey(twitterConfig.consumerKey)
				.apiSecret(twitterConfig.consumerSecret).callback(twitterConfig.callbackUrl).build();
		OAuthRequest requestOfApi = new OAuthRequest(Verb.GET, url);
		service.signRequest(accessToken, requestOfApi);
		Response response = requestOfApi.send();
		if (response == null) {
			Util.e("response is null");
			return null;
		}
		return response.getBody();
	}

	/**
	 * See <a href=
	 * "https://dev.twitter.com/rest/reference/get/account/verify_credentials" >
	 * verify_credentials</a>
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	public static VerifyCredentials getVerifyCredential(TwitterConfig twitterConfig, Token accessToken) {
		String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json?include_entities=false&skip_status=true";
		String content = doGet(twitterConfig, accessToken, PROTECTED_RESOURCE_URL);
		Util.i(content);
		return new Gson().fromJson(content, VerifyCredentials.class);
	}

	public static List<Photo> getPictureOf(TwitterConfig twitterConfig, Token accessToken, String tag) {
		String hashTag = Util.getString("#", tag);
		String url = Http.urlString("https://api.twitter.com/1.1/search/tweets.json", "q", hashTag, "result_type",
				"mixed", "count", 100);

		String content = doGet(twitterConfig, accessToken, url);
		return parsePhotos(content);
	}

	private static List<Photo> parsePhotos(String content) {
		List<Photo> photos = new ArrayList<Photo>();
		if (content == null) {
			Util.e("content is null");
			return photos;
		}
		Util.i(content);

		SearchResponse searchResponse = new Gson().fromJson(content, SearchResponse.class);
		if (searchResponse == null) {
			Util.e("searchResponse is null");
			return photos;
		}

		Util.i("searchResponse.statuses.size() = ", searchResponse.statuses.size());
		for (SearchResponse.Status status : searchResponse.statuses) {
			Photo photo = parsePhoto(status);
			if (photo != null) {
				photos.add(photo);
			}
		}
		Util.i("photos.size() = ", photos.size());
		return photos;
	}

	private static Photo parsePhoto(Status status) {
		try {
			if (status == null || status.entities == null || status.entities.media == null) {
				return null;
			}
			for (Media media : status.entities.media) {
				if (isImage(media.media_url)) {
					Photo photo = new Photo();
					photo.setUrl(media.media_url);
					photo.setText(status.text);
					photo.setTime(parseTime(status.created_at));
					return photo;
				}
			}
		} catch (Exception e) {
			Util.e(e);
		}
		return null;
	}

	private static long parseTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		try {
			sdf.setLenient(true);
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (Exception e) {
		}
		return System.currentTimeMillis();
	}

	private static boolean isImage(String url) {
		if (Util.isEmpty(url)) {
			return false;
		}
		for (String suffix : Constants.IMAGE_SUFFIXES) {
			if (url.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}
}
