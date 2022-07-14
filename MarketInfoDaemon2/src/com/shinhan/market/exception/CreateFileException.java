/**
 * @author ±èµ¿Çå
 *
 * 2012.09.09
 */
package com.shinhan.market.exception;

public class CreateFileException extends DataException {
	private static final long serialVersionUID = 1L;
	
	public CreateFileException() {
		
	}
	
	public CreateFileException(String message) {
		super("[CreateFileException] "+message);
	}
	
	public CreateFileException(String message, Exception e) {
		super("[CreateFileException] "+message, e);
	}
}
