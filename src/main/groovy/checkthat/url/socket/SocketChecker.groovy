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
    @Override
    SocketResponse apply(String host, Integer port) {
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
