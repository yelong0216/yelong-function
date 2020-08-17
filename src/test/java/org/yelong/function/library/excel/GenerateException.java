/**
 * 
 */
package org.yelong.function.library.excel;

/**
 * @author PengFei
 *
 */
public class GenerateException extends Exception {

	private static final long serialVersionUID = 1195693222918948125L;

	public GenerateException(Throwable t) {
		super(t);
	}

	public GenerateException(String message, Throwable t) {
		super(message, t);
	}

}
