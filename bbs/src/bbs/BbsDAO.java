package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BbsDAO {
	private DataSource ds = null;
	private Connection con = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 생성자
	public BbsDAO() {
		// dbcp접속
		try {
			Context init = new InitialContext();
			Context env = (Context) init.lookup("java:/comp/env");
			ds = (DataSource) env.lookup("jdbc/bbs");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 데이터목록
	public Vector<Object> get_data_list(String field, String word) throws SQLException {
		Vector<Object> list = new Vector<Object>();
		try {
			con = ds.getConnection();
			st = con.createStatement();
			String sql = null;
			if (word == null || word.equals("")) {
				sql = "select * from bbs order by pos";
			} else {
				sql = "select * from bbs where " + field + "like '%" + word + "%' order by pos";
			}
			rs = st.executeQuery(sql);

			while (rs.next()) {
				BbsDTO dto = new BbsDTO();
				dto.setNum(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setEmail(rs.getString(3));
				dto.setHomepage(rs.getString(4));
				dto.setSubject(rs.getString(5));
				dto.setContent(rs.getString(6));
				dto.setPos(rs.getInt(7));
				dto.setDepth(rs.getInt(8));
				dto.setRegdate(rs.getString(9));
				dto.setPass(rs.getString(10));
				dto.setCount(rs.getInt(11));
				dto.setIp(rs.getString(12));
				list.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	// pos칼럼(순서)값에 1더함
	public void plus_pos() throws SQLException {
		try {
			con = ds.getConnection();
			st = con.createStatement();
			st.executeUpdate("update bbs set pos = pos+1");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	// count칼럼(조회수)값에 1더함
	public void plus_count(int num) throws SQLException {
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("update bbs set count= count+1 where num=?");
			ps.setInt(1, num);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	// 데이터 삽입
	public void insert_data(BbsDTO dto) throws SQLException {
		// 순서 1더함
		plus_pos();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("insert into bbs values (bbs_seq.nextval,?,?,?,?,?,?,?,sysdate,?,?,?)");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getHomepage());
			ps.setString(4, dto.getSubject());
			ps.setString(5, dto.getContent());
			ps.setInt(6, dto.getPos());
			ps.setInt(7, dto.getDepth());
			ps.setString(8, dto.getPass());
			ps.setInt(9, dto.getCount());
			ps.setString(10, dto.getIp());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 해당 특정 레코드 불러오기
	public BbsDTO get_data(int num) throws SQLException {
		BbsDTO dto = new BbsDTO();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("select * from bbs where num=?");
			ps.setInt(1, num);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto.setNum(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setEmail(rs.getString(3));
				dto.setHomepage(rs.getString(4));
				dto.setSubject(rs.getString(5));
				dto.setContent(rs.getString(6));
				dto.setPos(rs.getInt(7));
				dto.setDepth(rs.getInt(8));
				dto.setRegdate(rs.getString(9));
				dto.setPass(rs.getString(10));
				dto.setCount(rs.getInt(11));
				dto.setIp(rs.getString(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		return dto;
	}

	// 해당 특정 레코드 삭제
	public void delete_data(int num) throws SQLException {

		try {
			con = ds.getConnection();
			ps = con.prepareStatement("delete form bbs where num=?");
			ps.setInt(1, num);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.close();
			con.close();
		}
	}

	// 해당 특정 레코드 업데이트
	public void update_data(BbsDTO dto) throws SQLException {

		try {
			con = ds.getConnection();
			ps = con.prepareStatement("update bbs set name=?,email=?,homepage=?," + "subject=?,content=? where num=?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getHomepage());
			ps.setString(4, dto.getSubject());
			ps.setString(5, dto.getContent());
			ps.setInt(6, dto.getNum());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.close();
			con.close();
		}
	}

	// 순서가 추가로 들어왔다면 해당순서보다 후위순서들 +1
	public void update_reply(BbsDTO dto) throws SQLException {
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("update bbs set pos=pos+1 where pos>?");
			ps.setInt(1, dto.getPos());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.close();
			con.close();
		}
	}

	// 답변글 삽입
	public void insert_reply(BbsDTO dto) throws SQLException {
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("insert into bbs values (bbs_seq.nextval,?,?,?,?,?,?,?,sysdate,?,?,?)");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getHomepage());
			ps.setString(4, dto.getSubject());
			ps.setString(5, dto.getContent());
			ps.setInt(6, dto.getPos() + 1);
			ps.setInt(7, dto.getDepth() + 1);
			ps.setString(8, dto.getPass());
			ps.setInt(9, dto.getCount());
			ps.setString(10, dto.getIp());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
