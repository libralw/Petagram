package thirdPartyAPI;

import java.util.ArrayList;
import java.util.List;

import model.InstagramConfig;
import util.Http;
import util.Util;

import com.google.gson.Gson;

import databeans.Photo;
import databeans.instagram.TagResponse;
import databeans.instagram.Token;
import databeans.instagram.UserInfoResponse.UserInfo;

public class Instagram {

	/**
	 * See <a href="http://instagram.com/developer/endpoints/users/#get_users">
	 * /users /user-id</a>
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	public static UserInfo getUserInfo(String userId, String token) {
		String url = Util.getString("https://api.instagram.com/v1/users/", userId, "/");
		return Http.contentByGet(UserInfo.class, url, "access_token", token);
	}

	/**
	 * See <a href=
	 * "http://instagram.com/developer/endpoints/tags/#get_tags_media_recent" >
	 * /tags/tag-name/media/recent</a>
	 * 
	 * @param accessToken
	 * @param tag
	 */
	public static List<Photo> getPictureOf(String accessToken, String tag) {
		tag = Http.urlEncode(tag);

		List<Photo> photos = new ArrayList<Photo>();
		String url = Util.getString("https://api.instagram.com/v1/tags/", tag, "/media/recent");
		TagResponse response = Http.contentByGet(TagResponse.class, url, "access_token", accessToken, "count", 100);
		if (response == null || response.data == null) {
			Util.e("exit, response is null");
			return photos;
		}

		Util.i(response);
		for (TagResponse.ImageInfo info : response.data) {
			if (info == null) {
				continue;
			}
			Photo photo = parsePhoto(info);
			if (photo != null) {
				photos.add(photo);
			}
		}
		Util.i("photos.size() = ", photos.size());
		return photos;
	}

	private static Photo parsePhoto(TagResponse.ImageInfo info) {
		try {
			Photo photo = new Photo();
			photo.setUrl(info.images.standard_resolution.url);
			if (info.likes != null) {
				photo.setLikes(info.likes.count);
			}
			if (info.caption != null) {
				photo.setTimeString(info.caption.created_time);
				photo.setText(info.caption.text);
			} else {
				photo.setTime(System.currentTimeMillis() / 1000);
			}
			return photo;
		} catch (Exception e) {
			Util.e(e);
		}
		return null;
	}

	public static Token getToken(InstagramConfig config, String code) {
		String response = Http.contentByPost("https://api.instagram.com/oauth/access_token", "client_id",
				config.CLIENT_ID, "client_secret", config.CLIENT_SECRET, "grant_type", "authorization_code",
				"redirect_uri", config.REDIRECT_URI, "code", code);
		Util.i(response);
		if (Util.isEmpty(response)) {
			Util.e("response of request token faild");
			return null;
		}
		return new Gson().fromJson(response, Token.class);
	}
}
