package controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.PhotoDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import util.Util;
import databeans.Connection;
import databeans.Photo;
import databeans.User;

public class HomeAction extends Action {

	private static final String HOME_JSP = "home.jsp";

	public static final String NAME = "home.do";

	PhotoDAO photoDAO;

	public HomeAction(Model model) {
		super(model);
		photoDAO = model.getPhotoDAO();
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

			// get maxId
			int maxId = getIntegerParameter(request, "maxId", Integer.MAX_VALUE);
			int minId = getIntegerParameter(request, "minId", 0);

			// get photos
			ArrayList<Integer> followedIds = getFollowedIdsOf(user);
			followedIds.add(user.getId());
			Photo[] photos = photoDAO.getPhotoOfFollowed(followedIds);
			if (photos == null || photos.length == 0) {
				return HOME_JSP;
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
			request.setAttribute("nextPage",
					Util.getString(HomeAction.NAME, "?maxId=", validPhotos[validPhotos.length - 1].getId()));
			request.setAttribute("prevPage", Util.getString(HomeAction.NAME, "?minId=", validPhotos[0].getId()));
			request.setAttribute("hasNext", validPhotos[validPhotos.length - 1] != photos[0]);
			return HOME_JSP;
		} catch (Exception e) {
			return HOME_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}

	private ArrayList<Integer> getFollowedIdsOf(User user) throws RollbackException {
		ArrayList<Integer> followedIds = new ArrayList<>();
		Connection[] followeds = model.connectionDAO.getFollowedOf(user.getUserName());
		for (Connection connection : followeds) {
			User followed = model.getUserDAO().readByUserName(connection.getFollowed());
			if (followed != null) {
				Util.i("add ", connection.getFollowed());
				followedIds.add(followed.getId());
			}
		}
		return followedIds;
	}
}
