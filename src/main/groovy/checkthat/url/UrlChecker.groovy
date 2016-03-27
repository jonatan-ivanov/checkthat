package checkthat.url

import checkthat.url.http.HttpInvoker
import checkthat.url.socket.SocketResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.function.BiFunction
import java.util.function.Function
/**
 * @author Jonatan Ivanov
 */
@Component
class UrlChecker implements Function<String, UrlResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlChecker.class);

    @Autowired private HttpInvoker httpInvoker;
    @Autowired private BiFunction<String, Integer, SocketResponse> socketChecker;

    @Override
    UrlResponse apply(String url) {
        LOGGER.info("Checking url: $url");
        if (isHttp(url)) {
            return httpInvoker.invoke(url);
        }
        else {
            return checkSocket(url);
        }
    }

    private SocketResponse checkSocket(String url) {
        String protocol = getProtocol(url);
        String host = getHost(url);
        int port = getPort(url);

        if (protocol != null && host != null && port > 0) {
            return socketChecker.apply(host, port);
        }
        else {
            throw new IllegalArgumentException(
                    "Unsupported URL: $url, please use http(s) or socket://host:port to check any port");
        }
    }

    private static boolean isHttp(String url) {
        return url.startsWith("http");
    }

    private static String getProtocol(String url) {
        return url.toURI().getScheme();
    }

    private static String getHost(String url) {
        return url.toURI().getHost();
    }

    private static int getPort(String url) {
        return url.toURI().getPort();
    }
}
