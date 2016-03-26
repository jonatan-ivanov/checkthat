package checkthat.controller

import checkthat.ping.PingResult
import checkthat.url.http.HttpResponse
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import java.util.function.Function

/**
 * @author Jonatan Ivanov
 */
@Controller
class CheckerController {
    @Autowired private Function<String,HttpResponse> urlChecker;
    @Autowired private Function<String,PingResult> pingChecker;

    @RequestMapping(path = "/")
    @ResponseBody def check(
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String server) {
        if (url != null) {
            return urlChecker.apply(url);
        }
        else if (server != null) {
            return pingChecker.apply(server);
        }
        else {
            throw new IllegalArgumentException("You need to specify either the url to invoke or the server to ping");
        }
    }

    @RequestMapping(path = "/url")
    @ResponseBody PingResult checkUrl(@RequestParam String url) {
        return urlChecker.apply(url);
    }

    @RequestMapping(path = "/server")
    @ResponseBody PingResult checkServer(@RequestParam String server) {
        return pingChecker.apply(server)
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody ErrorDetails handleError(Throwable error) {
        Throwable rootCause = ExceptionUtils.getRootCause(error);
        return new ErrorDetails(
                errorMessage: error?.getMessage(),
                errorClass: error?.getClass(),
                rootCauseMessage: rootCause?.getMessage(),
                rootCauseClass: rootCause?.getClass()
        );
    }
}
