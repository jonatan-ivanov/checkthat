package checkthat.url.http

import groovy.transform.ToString

@ToString(includeNames = true)
class HttpResponse {
    int responseCode;
    String responseMessage;
}
