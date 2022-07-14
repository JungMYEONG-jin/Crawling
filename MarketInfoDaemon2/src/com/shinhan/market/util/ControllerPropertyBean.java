package com.shinhan.market.util;

import java.io.Serializable;
import java.util.Map;

public class ControllerPropertyBean implements Serializable {
	private static final long serialVersionUID = 1L;

	Map<String, String> props = null;

	public Map<String, String> getProps() {
		return props;
	}

	public void setProps(Map<String, String> props) {
		this.props = props;
	}
}
