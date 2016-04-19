package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CommentDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import databeans.Comment;
import databeans.Photo;
import databeans.User;
import formbeans.CommentForm;

public class CommentAction extends Action {

	public static final String COMMENT_NAME = "comment.do";

	private static final String RESULT_JSP = "template-result.jsp";

	private FormBeanFactory<CommentForm> formBeanFactory = FormBeanFactory.getInstance(CommentForm.class);

	private CommentDAO commentDAO;

	public CommentAction(Model model) {
		super(model);
		commentDAO = model.getCommentDAO();
	}

	public String getName() {
		return COMMENT_NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// user info
			User user = (User) request.getSession(false).getAttribute("user");
			request.setAttribute("user", user);

			// comment form
			CommentForm form = formBeanFactory.create(request);
			// photo info
			Photo photo = model.getPhotoDAO().read(form.getIdValue());
			if (photo == null) {
				errors.add("invalid photo id");
				return RESULT_JSP;
			}
			request.setAttribute("photo", photo);

			if (!form.isPresent()) {
				errors.add("arguments are required");
				return RESULT_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return RESULT_JSP;
			}

			// save to db
			Comment comment = new Comment();
			comment.setUserName(user.getUserName());
			comment.setPhotoId(form.getIdValue());
			comment.setComment(form.getComment());
			comment.setTime(System.currentTimeMillis());
			commentDAO.create(comment);
			model.commentHistoryDAO.inc(photo.getUserId());

			return Util.getString(ViewPhotoAction.NAME, "?id=", photo.getId());
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return RESULT_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
