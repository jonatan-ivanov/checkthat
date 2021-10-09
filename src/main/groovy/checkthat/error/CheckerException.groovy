package checkthat.error

/**
 * @author Jonatan Ivanov
 */
class CheckerException extends Exception {
	CheckerException(String message, Throwable cause) {
		super(message, cause);
	}
}
