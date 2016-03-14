package checkthat.url.http

class HttpClient {
    static HttpResponse invoke(String urlString) {
        HttpURLConnection conn = (HttpURLConnection) urlString.toURL().openConnection();
        return new HttpResponse(responseCode: conn.responseCode, responseMessage: conn.responseMessage);
    }
}
