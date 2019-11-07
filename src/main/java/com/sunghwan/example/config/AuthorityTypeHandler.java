package com.sunghwan.example.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class AuthorityTypeHandler extends BaseTypeHandler<SimpleGrantedAuthority> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement,
                                    int i,
                                    SimpleGrantedAuthority simpleGrantedAuthority,
                                    JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, simpleGrantedAuthority.getAuthority());
    }

    @Override
    public SimpleGrantedAuthority getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return new SimpleGrantedAuthority(resultSet.getString(columnName));
    }

    @Override
    public SimpleGrantedAuthority getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return new SimpleGrantedAuthority(resultSet.getString(columnIndex));
    }

    @Override
    public SimpleGrantedAuthority getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return new SimpleGrantedAuthority(callableStatement.getString(columnIndex));
    }
}
