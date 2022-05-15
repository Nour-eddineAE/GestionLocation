package exceptions;

public class InvalidDate extends Exception{
	private static final long serialVersionUID = 1L;
	
	public InvalidDate(String msg) {
		super(msg);
	}
}
