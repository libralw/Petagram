package model;

import util.ConfigFile;

public class InstagramConfig extends ConfigFile {
	public String CLIENT_ID;
	public String CLIENT_SECRET;
	public String WEBSITE_URL;
	public String REDIRECT_URI;

	public InstagramConfig(String dir) {
		super(dir);
	}
}
