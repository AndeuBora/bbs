package bbs;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cookie_util {
	// �ѹ��̶� �� ����ߴٸ� ��Ű�� ����
	private HashMap<String, Cookie> cookie_map = new HashMap<String, Cookie>();
	static BASE64Encoder encoder = new BASE64Encoder();
	static BASE64Decoder decoder = new BASE64Decoder();

	// ������
	public Cookie_util(HttpServletRequest request) throws IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie_map.put(cookies[i].getName(), cookies[i]);
			}
		}
	}

	// ��Ű����
	// �̸�,��
	public static Cookie create_cookie(String name, String value) throws IOException {
		return new Cookie(name, encoder.encode(value.getBytes()));
	}

	// �̸�,��,���,����ð�(��ȿ�Ⱓ/�ʴ���)
	public static Cookie create_cookie(String name, String value, String path, int max_age) throws IOException {
		Cookie ck = new Cookie(name, encoder.encode(value.getBytes()));
		ck.setPath(path);
		ck.setMaxAge(max_age);
		return ck;
	}

	// �̸�,��,������,���,����ð�(��ȿ�Ⱓ)
	public static Cookie create_cookie(String name, String value, String domain, String path, int max_age)
			throws IOException {
		Cookie ck = new Cookie(name, encoder.encode(value.getBytes()));
		ck.setDomain(domain);
		ck.setPath(path);
		ck.setMaxAge(max_age);
		return ck;
	}

	// ��Ű�̸�
	public Cookie get_cookie(String name) {
		return (Cookie) cookie_map.get(name);

	}

	// ��Ű��
	public String get_value(String name) throws IOException {
		Cookie ck = (Cookie) cookie_map.get(name);
		if (ck == null) {
			return null;
		} else {
			byte[] b = decoder.decodeBuffer(ck.getValue());// �ѱ۾ȱ�������
			return new String(b);
		}
	}

	// ?? ��Ű�� ���翩��
	public boolean exists(String name) {
		return cookie_map.get(name) != null;
	}
}
