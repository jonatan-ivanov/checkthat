package checkthat.url.http

import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author Jonatan Ivanov
 */
@Component
class HttpInvoker {
    @Autowired private CloseableHttpClient httpClient;

    HttpResponse invoke(String url) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            StatusLine status = response.getStatusLine();
            return new HttpResponse(
                    url: url,
                    method: "GET",
                    statusCode: status?.getStatusCode(),
                    statusMessage: status?.getReasonPhrase()
            );
        }
        finally {
            response?.close();
        }
    }
}
