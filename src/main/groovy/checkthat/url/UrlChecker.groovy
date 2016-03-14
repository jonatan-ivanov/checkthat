package checkthat.url

import checkthat.url.http.HttpClient
import checkthat.url.http.HttpResponse
import org.springframework.stereotype.Component

import java.util.function.Function

@Component
class UrlChecker implements Function<String,HttpResponse> {
    @Override
    HttpResponse apply(String url) {
        if (url.startsWith("http")) {
            return HttpClient.invoke(url);
        }
        else {
            throw new IllegalArgumentException("Only HTTP or HTTPS are supported");
        }
    }
}
