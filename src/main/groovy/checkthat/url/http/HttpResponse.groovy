package checkthat.url.http

import checkthat.url.UrlResponse
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class HttpResponse implements UrlResponse {
	String url;
	String method;
	int statusCode;
	String statusMessage;
}
