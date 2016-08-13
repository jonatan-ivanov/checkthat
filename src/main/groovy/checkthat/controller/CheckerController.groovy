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
import org.springframework.web.bind.annotation.ResponseBody

import java.util.function.BiFunction
import java.util.function.Function

import static org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * @author Jonatan Ivanov
 */
@Controller
class CheckerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckerController.class);

    @Autowired private Function<String, UrlResponse> urlChecker;
    @Autowired private Function<String, PingResult> pingChecker;
    @Autowired private BiFunction<String, Integer, SocketResponse> socketChecker;
    @Autowired private BiFunction<String, Range<Integer>, List<SocketResponse>> multiSocketChecker;

    @RequestMapping(path = "/url/{url:.+}", method = GET)
    @ResponseBody UrlResponse checkUrl(@PathVariable String url) {
        String encodedUrl = url.minus("base64");
        String base64DecodedUrl = new String(Base64.getUrlDecoder().decode(encodedUrl));

        return urlChecker.apply(URLDecoder.decode(base64DecodedUrl, "UTF-8"));
    }

    @RequestMapping(path = "/server/{server:.+}", method = GET)
    @ResponseBody PingResult checkServer(@PathVariable String server) {
        return pingChecker.apply(server);
    }

    @RequestMapping(path = "/socket/{host}:{port:.+}", method = GET)
    @ResponseBody SocketResponse checkPort(@PathVariable String host, @PathVariable Integer port) {
        return socketChecker.apply(host, port);
    }

    @RequestMapping(path = "/sockets/{host}:{portRange:.+}", method = GET)
    @ResponseBody List<SocketResponse> checkPorts(@PathVariable String host, @PathVariable String portRange) {
        return multiSocketChecker.apply(host, PortRangeFactory.createRange(portRange));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody ErrorDetails handleError(Throwable error) {
        LOGGER.error(error?.getMessage(), error);
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
