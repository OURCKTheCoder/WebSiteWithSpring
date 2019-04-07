package top.ourck.beans;

public class User {

	private int id;
	private String name;
	private String password;
	private String salt;
	private String image;
	
	public User() {}

	public User(int id, String name, String password, String salt, String image) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.salt = salt;
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", salt=" + salt + ", image=" + image
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
