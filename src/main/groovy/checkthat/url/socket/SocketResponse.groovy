package checkthat.url.socket

import checkthat.url.UrlResponse
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class SocketResponse implements UrlResponse {
	String host;
	int port;
	boolean connected;
}
