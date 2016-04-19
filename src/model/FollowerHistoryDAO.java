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
import databeans.FollowerHistory;

public class FollowerHistoryDAO extends GenericDAO<FollowerHistory> {

	public FollowerHistoryDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(FollowerHistory.class, tableName, pool);
	}

	static long TIME_MASK = 24 * 60 * 60 * 1000;

	public void inc(int userId) throws RollbackException {
		try {
			Transaction.begin();

			long current = System.currentTimeMillis();
			long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;

			FollowerHistory[] followerHistory = match(
					MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("date", timeOf00h00m00s)));
			FollowerHistory visitHistory = null;
			if (followerHistory == null || followerHistory.length == 0) {
				visitHistory = new FollowerHistory();
				visitHistory.setUserId(userId);
				visitHistory.setDate(timeOf00h00m00s);
				visitHistory.setFollowers(1);
				create(visitHistory);
			} else {
				visitHistory = followerHistory[0];
				visitHistory.setFollowers(visitHistory.getFollowers() + 1);
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

	public void dec(int userId) throws RollbackException {
		try {
			Transaction.begin();

			long current = System.currentTimeMillis();
			long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;

			FollowerHistory[] followerHistory = match(
					MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("date", timeOf00h00m00s)));
			FollowerHistory visitHistory = null;
			if (followerHistory == null || followerHistory.length == 0) {
				visitHistory = new FollowerHistory();
				visitHistory.setUserId(userId);
				visitHistory.setDate(timeOf00h00m00s);
				visitHistory.setFollowers(-1);
				create(visitHistory);
			} else {
				visitHistory = followerHistory[0];
				visitHistory.setFollowers(visitHistory.getFollowers() - 1);
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

	public FollowerHistory[] getWeeklyHistory(int userId) throws RollbackException {
		long current = System.currentTimeMillis();
		long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeOf00h00m00s);

		int DAYS_OF_WEEK = 7;
		c.add(Calendar.DATE, -(DAYS_OF_WEEK - 1));
		long sevenDaysAgo = c.getTimeInMillis();

		FollowerHistory[] historyRecords = match(
				MatchArg.and(MatchArg.equals("userId", userId), MatchArg.greaterThan("date", sevenDaysAgo)));
		if (historyRecords == null) {
			historyRecords = new FollowerHistory[0];
		}

		ArrayList<FollowerHistory> result = new ArrayList<>();
		while (c.getTimeInMillis() <= timeOf00h00m00s) {
			FollowerHistory followers = null;
			for (FollowerHistory history : historyRecords) {
				if (history.getDate() == c.getTimeInMillis()) {
					followers = history;
				}
			}
			if (followers == null) {
				followers = new FollowerHistory();
				followers.setDate(c.getTimeInMillis());
				followers.setUserId(userId);
				followers.setFollowers(0);
			}
			result.add(followers);
			c.add(Calendar.DATE, 1);
		}

		return result.toArray(new FollowerHistory[result.size()]);
	}
}
