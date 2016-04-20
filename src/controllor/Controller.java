package controllor;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Model;
import util.Constants;
import util.Util;
import worker.DefaultInstagramAccountsUpdateTask;
import worker.DefaultTwitterAccountsUpdateTask;
import databeans.User;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	private Model model;
	Timer timer;

	public void init() throws ServletException {
		model = new Model(getServletConfig());
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				new DefaultInstagramAccountsUpdateTask(model).run();
				new DefaultTwitterAccountsUpdateTask(model).run();
			}
		}, 0, Constants.UPDATE_INTERVAL);

		Action.add(new HomeAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new LoginAction(model));
		Action.add(new InstagramLoginAction(model));
		Action.add(new TwitterLoginAction(model));
		Action.add(new InstagramLoginCallbackAction(model));
		Action.add(new TwitterLoginCallbackAction(model));
		Action.add(new SearchUserAction(model));
		Action.add(new SearchPhotoAction(model));
		Action.add(new ViewUserAction(model));
		Action.add(new ViewPhotoAction(model));
		Action.add(new CommentAction(model));
		Action.add(new LikeAction(model));
		Action.add(new ConnectionAction(model));
		Action.add(new UploadPhotoAction(model));
		Action.add(new LogoutAction(model));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		nextPage = performCommonAction(request);
		sendToNextPage(nextPage, request, response);
	}

	private String performCommonAction(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		User user = (User) request.getSession().getAttribute("user");

		if (action.endsWith("login.do") || action.endsWith("callback.do")
				|| action.equals(RegisterAction.REGISTER_NAME)) {
			return Action.perform(action, request);
		}

		if (user == null) {
			return LoginAction.NAME;
		}

		return Action.perform(action, request);
	}

	private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
			return;
		}
		String url = getUrlWithoutQueryString(nextPage);

		Util.i("nextPage = ", nextPage);
		if (nextPage.indexOf("://") != -1) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".do") || url.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
			d.forward(request, response);
			return;
		}

		throw new ServletException(
				Controller.class.getName() + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	private String getUrlWithoutQueryString(String nextPage) {
		int questionMark = nextPage.lastIndexOf('?');
		if (questionMark == -1) {
			return nextPage;
		}
		return nextPage.substring(0, questionMark);
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}

	@Override
	public void destroy() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.destroy();
	}
}
