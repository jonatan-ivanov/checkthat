package checkthat.url.socket

import java.util.function.BiFunction

import checkthat.error.CheckerException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * @author Jonatan Ivanov
 */
@Component
class SocketChecker implements BiFunction<String, Integer, SocketResponse> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketChecker.class);

	@Value('${socket.timeout}') private int socketTimeout;

	@Override
	SocketResponse apply(String host, Integer port) {
		LOGGER.info("Connecting to $host:$port");

		Socket socket = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), socketTimeout);
			LOGGER.info("Successfully connected to $host:$port");
			return new SocketResponse(
					host: host,
					port: port,
					connected: socket.isConnected()
			);
		}
		catch (Exception e) {
			throw new CheckerException("Error occured while conncting to $host:$port", e);
		}
		finally {
			socket?.close();
		}
	}
}
