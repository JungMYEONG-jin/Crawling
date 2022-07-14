package com.shinhan.market.daemon.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SendInfoMapper implements ParameterizedRowMapper<SendInfo> {
	@Override
	public SendInfo mapRow(ResultSet rs, int rowNum) throws SQLException
	{		
		SendInfo sendInfo = new SendInfo(
				rs.getString("SEQ"),
				rs.getString("APP_ID"),
				rs.getString("APP_PACKAGE"),
				rs.getString("OS_TYPE"),
				rs.getString("STORE_URL"),
				rs.getString("TITLE_NODE"),
				rs.getString("VERSION_NODE"),
				rs.getString("UPDATE_NODE"),
				rs.getString("REG_DT"),
				"",
				rs.getString("METHOD_TYPE"));
		
		return sendInfo;
	}	
}
