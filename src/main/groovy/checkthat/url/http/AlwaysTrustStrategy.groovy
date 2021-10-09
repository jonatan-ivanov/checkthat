package checkthat.url.http

import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import org.apache.http.conn.ssl.TrustStrategy

/**
 * @author Jonatan Ivanov
 */
class AlwaysTrustStrategy implements TrustStrategy  {
	@Override
	boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		return true;
	}
}
