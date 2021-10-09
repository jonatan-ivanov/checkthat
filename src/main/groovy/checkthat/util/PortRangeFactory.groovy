package checkthat.util

/**
 * @author Jonatan Ivanov
 */
class PortRangeFactory {
	static Range<Integer> createRange(String range) {
		String[] split = range?.split("\\.\\.");
		if (split?.length == 1) {
			return createRange(split[0], split[0]);
		}
		else if (split?.length == 2) {
			return createRange(split[0], split[1]);
		}
		else {
			throw new IllegalArgumentException("Invalid range: $range");
		}
	}

	static Range<Integer> createRange(String from, String to) {
		try {
			return from?.toInteger()..to?.toInteger();
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid range: $from..$to", e);
		}
	}
}
