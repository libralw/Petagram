package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import util.Util;
import databeans.Connection;

public class ConnectionDAO extends GenericDAO<Connection> {

	public ConnectionDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Connection.class, tableName, pool);
	}

	public void createIfNotExists(Connection connection) throws RollbackException {
		try {
			Transaction.begin();
			Connection[] connections = match(MatchArg.and(MatchArg.equals("followed", connection.getFollowed()),
					MatchArg.equals("follower", connection.getFollower())));
			if (connections == null || connections.length == 0) {
				create(connection);
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

	public void deleteIfExists(Connection connection) throws RollbackException {
		try {
			Transaction.begin();
			Connection[] connections = match(MatchArg.and(MatchArg.equals("followed", connection.getFollowed()),
					MatchArg.equals("follower", connection.getFollower())));
			if (connections != null && connections.length > 0) {
				delete(connections[0].getId());
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

	public Connection[] getFollowerOf(String userName) throws RollbackException {
		Connection[] connections = match(MatchArg.equals("followed", userName));
		return connections;
	}

	public Connection[] getFollowedOf(String userName) throws RollbackException {
		Connection[] connections = match(MatchArg.equals("follower", userName));
		return connections;
	}

	public void createDefaultConnection(String follower) {
		for (String defaultUser : Constants.DEFAULT_INSTAGRAM_ACCOUNTS) {
			Connection connection = new Connection();
			connection.setFollower(follower);
			connection.setFollowed(defaultUser);
			try {
				createIfNotExists(connection);
			} catch (RollbackException e) {
				Util.e(e);
			}
		}
		for (String defaultUser : Constants.DEFAULT_TWITTER_ACCOUNTS) {
			Connection connection = new Connection();
			connection.setFollower(follower);
			connection.setFollowed(defaultUser);
			try {
				createIfNotExists(connection);
			} catch (RollbackException e) {
				Util.e(e);
			}
		}
	}
}
