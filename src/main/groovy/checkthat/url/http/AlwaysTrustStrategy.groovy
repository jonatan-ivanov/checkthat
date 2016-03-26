package checkthat.url.http

import org.apache.http.conn.ssl.TrustStrategy

import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * @author Jonatan Ivanov
 */
class AlwaysTrustStrategy implements TrustStrategy  {
    @Override
    boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return true;
    }
}
