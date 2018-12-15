package com.luo.util;

import com.luo.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * mysql分页工具类，注意start不是页数，是开始记录数
 * @author guoliang
 *
 */
public class MysqlPaginationUtil {
    private int start;//开始记录数，不是页数
    private int limit;//限制条数
    private int total;//查询总数
    private List<Map<String,Object>> list;//date
    private JdbcTemplate jdbcTemplate;

    public MysqlPaginationUtil() {
        super();
    }

    public int getStart() {
        return start;
    }

    /**
     * 开始记录数，不是页数
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 获取总数
     * @param sql
     */
    public void setCountSql(String sql){
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        String count = "";
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                count = StringUtil.trim(rs.getObject(1));
            }
            total = Integer.parseInt("".equals(count)?"0":count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(st);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * 获取数据
     * @param sql
     */
    public void setQuerySql(String sql){
        sql += " limit "+start+","+limit;
        list = jdbcTemplate.queryForList(sql);
    }
}
