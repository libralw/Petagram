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

import model.InstagramConfig;
import model.Model;
import util.Http;
import util.Util;

public class InstagramLoginAction extends Action {

	public static final String NAME = "instagram-login.do";

	public InstagramLoginAction(Model model) {
		super(model);
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		String instagramAuthUrl = null;
		InstagramConfig config = this.model.instagramConfig;
		instagramAuthUrl = Http.urlString("https://api.instagram.com/oauth/authorize/", "client_id", config.CLIENT_ID,
				"redirect_uri", config.REDIRECT_URI, "response_type", "code");
		return instagramAuthUrl;
	}
}
