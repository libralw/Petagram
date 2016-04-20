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
import formbeans.SearchForm;

public class SearchUserAction extends Action {

	private static final String SEARCH_RESULT_JSP = "search-result.jsp";

	private static final String SEARCH_JSP = "home.do";

	public static final String NAME = "search-user.do";

	private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);

	UserDAO userDao;

	public SearchUserAction(Model model) {
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
			SearchForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			Util.i(form);

			if (!form.isPresent()) {
				return SEARCH_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return SEARCH_JSP;
			}

			User[] users = model.getUserDAO().searchUserName(form.getKeyword());
			request.setAttribute("users", users);
			return SEARCH_RESULT_JSP;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return SEARCH_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return SEARCH_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
