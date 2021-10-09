package checkthat.ping

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class PingResult {
	String host;
	int exitCode;
	String stdOut;
	String stdErr;
}
