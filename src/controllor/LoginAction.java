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
import model.UserDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import databeans.User;
import formbeans.LoginForm;

public class LoginAction extends Action {

	private static final String LOGIN_JSP = "login.jsp";

	public static final String NAME = "login.do";

	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

	UserDAO userDao;

	public LoginAction(Model model) {
		super(model);
		userDao = model.getUserDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user != null) {
				return HomeAction.NAME;
			}

			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			Util.i(form);

			if (!form.isPresent()) {
				return LOGIN_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return LOGIN_JSP;
			}

			user = userDao.readByUserName(form.getUserName());
			if (user == null) {
				errors.add("User name not found");
				return LOGIN_JSP;
			}

			if (!user.checkPassword(form.getPassword())) {
				errors.add("Incorrect password");
				return LOGIN_JSP;
			}
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			request.setAttribute("message", "login susscess");
			return HomeAction.NAME;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return LOGIN_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return LOGIN_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
