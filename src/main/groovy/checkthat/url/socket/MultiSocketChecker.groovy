package checkthat.url.socket

import java.util.function.BiFunction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author Jonatan Ivanov
 */
@Component
class MultiSocketChecker implements BiFunction<String, Range<Integer>, List<SocketResponse>> {
	@Autowired private BiFunction<String, Integer, SocketResponse> socketChecker;

	@Override
	List<SocketResponse> apply(String host, Range<Integer> portRange) {
		List<SocketResponse> socketResponses = [];
		Exception lastException = null;

		for (int port in portRange) {
			try {
				socketResponses.add(socketChecker.apply(host, port));
			}
			catch (Exception e) {
				lastException = e;
			}
		}

		if (!socketResponses.isEmpty()) {
			return socketResponses;
		}
		else {
			throw lastException;
		}
	}
}

