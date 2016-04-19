package controllor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.apache.commons.io.FileUtils;
import org.genericdao.RollbackException;
import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import databeans.Photo;
import databeans.User;
import formbeans.UploadPhotoForm;

public class UploadPhotoAction extends Action {
	private static final String ERROR_JSP = "home.jsp";

	private static final String UPLOAD_DO = "upload-photo.do";

	private FormBeanFactory<UploadPhotoForm> formBeanFactory = FormBeanFactory.getInstance(UploadPhotoForm.class);

	public UploadPhotoAction(Model model) {
		super(model);
	}

	public String getName() {
		return UPLOAD_DO;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			User user = (User) request.getSession(false).getAttribute("user");
			UploadPhotoForm form = formBeanFactory.create(request);
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0)
				return ERROR_JSP;

			FileProperty fileProp = form.getFile();
			if (getFileNameSuffix(fileProp.getFileName()) == null) {
				errors.add("unsupported file type");
				return ERROR_JSP;
			}

			// save file to WebContent/images/upload
			String webContentPath = request.getServletContext().getRealPath("/");
			String fileLocalName = generateFileName(fileProp);
			String pathInWebContent = Util.getPath("images/upload", user.getId(), fileLocalName);
			String pathInSystem = Util.getPath(webContentPath, pathInWebContent);
			Util.i("save file to ", pathInSystem);
			FileUtils.writeByteArrayToFile(new File(pathInSystem), fileProp.getBytes());

			// save to db
			Photo photo = new Photo();
			photo.setUrl(pathInWebContent);
			if (!Util.isEmpty(form.getText())) {
				photo.setText(fixBadChars(form.getText()));
			}
			photo.setUserId(user.getId());
			photo.setTime(System.currentTimeMillis() / 1000);
			model.getPhotoDAO().create(photo);

			return HomeAction.NAME;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return ERROR_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return ERROR_JSP;
		} catch (IOException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return ERROR_JSP;
		}
	}

	private String getFileNameSuffix(String fileName) {
		if (Util.isEmpty(fileName)) {
			return null;
		}
		fileName = fileName.toLowerCase();
		for (String suffix : Constants.IMAGE_SUFFIXES) {
			if (fileName.endsWith(suffix)) {
				return suffix;
			}
		}
		return null;
	}

	private String generateFileName(FileProperty file) {
		String sha1 = Util.getSHA1(file.getBytes(), null);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		return Util.getString(sha1, timestamp, getFileNameSuffix(file.getFileName()));
	}

	private String fixBadChars(String s) {
		if (s == null || s.length() == 0)
			return s;

		Pattern p = Pattern.compile("[<>\"&]");
		Matcher m = p.matcher(s);
		StringBuffer b = null;
		while (m.find()) {
			if (b == null)
				b = new StringBuffer();
			switch (s.charAt(m.start())) {
			case '<':
				m.appendReplacement(b, "&lt;");
				break;
			case '>':
				m.appendReplacement(b, "&gt;");
				break;
			case '&':
				m.appendReplacement(b, "&amp;");
				break;
			case '"':
				m.appendReplacement(b, "&quot;");
				break;
			default:
				m.appendReplacement(b, "&#" + ((int) s.charAt(m.start())) + ';');
			}
		}

		if (b == null)
			return s;
		m.appendTail(b);
		return b.toString();
	}

}
