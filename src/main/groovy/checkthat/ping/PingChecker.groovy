package checkthat.ping

import java.util.function.Function

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component

/**
 * @author Jonatan Ivanov
 */
@Component
class PingChecker implements Function<String, PingResult> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PingChecker.class);
	private static final String OS_NAME = System.getProperty("os.name");

	@Override
	PingResult apply(String host) {
		LOGGER.info("Pinging $host");
		if (seemsValidHost(host)) {
			Process pingProcess = createCommand(OS_NAME, host).execute();
			pingProcess.waitFor();

			return new PingResult(
					host: host,
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
		return host ==~ "[a-zA-Z0-9\\.:-]+"; //almost :)
	}
}
