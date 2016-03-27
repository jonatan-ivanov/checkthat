package checkthat.controller

import checkthat.error.ErrorDetails
import checkthat.ping.PingResult
import checkthat.url.UrlResponse
import checkthat.url.socket.SocketResponse
import checkthat.util.PortRangeFactory
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import java.util.function.BiFunction
import java.util.function.Function

/**
 * @author Jonatan Ivanov
 */
@Controller
class CheckerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckerController.class);

    @Autowired private Function<String, UrlResponse> urlChecker;
    @Autowired private Function<String, PingResult> pingChecker;
    @Autowired private BiFunction<String, Range<Integer>, List<SocketResponse>> multiSocketChecker;

    @RequestMapping(path = "/url")
    @ResponseBody UrlResponse checkUrl(@RequestParam String url) {
        return urlChecker.apply(url);
    }

    @RequestMapping(path = "/server/{server:.+}")
    @ResponseBody PingResult checkServer(@PathVariable String server) {
        return pingChecker.apply(server);
    }

    @RequestMapping(path = "/socket/{host}:{portRange:.+}")
    @ResponseBody List<SocketResponse> checkPorts(@PathVariable String host, @PathVariable String portRange) {
        return multiSocketChecker.apply(host, PortRangeFactory.createRange(portRange));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody ErrorDetails handleError(Throwable error) {
        LOGGER.error(error.getMessage(), error);
        Throwable cause = error?.cause;
        Throwable rootCause = ExceptionUtils.getRootCause(error);

        return new ErrorDetails(
                errorMessage: error?.getMessage(),
                errorClass: error?.getClass(),
                causeClass: cause?.getClass(),
                causeMessage: cause?.getMessage(),
                rootCauseMessage: rootCause?.getMessage(),
                rootCauseClass: rootCause?.getClass()
        );
    }
}
