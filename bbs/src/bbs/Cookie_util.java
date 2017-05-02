package bbs;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cookie_util {
	// 한번이라도 글 등록했다면 쿠키에 저장
	private HashMap<String, Cookie> cookie_map = new HashMap<String, Cookie>();
	static BASE64Encoder encoder = new BASE64Encoder();
	static BASE64Decoder decoder = new BASE64Decoder();

	// 생성자
	public Cookie_util(HttpServletRequest request) throws IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie_map.put(cookies[i].getName(), cookies[i]);
			}
		}
	}

	// 쿠키생성
	// 이름,값
	public static Cookie create_cookie(String name, String value) throws IOException {
		return new Cookie(name, encoder.encode(value.getBytes()));
	}

	// 이름,값,경로,저장시간(유효기간/초단위)
	public static Cookie create_cookie(String name, String value, String path, int max_age) throws IOException {
		Cookie ck = new Cookie(name, encoder.encode(value.getBytes()));
		ck.setPath(path);
		ck.setMaxAge(max_age);
		return ck;
	}

	// 이름,값,도메인,경로,저장시간(유효기간)
	public static Cookie create_cookie(String name, String value, String domain, String path, int max_age)
			throws IOException {
		Cookie ck = new Cookie(name, encoder.encode(value.getBytes()));
		ck.setDomain(domain);
		ck.setPath(path);
		ck.setMaxAge(max_age);
		return ck;
	}

	// 쿠키이름
	public Cookie get_cookie(String name) {
		return (Cookie) cookie_map.get(name);

	}

	// 쿠키값
	public String get_value(String name) throws IOException {
		Cookie ck = (Cookie) cookie_map.get(name);
		if (ck == null) {
			return null;
		} else {
			byte[] b = decoder.decodeBuffer(ck.getValue());// 한글안깨지도록
			return new String(b);
		}
	}

	// ?? 쿠키값 존재여부
	public boolean exists(String name) {
		return cookie_map.get(name) != null;
	}
}
