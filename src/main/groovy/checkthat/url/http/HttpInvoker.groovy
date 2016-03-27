package checkthat.url.http

import checkthat.error.CheckerException
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author Jonatan Ivanov
 */
@Component
class HttpInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpInvoker.class);
    @Autowired private CloseableHttpClient httpClient;

    HttpResponse invoke(String url) {
        LOGGER.info("Invoking $url");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet(url));
            StatusLine status = response.getStatusLine();
            LOGGER.info("Successfully invoked $url");

            return new HttpResponse(
                    url: url,
                    method: "GET",
                    statusCode: status?.getStatusCode(),
                    statusMessage: status?.getReasonPhrase()
            );
        }
        catch (Exception e) {
            throw new CheckerException("Error occured while invoking $url", e);
        }
        finally {
            response?.close();
        }
    }
}
