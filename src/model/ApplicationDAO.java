package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Util;
import databeans.ApplicationData;

public class ApplicationDAO extends GenericDAO<ApplicationData> {

	public ApplicationDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(ApplicationData.class, tableName, pool);
	}

	public synchronized void init() {
		try {
			Transaction.begin();

			if (getCount() == 0) {
				ApplicationData data = new ApplicationData();
				create(data);
			}

			Transaction.commit();
		} catch (RollbackException e) {
			Util.e(e);
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public ApplicationData getApplicationData() {
		ApplicationData[] applicationData = null;
		try {
			applicationData = match();
		} catch (RollbackException e) {
			Util.e(e);
		}
		return applicationData[0];
	}

	public long getNextUpdateTime() {
		ApplicationData appdata = getApplicationData();
		return appdata.getNextUpdateTime();
	}
}
