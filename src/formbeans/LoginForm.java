package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

public class LoginForm extends FormBean {
	private String userName;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String s) {
		password = s.trim();
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (userName == null || userName.length() == 0)
			errors.add("User name is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");

		if (errors.size() > 0)
			return errors;

		if (userName.matches(".*[<>\"].*"))
			errors.add("User name may not contain angle brackets or quotes");

		return errors;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
