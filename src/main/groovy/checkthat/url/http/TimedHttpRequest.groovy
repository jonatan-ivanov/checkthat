package checkthat.url.http

import checkthat.error.CheckerException
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Spencer Thomas
 */
class TimedHttpRequest implements Callable<HttpResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedHttpRequest.class)
    
    private final String url
    private final int timeoutMs
    private final CloseableHttpClient httpClient

    TimedHttpRequest(String url, int timeoutMs, CloseableHttpClient httpClient) {
        this.url = url
        this.timeoutMs = timeoutMs
        this.httpClient = httpClient;
    }

    HttpResponse invoke() {
        try {
            return Executors.newSingleThreadExecutor().submit(this).get(timeoutMs, TimeUnit.MILLISECONDS)
        }
        catch (Exception e) {
            if (e instanceof ExecutionException) {
                throw e
            }
            throw new CheckerException("Error occured while invoking $url", e)
        }
    }

    @Override
    HttpResponse call() throws Exception {
        LOGGER.info("Invoking $url")
        CloseableHttpResponse response = null
        try {
            response = httpClient.execute(new HttpGet(url))
            StatusLine status = response.getStatusLine()
            LOGGER.info("Successfully invoked $url")

            return new HttpResponse(
                    url: url,
                    method: "GET",
                    statusCode: status?.getStatusCode(),
                    statusMessage: status?.getReasonPhrase()
            )
        }
        catch (Exception e) {
            throw new CheckerException("Error occured while invoking $url", e)
        }
        finally {
            response?.close()
        }
    }
}