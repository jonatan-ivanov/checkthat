package checkthat.url.http

import org.apache.http.impl.client.CloseableHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


/**
 * @author Jonatan Ivanov
 */
@Component
class HttpInvoker {
    @Autowired private CloseableHttpClient httpClient
    @Value('${http.executionTimeout}') private int httpRequestTimeout

    HttpResponse invoke(String url) {
        return new TimedHttpRequest(url, httpRequestTimeout, httpClient).invoke()
    }
}
