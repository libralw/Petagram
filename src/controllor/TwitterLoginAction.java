package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import util.Util;

public class TwitterLoginAction extends Action {

	private static final String RESULT_JSP = "template-result";

	public static final String NAME = "twitter-login.do";

	public TwitterLoginAction(Model model) {
		super(model);
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey(model.twitterConfig.consumerKey)
				.apiSecret(model.twitterConfig.consumerSecret).callback(model.twitterConfig.callbackUrl).build();

		Util.i("fetching requestToken...");
		Token requestToken = service.getRequestToken();
		Util.i("got requestToken, requestToken = ", requestToken);
		if (requestToken == null) {
			errors.add(Util.getString("request token failed, twitterConfig = ", model.twitterConfig));
			return RESULT_JSP;
		}

		String twitterAuthUrl = service.getAuthorizationUrl(requestToken);
		return twitterAuthUrl;
	}
}
