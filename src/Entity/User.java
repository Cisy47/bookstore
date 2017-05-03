package Entity;

public class User extends Object implements Comparable<User>{
	private int id;
	private String username;
	private String password;
	private String email;
	private String mobile;
	private int coin;
    private int role;
	private String payPass;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				", coin=" + coin +
				", role=" + role +
				", payPass=" + payPass +'\''+
				'}';
	}

	public int getRole() {
		return role;
	}

	public String getPayPass() {
		return payPass;
	}

	public void setPayPass(String payPass) {
		this.payPass = payPass;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail(){return email; }

	public void setEmail(String email) {
		this.email = email;
	}

	public int compareTo(User user){
		return (int)(this.getId() - user.getId());
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User user = (User)obj;
		return user.getId() ==getId()&&
				user.getUsername().equals(getUsername())&&
				user.getPassword().equals(getPassword())&&
				user.getMobile()==getMobile()&&
				user.getEmail()==getEmail();
	}

}
