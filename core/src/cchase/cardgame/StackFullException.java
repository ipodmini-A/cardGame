package cchase.cardgame;

/**
 * <p>
 * Title: StackFullException.java
 * </p>
 * 
 * <p>
 * Description: Returns an exception if the stack is full
 * </p>
 * 
 * @author Christopher Chase
 */

public class StackFullException extends RuntimeException {
	/**
	 * Constructs a new StackFullException with a default error message string.
	 */
	public StackFullException(){
		super("Exception : Stack is full");
	}
	/**
	 * Constructs a new StackFullException with the parameter as the error message string.
	 * @param msg The string passed as the error message string.
	 */
	public StackFullException(String msg){
		super(msg);
	}
}
