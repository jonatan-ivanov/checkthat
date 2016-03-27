package checkthat

import checkthat.url.http.AlwaysTrustStrategy
import org.apache.http.client.config.RequestConfig
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter

import javax.net.ssl.SSLContext
import javax.servlet.DispatcherType

/**
 * @author Jonatan Ivanov
 */
@SpringBootApplication
class Application {
    @Autowired private Environment env;

    @Bean
    CloseableHttpClient httpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(createRequestConfig())
                .setSSLSocketFactory(createSSLConnectionSocketFactory())
                .build();
    }

    private RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(env.getProperty("http.socketTimeout", Integer.class))
                .setConnectTimeout(env.getProperty("http.connectTimeout", Integer.class))
                .setConnectionRequestTimeout(env.getProperty("http.connectionRequestTimeout", Integer.class))
                .build();
    }

    private static SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, new AlwaysTrustStrategy())
                .build();

        return new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new UrlRewriteFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);

        return filterRegistrationBean;
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args);
    }
}
