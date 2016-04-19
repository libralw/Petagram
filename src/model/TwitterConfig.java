package model;

import util.ConfigFile;

public class TwitterConfig extends ConfigFile {
	public String consumerKey;
	public String callbackUrl;
	public String consumerSecret;

	public TwitterConfig(String dir) {
		super(dir);
	}
}
