package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class ConnectionForm extends FormBean {
	private String action;
	private String id;

	public String getAction() {
		return action;
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getIdErrors(errors);
		getActionErrors(errors);

		return errors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdValue() {
		return Integer.valueOf(id);
	}

	private void getIdErrors(List<String> errors) {
		if (Util.isEmpty(id)) {
			errors.add("id is required");
			return;
		}
		try {
			Integer.parseInt(id);
		} catch (Exception e) {
			errors.add("invalid id");
		}
	}

	private void getActionErrors(List<String> errors) {
		if (Util.isEmpty(action)) {
			errors.add("action is required");
			return;
		}
		if (!isFollow() && !isUnFollow()) {
			errors.add("invalid action");
			return;
		}
	}

	private boolean isUnFollow() {
		return "unfollow".equals(action);
	}

	public boolean isFollow() {
		return "follow".equals(action);
	}
}
