package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import util.Util;
import databeans.User;

public class UserDAO extends GenericDAO<User> {

	public UserDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(User.class, tableName, pool);
	}

	public void createByPetagram(User user) throws RollbackException {
		try {
			Transaction.begin();
			if (readByUserName(user.getUserName()) != null) {
				throw new RollbackException("User name already exists");
			}
			super.create(user);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void createByTwitter(String instagramId, String preferredName) throws RollbackException {
		try {
			Transaction.begin();
			if (readByInstagramId(instagramId) != null) {
				throw new RollbackException("Twitter account already exists");
			}
			User user = new User();
			user.setTwitterId(instagramId);
			user.setUserName(getValidUserName(preferredName, "Twitter"));
			super.create(user);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void createByInstagram(String instagramId, String preferredName) throws RollbackException {
		try {
			Transaction.begin();
			if (readByInstagramId(instagramId) != null) {
				throw new RollbackException("InstagramId already exists");
			}
			User user = new User();
			user.setInstagramId(instagramId);
			user.setUserName(getValidUserName(preferredName, "Instagram"));
			super.create(user);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void setPassword(String email, String password) throws RollbackException {
		try {
			Transaction.begin();
			User dbUser = readByUserName(email);

			if (dbUser == null) {
				throw new RollbackException("User " + email + " no longer exists");
			}
			dbUser.setPassword(password);

			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public User readByUserName(String userName) throws RollbackException {
		User[] users = match(MatchArg.equals("userName", userName));

		if (users.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (users.length == 0) {
			return null;
		}
		return users[0];
	}

	public User[] searchUserName(String userName) throws RollbackException {
		User[] users = match(MatchArg.contains("userName", userName));
		return users;
	}

	public User readByTwitterId(String twitterId) throws RollbackException {
		User[] users = match(MatchArg.equals("twitterId", twitterId));

		if (users.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (users.length == 0) {
			return null;
		}
		return users[0];
	}

	public User readByInstagramId(String instagramId) throws RollbackException {
		User[] users = match(MatchArg.equals("instagramId", instagramId));

		if (users.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (users.length == 0) {
			return null;
		}
		return users[0];
	}

	private String getValidUserName(String preferredUserName, String suffix) throws RollbackException {
		String userName = preferredUserName;
		if (readByUserName(userName) == null) {
			return userName;
		}

		userName = Util.getString(preferredUserName, "@", suffix);
		if (readByUserName(userName) == null) {
			return userName;
		}

		userName = Util.getString(preferredUserName, "From", suffix);
		if (readByUserName(userName) == null) {
			return userName;
		}

		int tail = 0;
		do {
			userName = Util.getString(preferredUserName, tail);
			tail++;
		} while (readByUserName(userName) != null);

		return userName;
	}

	public void createDefaultAccount() throws RollbackException {
		try {
			Transaction.begin();
			for (String userName : Constants.DEFAULT_INSTAGRAM_ACCOUNTS) {
				User user = readByUserName(userName);
				if (user != null) {
					continue;
				}
				user = new User();
				user.setUserName(userName);
				create(user);
			}
			for (String userName : Constants.DEFAULT_TWITTER_ACCOUNTS) {
				User user = readByUserName(userName);
				if (user != null) {
					continue;
				}
				user = new User();
				user.setUserName(userName);
				create(user);
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
