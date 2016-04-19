package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigFile {
	protected transient String dir;
	private Map<String, Object> miscellaneous;

	public void commit() {
		writeToJsonFile();
	}

	public String toString() {
		return new Gson().toJson(this);
	}

	public ConfigFile(String dir) {
		this.dir = dir;
		String configFilePath = getConfigFilePath();
		File file = new File(configFilePath);
		if (!file.exists()) {
			Util.i(file, " not exist");
			return;
		}
		String jsonString = readFileContent(file);
		Util.i("jsonString = ", jsonString);
		ConfigFile configFile = new Gson().fromJson(jsonString, this.getClass());
		try {
			copy(this, configFile);
		} catch (IllegalAccessException e) {
			Util.e(e);
		}
	}

	public void write(String key, Object value) {
		if (miscellaneous == null) {
			miscellaneous = new HashMap<String, Object>();
		}
		miscellaneous.put(key, value);
		writeToJsonFile();
	}

	public Object read(String key) {
		if (miscellaneous == null) {
			return null;
		}
		return miscellaneous.get(key);
	}

	private String getConfigFilePath() {
		return Util.getPath(dir, Util.getString(this.getClass().getSimpleName(), ".json"));
	}

	protected void writeToJsonFile() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(this);
		RandomAccessFile randomAccessFile = null;
		try {
			File file = new File(getConfigFilePath());
			if (!file.exists()) {
				file.createNewFile();
			}
			randomAccessFile = new RandomAccessFile(file, "rws");
			randomAccessFile.writeUTF(jsonString);
		} catch (IOException e) {
			Util.e(e);
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static String readFileContent(File file) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		StringBuilder sb = new StringBuilder();
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String s = null;
			do {
				s = bufferedReader.readLine();
				if (!Util.isEmpty(s)) {
					sb.append(s);
				}
			} while (!Util.isEmpty(s));
		} catch (FileNotFoundException e) {
			Util.e(e);
		} catch (IOException e) {
			Util.e(e);
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				Util.e("close", e);
			}
		}
		return sb.toString();
	}

	public static void copy(Object to, Object from) throws IllegalAccessException {
		if (from == null || to == null || !from.getClass().equals(to.getClass())) {
			throw new IllegalArgumentException();
		}
		Class<?> cls = from.getClass();
		while (cls != Object.class) {
			Field[] f = cls.getDeclaredFields();
			for (Field field : f) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				Object fieldValue = field.get(from);
				field.set(to, fieldValue);
			}
			cls = cls.getSuperclass();
		}
	}
}
