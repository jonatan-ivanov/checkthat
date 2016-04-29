package checkthat

import checkthat.filter.Base64UrlEncodingFilter
import checkthat.url.http.AlwaysTrustStrategy
import com.google.common.base.Predicates
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
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import javax.net.ssl.SSLContext
import javax.servlet.DispatcherType

/**
 * @author Jonatan Ivanov
 */
@SpringBootApplication
@EnableSwagger2
class Application {
    @Autowired private Environment env;

    static void main(String[] args) {
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        SpringApplication.run(Application, args);
    }

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
        filterRegistrationBean.setFilter(new Base64UrlEncodingFilter("/url"));
        filterRegistrationBean.addUrlPatterns("/url/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);

        return filterRegistrationBean;
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SPRING_WEB)
                .groupName("checkthat")
                .apiInfo(getApiInfo())
                .select().paths(Predicates.not(PathSelectors.regex("/error")))
                .build();
    }

    private static ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Checkthat REST API Doc")
                .build();
    }
}
