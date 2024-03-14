package com.example.springtest1.homework.task2;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RequestSender
{
    private HttpClient httpClient;
    private static final String SCHEME = "https://";
    private static final String URI = "api.openweathermap.org/data/2.5/weather";
    private static final String QUERY_PARAM = "q=%s&appid=%s";
    //private static final String SUFFIX_API_SEARCH_BY_CITY = "/search/cities/?lang=%s&query=%s";
    private static final String API_KEY = "e6dd21c2d1c338f0ce8fe8b73419a7a9";
    private static final String CITY_PARAM = "q";
    private static final String APPID_PARAM = "appid";
    //private static final String LANG = "ru";

    public RequestSender()
    {
        httpClient = HttpClient.newHttpClient();
    }

    public String getInfoByCity(String city) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        String validURI = String.format("%s%s?%s=%s&%s=%s",SCHEME,URI,CITY_PARAM,URLEncoder.encode(city, StandardCharsets.UTF_8),APPID_PARAM,URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(validURI))
                //.header("X-Gismeteo-Token","56b30cb255.3443075")
                //.header("Accept-Encoding","deflate")
                .GET()
                .build();
        Map<String, List<String>> headers = request.headers().map();
        CompletableFuture<HttpResponse<String>> response =  httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> completeResponse = response.get();
        return completeResponse.body();
    }

    private WeatherDto toWeatherDto(HttpResponse<String> completeResponse)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new WeatherDto();
    }
}
