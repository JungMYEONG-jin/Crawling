package com.shinhan.market.daemon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MarketInfoMapper implements ParameterizedRowMapper<MarketInfo> {
	@Override
	public MarketInfo mapRow(ResultSet rs, int rowNum) throws SQLException
	{		
		MarketInfo sendInfo = new MarketInfo(
				rs.getString("SEQ"),
				rs.getString("APP_ID"),
				rs.getString("APP_PACKAGE"),
				rs.getString("OS_TYPE"),
				rs.getString("STORE_URL"),
				rs.getString("TITLE_NODE"),
				rs.getString("VERSION_NODE"),
				rs.getString("UPDATE_NODE"),
				rs.getString("REG_DT"),
				rs.getString("UPT_DT"),
				rs.getString("METHOD_TYPE")
				);
		
		
		return sendInfo;
	}	
}
