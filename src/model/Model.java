package model;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import util.Util;

public class Model {
	private UserDAO userDAO;
	private PhotoDAO photoDAO;
	private CommentDAO commentDAO;
	private ConnectionPool pool = null;
	public InstagramConfig instagramConfig = null;
	public TwitterConfig twitterConfig = null;
	public ApplicationDAO applicationDAO;
	public ConnectionDAO connectionDAO;
	public VisitHistoryDAO visitHistoryDAO;
	public LikeHistoryDAO likeHistoryDAO;
	public FollowerHistoryDAO followerHistoryDAO;
	public CommentHistoryDAO commentHistoryDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String configFileDir = config.getServletContext().getRealPath("/config");
			Util.i("configFileDir = ", configFileDir);

			// SQL
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");
			MySQLConfig mySQLConfig = new MySQLConfig(configFileDir);
			pool = new ConnectionPool(jdbcDriver, jdbcURL, mySQLConfig.username, mySQLConfig.password);
			initDB();

			// INSTAGRAM
			instagramConfig = new InstagramConfig(configFileDir);

			// TWITTER
			twitterConfig = new TwitterConfig(configFileDir);

			// DAOs
			userDAO = new UserDAO("user", pool);
			userDAO.createDefaultAccount();
			photoDAO = new PhotoDAO("photo", pool);
			applicationDAO = new ApplicationDAO("appdata", pool);
			applicationDAO.init();
			connectionDAO = new ConnectionDAO("connection", pool);
			visitHistoryDAO = new VisitHistoryDAO("visit", pool);
			likeHistoryDAO = new LikeHistoryDAO("likeHistory", pool);
			followerHistoryDAO = new FollowerHistoryDAO("followerHistory", pool);
			commentHistoryDAO = new CommentHistoryDAO("commentHistory", pool);
			commentDAO = new CommentDAO("comment", pool);
		} catch (DAOException e) {
			throw new ServletException(e);
		} catch (RollbackException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	public ConnectionDAO getConnectionDAO() {
		return connectionDAO;
	}

	private void initDB() throws SQLException {
	}
}
