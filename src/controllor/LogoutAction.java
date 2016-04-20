package controllor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

public class LogoutAction extends Action {

	public static final String NAME = "logout.do";

	public LogoutAction(Model model) {
		super(model);
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("user", null);
		}

		return LoginAction.NAME;
	}
}
