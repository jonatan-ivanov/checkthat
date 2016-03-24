package checkthat.controller

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * @author Jonatan Ivanov
 */
@ToString(includeNames = true)
@Immutable
class ErrorDetails {
    String errorMessage;
    Class errorClass;
    String rootCauseMessage;
    Class rootCauseClass;
}
