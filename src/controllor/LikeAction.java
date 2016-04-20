package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Util;
import databeans.Photo;

public class LikeAction extends Action {

	private static final String LIKE_FAILED_PAGE = "view-photo.jsp";

	public static final String NAME = "like.do";

	public LikeAction(Model model) {
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

			int id = getId(request);
			Photo photo = model.getPhotoDAO().read(id);
			if (photo == null) {
				errors.add("invalid id");
				return LIKE_FAILED_PAGE;
			}

			photo.setLikes(photo.getLikes() + 1);
			model.getPhotoDAO().update(photo);
			model.likeHistoryDAO.inc(photo.getUserId());

			return Util.getString(ViewPhotoAction.NAME, "?id=", photo.getId());
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return LIKE_FAILED_PAGE;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	private int getId(HttpServletRequest request) {
		String idString = request.getParameter("id");
		if (idString == null) {
			Util.e("id is required");
			return 0;
		}

		try {
			return Integer.valueOf(idString);
		} catch (Exception e) {
			Util.e("invalid id, id = ", idString);
		}
		return 0;
	}
}
