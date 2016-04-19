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

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import databeans.User;
import formbeans.RegisterForm;

public class RegisterAction extends Action {

	public static final String REGISTER_NAME = "register.do";

	private static final String REGISTER_JSP = "register.jsp";

	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;

	public RegisterAction(Model model) {
		super(model);
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return REGISTER_NAME;
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

			RegisterForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return REGISTER_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				for (String string : errors) {
					Util.e(string);
				}
				return REGISTER_JSP;
			}

			user = new User();
			user.setUserName(form.getUserName());
			user.setPassword(form.getPassword());
			userDAO.createByPetagram(user);

			user.setId(userDAO.readByUserName(form.getUserName()).getId());
			model.connectionDAO.createDefaultConnection(user.getUserName());

			request.getSession(true).setAttribute("user", user);
			request.setAttribute("message", "create employee account successfully!");
			return HomeAction.NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return REGISTER_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return REGISTER_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
