/**
 * 08-600 
 * hw#9
 * Jian Chen 
 * jianc1
 * Dec 06, 2014
 */

package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.genericdao.Transaction;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import thirdPartyAPI.Twitter;
import util.Util;
import worker.DefaultTwitterAccountsUpdateTask;
import databeans.User;
import databeans.twitter.VerifyCredentials;

public class TwitterLoginCallbackAction extends Action {

	private static final String LOGIN_JSP = "template-result.jsp";

	public static final String NAME = "twitter-callback.do";

	public static final String P_CODE = "code";

	public TwitterLoginCallbackAction(Model model) {
		super(model);
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String oauth_token = request.getParameter("oauth_token");
			String oauth_verifier = request.getParameter("oauth_verifier");
			Util.i("oauth_token = ", oauth_token, ", oauth_verifier = ", oauth_verifier);
			if (Util.isEmpty(oauth_verifier)) {
				Util.e("oauth_verifier is null");
				errors.add(Util.getString("authentication failed, response = ", request.getQueryString()));
				return LOGIN_JSP;
			}

			Verifier v = new Verifier(oauth_verifier);

			OAuthService service = new ServiceBuilder().provider(TwitterApi.class)
					.apiKey(model.twitterConfig.consumerKey).apiSecret(model.twitterConfig.consumerSecret)
					.callback(model.twitterConfig.callbackUrl).build();
			Token requestToken = new Token(oauth_token, model.twitterConfig.consumerSecret);
			Util.i("fetching access token...");
			Token accessToken = service.getAccessToken(requestToken, v);
			Util.i("got access token, token = ", accessToken);

			VerifyCredentials verifyCredentials = Twitter.getVerifyCredential(model.twitterConfig, accessToken);
			User user = model.getUserDAO().readByTwitterId(verifyCredentials.id_str);
			if (user == null) {
				model.getUserDAO().createByTwitter(verifyCredentials.id_str, verifyCredentials.screen_name);
				user = model.getUserDAO().readByTwitterId(verifyCredentials.id_str);
				model.connectionDAO.createDefaultConnection(user.getUserName());
			}

			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("user", user);
			httpSession.setAttribute("twitterToken", accessToken);

			updateDefaultAccount(model, accessToken);

			return HomeAction.NAME;
		} catch (Exception e) {
			Util.e(e);
			errors.add(e.toString());
			return LOGIN_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	private void updateDefaultAccount(Model model, Token accessToken) {
		DefaultTwitterAccountsUpdateTask.setValidToken(accessToken);
		if (model.applicationDAO.getNextUpdateTime() == 0) {
			// block when first run this application
			new DefaultTwitterAccountsUpdateTask(model).run();
		} else {
			new Thread(new DefaultTwitterAccountsUpdateTask(model)).start();
		}
	}
}
