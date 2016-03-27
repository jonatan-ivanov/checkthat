package checkthat.url.socket

import checkthat.error.CheckerException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.util.function.BiFunction

/**
 * @author Jonatan Ivanov
 */
@Component
class SocketChecker implements BiFunction<String, Integer, SocketResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketChecker.class);

    @Override
    SocketResponse apply(String host, Integer port) {
        LOGGER.info("Connecting to $host:$port");

        Socket socket = null;
        try {
            socket = new Socket(host, port);
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
