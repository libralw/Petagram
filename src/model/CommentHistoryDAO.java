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
import databeans.CommentHistory;

public class CommentHistoryDAO extends GenericDAO<CommentHistory> {

	public CommentHistoryDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(CommentHistory.class, tableName, pool);
	}

	static long TIME_MASK = 24 * 60 * 60 * 1000;

	public void inc(int userId) throws RollbackException {
		try {
			Transaction.begin();

			long current = System.currentTimeMillis();
			long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;

			CommentHistory[] comments = match(
					MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("date", timeOf00h00m00s)));
			CommentHistory commentHistory = null;
			if (comments == null || comments.length == 0) {
				commentHistory = new CommentHistory();
				commentHistory.setUserId(userId);
				commentHistory.setDate(timeOf00h00m00s);
				commentHistory.setComments(1);
				create(commentHistory);
			} else {
				commentHistory = comments[0];
				commentHistory.setComments(commentHistory.getComments() + 1);
				update(commentHistory);
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

	public CommentHistory[] getWeeklyHistory(int userId) throws RollbackException {
		long current = System.currentTimeMillis();
		long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeOf00h00m00s);

		int DAYS_OF_WEEK = 7;
		c.add(Calendar.DATE, -(DAYS_OF_WEEK - 1));
		long sevenDaysAgo = c.getTimeInMillis();

		CommentHistory[] Comments = match(
				MatchArg.and(MatchArg.equals("userId", userId), MatchArg.greaterThan("date", sevenDaysAgo)));
		if (Comments == null) {
			Comments = new CommentHistory[0];
		}

		ArrayList<CommentHistory> result = new ArrayList<>();
		while (c.getTimeInMillis() <= timeOf00h00m00s) {
			CommentHistory CommentHistory = null;
			for (CommentHistory comment : Comments) {
				if (comment.getDate() == c.getTimeInMillis()) {
					CommentHistory = comment;
				}
			}
			if (CommentHistory == null) {
				CommentHistory = new CommentHistory();
				CommentHistory.setDate(c.getTimeInMillis());
				CommentHistory.setUserId(userId);
				CommentHistory.setComments(0);
			}
			result.add(CommentHistory);
			c.add(Calendar.DATE, 1);
		}

		return result.toArray(new CommentHistory[result.size()]);
	}
}
