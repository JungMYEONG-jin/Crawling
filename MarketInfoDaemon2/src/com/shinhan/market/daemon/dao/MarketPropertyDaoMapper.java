package com.shinhan.market.daemon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MarketPropertyDaoMapper implements ParameterizedRowMapper<MarketPropertyDao> {
	@Override
	public MarketPropertyDao mapRow(ResultSet rs, int rowNum) throws SQLException
	{		
		MarketPropertyDao propertyDao = new MarketPropertyDao();
		propertyDao.setSeq(rs.getString("SEQ"));
		propertyDao.setPropertyVersion(rs.getString("PROPERTY_VERSION"));
		propertyDao.setPropertyStatus(rs.getString("PROPERTY_STATUS"));
		propertyDao.setPropertyDataType(rs.getString("PROPERTY_DATA_TYPE"));
		propertyDao.setPropertyData(rs.getString("PROPERTY_DATA"));
		propertyDao.setRegDt(rs.getString("REG_DT"));
		propertyDao.setUptDt(rs.getString("UPT_DT"));
		propertyDao.setRegUserId(rs.getString("REG_USER_ID"));
		propertyDao.setIsSetting(rs.getString("IS_SETTING"));
		return propertyDao;
	}	
}
