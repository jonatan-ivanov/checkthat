package checkthat.url.http

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class HttpResponse {
    int statusCode;
    String statusMessage;
}
