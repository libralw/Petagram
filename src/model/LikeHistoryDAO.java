package model;

import java.util.ArrayList;
import java.util.Calendar;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Util;
import databeans.LikeHistory;

public class LikeHistoryDAO extends GenericDAO<LikeHistory> {

	public LikeHistoryDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(LikeHistory.class, tableName, pool);
	}

	static long TIME_MASK = 24 * 60 * 60 * 1000;

	public void inc(int userId) throws RollbackException {
		try {
			Transaction.begin();

			long current = System.currentTimeMillis();
			long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;

			LikeHistory[] visits = match(
					MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("date", timeOf00h00m00s)));
			LikeHistory visitHistory = null;
			if (visits == null || visits.length == 0) {
				visitHistory = new LikeHistory();
				visitHistory.setUserId(userId);
				visitHistory.setDate(timeOf00h00m00s);
				visitHistory.setLikes(1);
				create(visitHistory);
			} else {
				visitHistory = visits[0];
				visitHistory.setLikes(visitHistory.getLikes() + 1);
				update(visitHistory);
			}
			Transaction.commit();
		} catch (RollbackException e) {
			Util.e(e);
			throw e;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public LikeHistory[] getWeeklyHistory(int userId) throws RollbackException {
		long current = System.currentTimeMillis();
		long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeOf00h00m00s);

		int DAYS_OF_WEEK = 7;
		c.add(Calendar.DATE, -(DAYS_OF_WEEK - 1));
		long sevenDaysAgo = c.getTimeInMillis();

		LikeHistory[] likes = match(
				MatchArg.and(MatchArg.equals("userId", userId), MatchArg.greaterThan("date", sevenDaysAgo)));
		if (likes == null) {
			likes = new LikeHistory[0];
		}

		ArrayList<LikeHistory> result = new ArrayList<>();
		while (c.getTimeInMillis() <= timeOf00h00m00s) {
			LikeHistory likeHistory = null;
			for (LikeHistory like : likes) {
				if (like.getDate() == c.getTimeInMillis()) {
					likeHistory = like;
				}
			}
			if (likeHistory == null) {
				likeHistory = new LikeHistory();
				likeHistory.setDate(c.getTimeInMillis());
				likeHistory.setUserId(userId);
				likeHistory.setLikes(0);
			}
			result.add(likeHistory);
			c.add(Calendar.DATE, 1);
		}

		return result.toArray(new LikeHistory[result.size()]);
	}
}
