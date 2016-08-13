package checkthat.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import java.util.regex.Matcher

/**
 * Since using an url as a {@link org.springframework.web.bind.annotation.PathVariable} does not work due to
 * Spring MVC and Tomcat issues, this class encodes the incoming URL path snippet to a URL and Filename safe Base64 string.
 *
 * @author Jonatan Ivanov
 */
class Base64UrlEncodingFilter implements Filter {
    private String path;

    Base64UrlEncodingFilter(String path) {
        this.path = path;
    }

    @Override
    void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Matcher matcher  = httpRequest.getRequestURI() =~ "$httpRequest.contextPath$path/(.+)";

        if (matcher.find()) {
            String plainText = matcher.group(1);
            if (!plainText.startsWith("base64")) {
                String encoded = Base64.getUrlEncoder().encodeToString(plainText.getBytes());
                httpRequest.getRequestDispatcher("$path/base64$encoded").forward(request, response);
            }
            else {
                chain.doFilter(request, response);
            }
        }
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    void destroy() {}
}
