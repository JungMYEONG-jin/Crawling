/**
 * @author ±èµ¿Çå
 *
 * 2012.09.09
 */
package com.shinhan.market.exception;

public class CrawlingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CrawlingException() {
		
	}
	
	public CrawlingException(String message) {
		super("[CrawlingException] "+message);
	}
	
	public CrawlingException(String message, Exception e) {
		super("[CrawlingException] "+message, e);
	}
}
