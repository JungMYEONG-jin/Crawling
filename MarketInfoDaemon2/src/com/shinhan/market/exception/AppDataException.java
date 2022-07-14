/**
 * @author ±èµ¿Çå
 *
 * 2012.09.09
 */
package com.shinhan.market.exception;

public class AppDataException extends DataException {
	private static final long serialVersionUID = 1L;
	
	public AppDataException() {
		
	}
	
	public AppDataException(String message) {
		super("[AppDataException] "+message);
	}
	
	public AppDataException(String message, Exception e) {
		super("[AppDataException] "+message, e);
	}
}
