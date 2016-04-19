package model;

import util.ConfigFile;

public class MySQLConfig extends ConfigFile {
	public String username = "root";
	public String password = "";

	public MySQLConfig(String dir) {
		super(dir);
	}
}
