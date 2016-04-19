package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class CommentForm extends FormBean {
	private String comment;
	private String id;

	public String getComment() {
		return comment;
	}

	public void setComment(String s) {
		comment = trimAndConvert(s, "<>\"");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getIdErrors(errors);
		if (comment == null || comment.length() == 0) {
			errors.add("Comment is required");
		}

		return errors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdValue() {
		try {
			return Integer.valueOf(id);
		} catch (Exception e) {
			return 0;
		}
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
}
