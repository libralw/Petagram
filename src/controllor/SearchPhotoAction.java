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
import model.PhotoDAO;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import databeans.Photo;
import databeans.User;
import formbeans.SearchForm;

public class SearchPhotoAction extends Action {

	private static final String SEARCH_RESULT_JSP = "search.jsp";

	public static final String NAME = "search-photo.do";

	private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);

	UserDAO userDao;

	public SearchPhotoAction(Model model) {
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
			request.setAttribute("user", user);

			SearchForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			Util.i(form);
			if (!form.isPresent()) {
				return SEARCH_RESULT_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return SEARCH_RESULT_JSP;
			}

			// get maxId
			int maxId = getIntegerParameter(request, "maxId", Integer.MAX_VALUE);
			int minId = getIntegerParameter(request, "minId", 0);

			// get photos
			PhotoDAO photoDAO = model.getPhotoDAO();
			Photo[] photos = photoDAO.getPhotosOf(form.getKeyword());
			if (photos == null || photos.length == 0) {
				return SEARCH_RESULT_JSP;
			}

			Photo[] validPhotos = null;
			if (minId != 0) {
				validPhotos = PhotoDAO.getOldestN(PhotoDAO.filter(photos, minId, maxId),
						Constants.PHOTO_NUMBER_PER_PAGE);
			} else {
				validPhotos = PhotoDAO.getLatestN(PhotoDAO.filter(photos, minId, maxId),
						Constants.PHOTO_NUMBER_PER_PAGE);
			}

			request.setAttribute("photos", validPhotos);
			request.setAttribute("hasPrev", validPhotos[0] != photos[photos.length - 1]);
			request.setAttribute("nextPage", Util.getString(SearchPhotoAction.NAME, "?maxId=",
					validPhotos[validPhotos.length - 1].getId(), "&keyword=", form.getKeyword()));
			request.setAttribute("prevPage", Util.getString(SearchPhotoAction.NAME, "?minId=", validPhotos[0].getId(),
					"&keyword=", form.getKeyword()));
			request.setAttribute("hasNext", validPhotos[validPhotos.length - 1] != photos[0]);

			return SEARCH_RESULT_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return SEARCH_RESULT_JSP;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return SEARCH_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
