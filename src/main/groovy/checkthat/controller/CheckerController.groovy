package checkthat.controller

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

    @RequestMapping(path = "/")
    @ResponseBody HttpResponse checkUrl(@RequestParam("url") String url) {
        return urlChecker.apply(url);
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
