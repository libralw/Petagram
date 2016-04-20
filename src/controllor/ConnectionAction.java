package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import databeans.Connection;
import databeans.User;
import formbeans.ConnectionForm;

public class ConnectionAction extends Action {

	private static final String RESULT_JSP = "template-result.jsp";

	public static final String NAME = "connection.do";

	private FormBeanFactory<ConnectionForm> formBeanFactory = FormBeanFactory.getInstance(ConnectionForm.class);

	public ConnectionAction(Model model) {
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
			// login user info
			User user = (User) request.getSession().getAttribute("user");
			request.setAttribute("user", user);

			// connection form
			ConnectionForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				errors.add("arguments are required");
				return RESULT_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return RESULT_JSP;
			}

			User targetUser = model.getUserDAO().read(form.getIdValue());
			if (targetUser == null) {
				errors.add("invalid user id");
				return RESULT_JSP;
			}

			Connection connection = new Connection();
			connection.setFollowed(targetUser.getUserName());
			connection.setFollower(user.getUserName());

			if (form.isFollow()) {
				model.connectionDAO.createIfNotExists(connection);
				model.followerHistoryDAO.inc(targetUser.getId());
			} else {
				model.connectionDAO.deleteIfExists(connection);
				model.followerHistoryDAO.dec(targetUser.getId());
			}
			return Util.getString(ViewUserAction.NAME, "?userName=", targetUser.getUserName());
		} catch (RollbackException e) {
			errors.add(e.toString());
			Util.e(e);
			return RESULT_JSP;
		} catch (FormBeanException e1) {
			errors.add(e1.toString());
			Util.e(e1);
			return RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
