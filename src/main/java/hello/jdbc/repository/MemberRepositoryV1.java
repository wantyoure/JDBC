package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j

/*
*  JDBC - DataSoure 사용 , jdbcUtils
* */
public class MemberRepositoryV1 {

        MemberRepositoryV1 memberRepositoryV1;


        private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
            String sql = "insert into member(member_id, money) values(?,?)";

            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = getConnection();
                pstmt=con.prepareStatement(sql);
                pstmt.setString(1,member.getMemberId());
                pstmt.setInt(2,member.getMoney());
                pstmt.executeUpdate(); // 쿼리 실행
                return member;
            } catch (SQLException e) {
                log.error("db error", e);
                throw e;
            }finally {
                pstmt.close();
                con.close();
            }

        }


    public Member findById(String memberId) throws SQLException {
            String sql = "select * from member where member_id = ?";

            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                con = getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,memberId);
                rs = pstmt.executeQuery(); // 실행할때 하는 것
                if (rs.next()) {
                    Member member = new Member();
                    member.setMemberId(rs.getString("member_id"));
                    member.setMoney(rs.getInt("money"));
                    return member;
                }else {
                    throw new NoSuchElementException("member not found memberId =" + memberId);
                }

            }catch (SQLException e) {
                log.info("db error", e);
                throw e;
            }finally {
                close(con,pstmt,rs);
            }
    }

    public void update(String memberId, int money)throws SQLException {
            String sql = "update member set money=? where member_id =?";

            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

        try {
            con = getConnection();
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,money);
            pstmt.setString(2,memberId);
            int resultSize = pstmt.executeUpdate();// 쿼리 실행
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            pstmt.close();
            con.close();
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            pstmt.close();
            con.close();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={},class={}",con,con.getClass());
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
