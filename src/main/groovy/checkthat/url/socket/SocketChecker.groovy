package checkthat.url.socket

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
            return new SocketResponse(
                    host: host,
                    port: port,
                    connected: socket.isConnected()
            );
        }
        finally {
            socket?.close();
        }
    }
}
