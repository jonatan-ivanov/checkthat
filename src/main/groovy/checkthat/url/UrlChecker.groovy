package checkthat.url

import checkthat.url.http.HttpInvoker
import checkthat.url.http.HttpResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.function.Function

/**
 * @author Jonatan Ivanov
 */
@Component
class UrlChecker implements Function<String,HttpResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlChecker.class);
    @Autowired private HttpInvoker httpInvoker;

    @Override
    HttpResponse apply(String url) {
        LOGGER.info("Checking url: $url");
        if (isSupported(url)) {
            return httpInvoker.invoke(url);
        }
        else {
            throw new IllegalArgumentException("Only HTTP or HTTPS are supported");
        }
    }

    private static boolean isSupported(String url) {
        return url.startsWith("http");
    }
}
