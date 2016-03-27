package checkthat.controller

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class ErrorDetails {
    Class errorClass;
    String errorMessage;
    Class rootCauseClass;
    String rootCauseMessage;
}
