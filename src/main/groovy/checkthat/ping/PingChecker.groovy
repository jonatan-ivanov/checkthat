package checkthat.ping

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.util.function.Function

/**
 * @author Jonatan Ivanov
 */
@Component
class PingChecker implements Function<String,PingResult> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingChecker.class);

    @Override
    PingResult apply(String host) {
        LOGGER.info("Pinging server: $host");
        if (seemsValidHost(host)) {
            Process pingProcess = createCommand(System.getProperty("os.name"), host).execute();
            pingProcess.waitFor();

            return new PingResult(
                    exitCode: pingProcess.exitValue(),
                    stdOut: pingProcess.inputStream.text,
                    stdErr: pingProcess.errorStream.text
            );
        }
        else {
            throw new IllegalArgumentException("This does not seem to be a valid hostname");
        }
    }

    private static String createCommand(String osName, String host) {
        if (osName.startsWith("Windows")) {
            return "ping -n 1 $host";
        }
        else {
            return "ping -c 1 $host";
        }
    }

    private static boolean seemsValidHost(String host) {
        return host ==~ "[a-zA-Z0-9\\.:-]+" //almost :)
    }
}
