package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Comment;

public class CommentDAO extends GenericDAO<Comment> {

	public CommentDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Comment.class, tableName, pool);
	}

	public void create(Comment comment) throws RollbackException {
		try {
			Transaction.begin();
			super.create(comment);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public Comment[] getCommentsOf(int photoId) throws RollbackException {
		Comment[] comments = match(MatchArg.equals("photoId", photoId));

		if (comments.length == 0) {
			return null;
		}
		return comments;
	}

}
