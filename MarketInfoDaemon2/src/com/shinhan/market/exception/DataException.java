/**
 * @author ±èµ¿Çå
 *
 * 2012.09.09
 */
package com.shinhan.market.exception;

public class DataException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DataException() {
		
	}
	
	public DataException(String message) {
		super("[DataException] "+message);
	}
	
	public DataException(String message, Exception e) {
		super("[DataException] "+message, e);
	}
}
