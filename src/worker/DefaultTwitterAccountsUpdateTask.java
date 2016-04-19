package worker;

import java.util.List;

import model.ApplicationDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.scribe.model.Token;

import thirdPartyAPI.Twitter;
import util.Constants;
import util.Util;
import databeans.ApplicationData;
import databeans.Photo;

public class DefaultTwitterAccountsUpdateTask implements Runnable {
	Model model;
	public static Token accessToken;

	public DefaultTwitterAccountsUpdateTask(Model model) {
		this.model = model;
	}

	public static void setValidToken(Token accessToken) {
		DefaultTwitterAccountsUpdateTask.accessToken = accessToken;
	}

	public static boolean needToUpdate(ApplicationDAO applicationDAO) {
		ApplicationData appData = applicationDAO.getApplicationData();
		long nextUpdateTime = appData.getNextUpdateTime();
		long currentTime = System.currentTimeMillis();
		if (nextUpdateTime > currentTime) {
			Util.i("no need to update, skip");
			return false;
		}
		appData.setNextUpdateTime(currentTime + Constants.UPDATE_INTERVAL);
		try {
			applicationDAO.update(appData);
		} catch (RollbackException e) {
			Util.e(e);
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
		Util.i("need to execute update, nextUpdateTime = ", nextUpdateTime, ", currentTime = ", currentTime);
		return true;
	}

	@Override
	public void run() {
		if (!Constants.ENABLE_WORKER) {
			return;
		}

		if (accessToken == null) {
			Util.e("accessToken is null");
			return;
		}
		if (!needToUpdate(model.applicationDAO)) {
			return;
		}
		for (int i = 0; i < Constants.DEFAULT_TWITTER_ACCOUNTS.length; i++) {
			String userName = Constants.DEFAULT_TWITTER_ACCOUNTS[i];
			String tag = Constants.DEFAULT_TWITTER_ACCOUNT_TAGS[i];
			databeans.User user;
			try {
				user = model.getUserDAO().readByUserName(userName);
				if (user == null) {
					Util.e("user is null");
					continue;
				}

				List<Photo> photos = Twitter.getPictureOf(model.twitterConfig, accessToken, tag);
				for (Photo photo : photos) {
					photo.setUserId(user.getId());
					try {
						model.getPhotoDAO().create(photo);
					} catch (RollbackException e) {
					}
				}
			} catch (RollbackException e1) {
				Util.e(e1);
			}
		}

	}

}
