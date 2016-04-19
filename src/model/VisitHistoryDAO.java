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
import databeans.VisitHistory;

public class VisitHistoryDAO extends GenericDAO<VisitHistory> {

	public VisitHistoryDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(VisitHistory.class, tableName, pool);
	}

	static long TIME_MASK = 24 * 60 * 60 * 1000;

	public void inc(int userId) throws RollbackException {
		try {
			Transaction.begin();

			long current = System.currentTimeMillis();
			long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;

			VisitHistory[] visits = match(
					MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("date", timeOf00h00m00s)));
			VisitHistory visitHistory = null;
			if (visits == null || visits.length == 0) {
				visitHistory = new VisitHistory();
				visitHistory.setUserId(userId);
				visitHistory.setDate(timeOf00h00m00s);
				visitHistory.setVisits(1);
				create(visitHistory);
			} else {
				visitHistory = visits[0];
				visitHistory.setVisits(visitHistory.getVisits() + 1);
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

	public VisitHistory[] getWeeklyHistory(int userId) throws RollbackException {
		long current = System.currentTimeMillis();
		long timeOf00h00m00s = current / TIME_MASK * TIME_MASK;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeOf00h00m00s);

		int DAYS_OF_WEEK = 7;
		c.add(Calendar.DATE, -(DAYS_OF_WEEK - 1));
		long sevenDaysAgo = c.getTimeInMillis();

		VisitHistory[] visits = match(
				MatchArg.and(MatchArg.equals("userId", userId), MatchArg.greaterThan("date", sevenDaysAgo)));
		if (visits == null) {
			visits = new VisitHistory[0];
		}

		ArrayList<VisitHistory> result = new ArrayList<>();
		while (c.getTimeInMillis() <= timeOf00h00m00s) {
			VisitHistory visitHistory = null;
			for (VisitHistory visit : visits) {
				if (visit.getDate() == c.getTimeInMillis()) {
					visitHistory = visit;
				}
			}
			if (visitHistory == null) {
				visitHistory = new VisitHistory();
				visitHistory.setDate(c.getTimeInMillis());
			}
			result.add(visitHistory);
			c.add(Calendar.DATE, 1);
		}

		return result.toArray(new VisitHistory[result.size()]);
	}
}
