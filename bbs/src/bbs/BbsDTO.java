package bbs;

public class BbsDTO {
	private int num;
	private String name = null;
	private String email = null;
	private String homepage = null;
	private String subject = null;
	private String content = null;
	private int pos = 0; // 순서
	private int depth = 0; // 답변글깊이
	private String regdate = null;
	private String pass = null;
	private int count = 0;
	private String ip = null;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	// 긴이름 자르기
	public String get_name_short() {
		if (name.length() > 3) {
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();

			name = sb1.append(name).substring(0, 3);
			name = sb2.append(name).append("...").toString();
		}
		return name;
	}

	// 긴제목 자르기
	public String get_subject_short() {
		if (subject.length() > 25) {
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();

			subject = sb1.append(subject).substring(0, 25);
			subject = sb2.append(subject).append("...").toString();
		}
		return subject;
	}
}
