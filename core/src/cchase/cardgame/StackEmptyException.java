package cchase.cardgame;

/**
 * <p>
 * Title: StackEmptyException.java
 * </p>
 * 
 * <p>
 * Description: Returns an exception if the stack is empty
 * </p>
 * 
 * @author Christopher Chase
 */

public class StackEmptyException extends RuntimeException {
	/**
	 * Constructs a new StackEmptyException with a default error message string.
	 */
	public StackEmptyException(){
		super("Exception : Stack is empty");
	}
	/**
	 * Constructs a new StackEmptyException with the parameter as the error message string.
	 * @param msg The string passed as the error message string.
	 */
	public StackEmptyException(String msg){
		super(msg);
	}
}
