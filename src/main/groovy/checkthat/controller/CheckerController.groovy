package checkthat.controller

import java.util.function.BiFunction
import java.util.function.Function

import checkthat.error.ErrorDetails
import checkthat.ping.PingResult
import checkthat.url.UrlResponse
import checkthat.url.socket.SocketResponse
import checkthat.util.PortRangeFactory
import io.swagger.v3.oas.annotations.Hidden
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

/**
 * @author Jonatan Ivanov
 */
@RestController
class CheckerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckerController.class);

	@Autowired private Function<String, UrlResponse> urlChecker;
	@Autowired private Function<String, PingResult> pingChecker;
	@Autowired private BiFunction<String, Integer, SocketResponse> socketChecker;
	@Autowired private BiFunction<String, Range<Integer>, List<SocketResponse>> multiSocketChecker;

	@GetMapping(path = "/url/{url:.+}")
	UrlResponse checkUrl(@PathVariable String url) {
		String encodedUrl = url.minus("base64");
		String base64DecodedUrl = new String(Base64.getUrlDecoder().decode(encodedUrl));

		return urlChecker.apply(URLDecoder.decode(base64DecodedUrl, "UTF-8"));
	}

	@GetMapping(path = "/server/{server:.+}")
	PingResult checkServer(@PathVariable String server) {
		return pingChecker.apply(server);
	}

	@GetMapping(path = "/socket/{host}:{port:.+}")
	SocketResponse checkPort(@PathVariable String host, @PathVariable Integer port) {
		return socketChecker.apply(host, port);
	}

	@GetMapping(path = "/sockets/{host}:{portRange:.+}")
	List<SocketResponse> checkPorts(@PathVariable String host, @PathVariable String portRange) {
		return multiSocketChecker.apply(host, PortRangeFactory.createRange(portRange));
	}

	@Hidden
	@GetMapping("/")
	RedirectView redirectWithUsingRedirectView() {
		return new RedirectView("/swagger-ui.html");
	}

	@ExceptionHandler(Throwable.class)
	ErrorDetails handleError(Throwable error) {
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
