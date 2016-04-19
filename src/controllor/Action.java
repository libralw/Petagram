/**
 * 08-600 
 * hw#9
 * Jian Chen 
 * jianc1
 * Dec 06, 2014
 */
package controllor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import util.Util;

public abstract class Action {
	protected Model model = null;

	public Action(Model model) {
		this.model = model;
	}

	// Returns the name of the action, used to match the request in the hash
	// table
	public abstract String getName();

	// Returns the name of the jsp used to render the output.
	public abstract String perform(HttpServletRequest request);

	//
	// Class methods to manage dispatching to Actions
	//
	private static Map<String, Action> hash = new HashMap<String, Action>();

	public static void add(Action a) {
		synchronized (hash) {
			hash.put(a.getName(), a);
		}
	}

	public static String perform(String name, HttpServletRequest request) {
		Action a;
		synchronized (hash) {
			a = hash.get(name);
		}
		if (a == null)
			return null;
		return a.perform(request);
	}

	public int getIntegerParameter(HttpServletRequest request, String key, int defaultValue) {
		String valueString = request.getParameter(key);
		if (valueString == null) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(valueString);
		} catch (NumberFormatException e) {
			Util.e(e);
			return defaultValue;
		}
	}
}
