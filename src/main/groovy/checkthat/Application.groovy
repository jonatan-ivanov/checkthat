package checkthat

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment

/**
 * @author Jonatan Ivanov
 */
@SpringBootApplication
class Application {
    @Autowired private Environment env;

    @Bean
    CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(env.getProperty("http.socketTimeout", Integer.class))
                .setConnectTimeout(env.getProperty("http.connectTimeout", Integer.class))
                .setConnectionRequestTimeout(env.getProperty("http.connectionRequestTimeout", Integer.class))
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args);
    }
}
