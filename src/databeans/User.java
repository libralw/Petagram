package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class User {
	private int id;
	private String userName = null;
	private String twitterId = null;
	private String instagramId = null;

	private String hashedPassword = "*";
	private int salt = 0;

	public boolean checkPassword(String password) {
		return hashedPassword.equals(hash(password));
	}

	public int getId() {
		return id;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public int getSalt() {
		return salt;
	}

	public int hashCode() {
		return userName.hashCode();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTwitterId(String s) {
		twitterId = s;
	}

	public void setHashedPassword(String x) {
		hashedPassword = x;
	}

	public void setPassword(String s) {
		salt = newSalt();
		hashedPassword = hash(s);
	}

	public void setSalt(int x) {
		salt = x;
	}

	private String hash(String clearPassword) {
		if (salt == 0)
			return null;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);

		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		String digestStr = bytesToHex(digestBytes);

		return digestStr;
	}

	private String bytesToHex(byte[] digestBytes) {
		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i = 0; i < digestBytes.length; i++) {
			int lowNibble = digestBytes[i] & 0x0f;
			int highNibble = (digestBytes[i] >> 4) & 0x0f;
			digestSB.append(Integer.toHexString(highNibble));
			digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();
		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192) + 1; // salt cannot be zero
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInstagramId() {
		return instagramId;
	}

	public void setInstagramId(String instagramId) {
		this.instagramId = instagramId;
	}
}
