package Example.steps;

import Example.steps.SaveData.SaveData;
import com.github.javafaker.Faker;
import com.ibm.icu.impl.Assert;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;

import javax.annotation.Nullable;
import java.util.*;

import static io.restassured.RestAssured.config;
import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class BaseRestApi extends SaveData {

    public static final Logger logger = LogManager.getLogger();

    public static final Faker fakerRU = new Faker(new Locale("ru"));

    public static final Faker fakerEN = new Faker();

    private static final Map<String, String> header = Map.of("Accept", "application/json, text/plain, */*; q=0.01", "Content-Type", "application/json; charset=UTF-8");


    /**
     * Метод GET API
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response get(@Nullable Map<String, String> queryParams,
                               @Nullable Map<String, String> headers,
                               @Nullable String body,
                               String urlSwagger,
                               String api,
                               @Nullable String apiAdditional) {
//        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(Options.builder().alwaysPrintMethod().targetPlatform(Platform.UNIX).useLogLevel(Level.INFO).build());
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000))) // Ограничение времени ответа от запроса
                .build();
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.GET,urlSwagger + api,queryParams,headers,getCookie(),body);
        Response response = SerenityRest.given().config(config)
                .filter(new AllureRestAssured())
                .spec(spec).get(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response get(@Nullable Map<String, String> queryParams,
                               @Nullable Map<String, String> headers,
                               @Nullable Map<String, String> cookies,
                               @Nullable String body,
                               String urlSwagger,
                               String api,
                               @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.GET,urlSwagger + api,queryParams,headers,getCookie(),body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).get(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Метод POST API
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response post(@Nullable Map<String, String> queryParams,
                                @Nullable Map<String, String> headers,
                                @Nullable String body,
                                String urlSwagger,
                                String api,
                                @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::queryParam);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.POST,urlSwagger + api,queryParams,headers,null,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).post(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Метод POST API в который передаю новые куки
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response post(@Nullable Map<String, String> queryParams,
                                @Nullable Map<String, String> headers,
                                @Nullable Map<String, String> cookies,
                                @Nullable String body,
                                String urlSwagger,
                                String api,
                                @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::queryParam);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.POST,urlSwagger + api,queryParams,headers,cookies,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).post(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Метод POST API в который передаю новые куки и путь к файлу!!!
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response post(@Nullable Map<String, String> queryParams,
                                @Nullable Map<String, String> headers,
                                @Nullable Map<String, String> cookies,
                                @Nullable Map<String, Object> body,
                                String urlSwagger,
                                String api,
                                @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                spec.multiPart(key, value);
            }
        }
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.POST,urlSwagger + api,queryParams,headers,cookies,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).post(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Метод PUT API
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response put(@Nullable Map<String, String> queryParams,
                               @Nullable Map<String, String> headers,
                               @Nullable String body,
                               String urlSwagger,
                               String api,
                               @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::queryParam);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).put(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
//      spec.given().log().all();
//        getCurl(Curl.METHOD.PUT, urlSwagger + api, queryParams, headers, null, body);
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response put(@Nullable Map<String, String> queryParams,
                               @Nullable Map<String, String> headers,
                               @Nullable Map<String, String> cookies,
                               @Nullable String body,
                               String urlSwagger,
                               String api,
                               @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::queryParam);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.PUT,urlSwagger + api,queryParams,headers,cookies,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).put(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response delete(@Nullable Map<String, String> queryParams,
                                  @Nullable Map<String, String> headers,
                                  @Nullable String body,
                                  String urlSwagger,
                                  String api,
                                  @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.contentType("application/json").body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).delete(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
//      spec.given().log().all();
//        getCurl(Curl.METHOD.DELETE,urlSwagger + api,queryParams,headers, null,body);
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response delete(@Nullable Map<String, String> queryParams,
                                  @Nullable Map<String, String> headers,
                                  @Nullable Map<String, String> cookies,
                                  @Nullable String body,
                                  String urlSwagger,
                                  String api,
                                  @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//      spec.given().log().all();
//        getCurl(Curl.METHOD.DELETE,urlSwagger + api,queryParams,headers,cookies,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).delete(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Отправка файла (аватара или чего-то ещё). Где body это Map
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response patch(@Nullable Map<String, String> queryParams,
                                 @Nullable Map<String, String> headers,
                                 @Nullable Map<String, Object> body,
                                 String urlSwagger,
                                 String api,
                                 @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                spec.multiPart(key, value);
            }
        }
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.PATCH,urlSwagger + api,queryParams,headers, null,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).patch(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * Отправка файла (аватара или чего-то ещё). Где body это Map + cookies
     *
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response patch(@Nullable Map<String, String> queryParams,
                                 @Nullable Map<String, String> headers,
                                 @Nullable Map<String, String> cookies,
                                 @Nullable Map<String, Object> body,
                                 String urlSwagger,
                                 String api,
                                 @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                spec.multiPart(key, value);
            }
        }
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//        spec.given().log().all();
//        getCurl(Curl.METHOD.PATCH,urlSwagger + api,queryParams,headers,cookies,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).patch(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response patch(@Nullable Map<String, String> queryParams,
                                 @Nullable Map<String, String> headers,
                                 @Nullable String body,
                                 String urlSwagger,
                                 String api,
                                 @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))
                .build();
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//      spec.given().log().all();
//        getCurl(Curl.METHOD.PATCH,urlSwagger + api,queryParams,headers, null,body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).patch(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    /**
     * @param queryParams   Мапа параметров
     * @param headers       Заголовки
     * @param cookies       Мапа Куки в запросе
     * @param body          Тело запроса
     * @param urlSwagger    Базовый URL
     * @param api           url запроса
     * @param apiAdditional API Дополнительный добавляется в конце url запроса
     * @return Response ответ
     */
    public static Response patch(@Nullable Map<String, String> queryParams,
                                 @Nullable Map<String, String> headers,
                                 @Nullable Map<String, String> cookies,
                                 @Nullable String body,
                                 String urlSwagger,
                                 String api,
                                 @Nullable String apiAdditional) {
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(urlSwagger)
                .setConfig(RestAssured.config().httpClient(httpClientConfig().setParam("http.connection.timeout", 30000)))// Ограничение времени ответа от запроса
                .build();
        if (Objects.nonNull(cookies)) spec.cookies(cookies);
        if (Objects.nonNull(queryParams)) queryParams.forEach(spec::param);
        header.forEach(spec::header);
        if (Objects.nonNull(headers)) headers.forEach(spec::header);
        if (Objects.nonNull(body)) spec.body(body);
        if (Objects.nonNull(apiAdditional)) {
            api = api.replaceAll("/*$", "");
            api += "/" + apiAdditional;
        }
//      spec.given().log().all();
//        getCurl(Curl.METHOD.PATCH, urlSwagger + api, queryParams, headers, cookies, body);
        Response response = SerenityRest.given()
                .filter(new AllureRestAssured())
                .spec(spec).patch(api);
        Allure.addAttachment("Время ответа ручки " + api, response.getTime() + "ms");
        return response;
    }

    public void assertResponseData(ValidatableResponse validatableResponse, List<Map<String, String>> allData) {
        try {
            validatableResponse.extract().body().asString();
        } catch (ClassCastException e) {
            org.junit.Assert.fail("ValidatableResponse ответа пустой, не с чем сравнивать поля");
        }
        List<Object> assertions = new ArrayList<>();
        List<String> assertPath = new ArrayList<>();
        for (Map<String, String> data : allData) {
//            System.out.println(data);
            if (data.get("Value")
                    .equals("!null")) {
                assertions.add(data.get("Path"));
                assertions.add(notNullValue());
                continue;
            }
            if (data.get("Value")
                    .equals("null")) {
                assertions.add(data.get("Path"));
                assertions.add(equalTo(null));
                continue;
            }
            if (data.get("Value")
                    .equals("present")) {
                assertPath.add(data.get("Path"));
                continue;
            }
            if (data.get("Value")
                    .startsWith("is:")) {
                assertions.add(data.get("Path"));
                assertions.add(getClassMatcher(data.get("Value")));
                continue;
            }
            assertions.add(data.get("Path"));
            assertions.add(equalTo(convert(data.get("Value"))));
        }
        try {
            validatableResponse
                    .body("", notNullValue(), assertions.toArray());
        } catch (IllegalArgumentException illegalArgumentException) {
            Assertions.fail("Возникла ошибка при обращении по пути к проверяемому объекту, " +
                    "следует проверить, что путь Path указан верно !!! \n" + illegalArgumentException);
        }
        if (!assertPath.isEmpty()) {
            for (String value : assertPath) {
                if (value.contains(".")) {
                    validatableResponse.body(value.replaceAll(".[^.]+$", ""), hasKey(value.replaceAll("^.*\\.", "")));
                } else {
                    validatableResponse.body("", hasKey(value));
                }
            }
        }
    }

    private Matcher<?> getClassMatcher(String value) {
        Assertions.assertThat(value)
                .as("Не верный формат значения (должен начинаться с 'is:'): " + value)
                .startsWith("is:");
        value = value.replace("is:", "");
        switch (value) {
            case "A" -> {
                try {
                    return instanceOf(ArrayList.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "I" -> {
                try {
                    return instanceOf(Integer.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "F" -> {
                try {
                    return instanceOf(Float.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "S" -> {
                try {
                    return instanceOf(String.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "B" -> {
                try {
                    return instanceOf(Boolean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "L" -> {
                try {
                    return instanceOf(Long.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "not000" -> {
                try {
                    return not("00000000-0000-0000-0000-000000000000");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Assert.fail("Указан не верный тип для значения " + value);
        return null;
    }

    protected Object convert(String value) {
        if (value.startsWith("I:")) {
            return Integer.parseInt(value.replaceAll("^I:", ""));
        }
        if (value.startsWith("F:")) {
            return Float.parseFloat(value.replaceAll("^F:", ""));
        }
        if (value.startsWith("B:")) {
            return Boolean.parseBoolean(value.replaceAll("^B:", ""));
        }
        if (value.startsWith("L:")) {
            return Long.parseLong(value.replaceAll("^L:", ""));
        }
        if (value.startsWith("S:")) {
//            if (value.contains("%email")) {
//                value = value.replaceAll("%email", getEmail());
//            }
            return value.replaceAll("^S:", "");
        }
        Assert.fail("Указан не верный тип для значения " + value);
        return null;
    }

    public static void assertStepEquals(Object valueBd, Object valueResponse) {
        org.junit.Assert.assertFalse("Объект проверки из БД NULL", Objects.isNull(valueBd));
        org.junit.Assert.assertFalse("Объект проверки из Ответа IS NULL", Objects.isNull(valueResponse));
        org.junit.Assert.assertEquals("Значение в БД '" + valueBd + "' не совпадает с ожидаемым: '" + valueResponse
                + "'\n", valueBd.toString(), valueResponse.toString());
    }

    public static void assertStepContains(Object valueBd, Object valueResponse) {
        try {
            org.junit.Assert.assertTrue(
                    "Значение '" + valueBd + "' в БД, не найден в ответе: '" + valueResponse + "'\n", valueBd.toString()
                            .toUpperCase()
                            .contains(valueResponse.toString()
                                    .toUpperCase()));
        } catch (AssertionError err) {
            org.junit.Assert.assertTrue("Значение '" + valueBd + "' в БД, не найден в ответе: '" + valueResponse
                    + "'\n", valueResponse.toString()
                    .toUpperCase()
                    .contains(valueBd.toString()
                            .toUpperCase()));
        }
    }

    public static void assertStepNull(String valueBd) {
        org.junit.Assert.assertNull("Значение '" + valueBd + "' не совпадает с ожидаемым\n", valueBd);
    }

    public static void assertStepNotNull(String valueBd) {
        org.junit.Assert.assertNotNull("Значение '" + valueBd + "' не совпадает с ожидаемым\n", valueBd);
    }

    public static void assertStepEquals(String name, Object valueBd, Object valueExpected) {
        org.junit.Assert.assertFalse("Объект проверки из БД NULL", Objects.isNull(valueBd));
        org.junit.Assert.assertFalse("Объект проверки из Ответа IS NULL", Objects.isNull(valueExpected));
        org.junit.Assert.assertEquals(
                "Значение столбца '" + name + "':'" + valueBd + "' в БД, не совпадает с ожидаемым: '" + valueExpected
                        + "'\n", valueBd.toString(), valueExpected.toString());
    }

    public static void assertStepNotEquals(String name, Object valueBd, Object valueResponse) {
        org.junit.Assert.assertNotEquals(
                "Значение столбца '" + name + "':'" + valueBd + "' в БД, совпадает с ожидаемым: '" + valueResponse
                        + "'\n", valueBd.toString(), valueResponse.toString());
    }

    public static void assertStepContains(String name, Object valueBd, Object valueResponse) {
        try {
            org.junit.Assert.assertTrue(
                    "Значение столбца '" + name + "':'" + valueBd + "' в БД, не найден в ответе: '" + valueResponse
                            + "'\n", valueBd.toString()
                            .toUpperCase()
                            .contains(valueResponse.toString()
                                    .toUpperCase()));
        } catch (AssertionError err) {
            org.junit.Assert.assertTrue(
                    "Значение столбца '" + name + "':'" + valueBd + "' в БД, не найден в ответе: '" + valueResponse
                            + "'\n", valueResponse.toString()
                            .toUpperCase()
                            .contains(valueBd.toString()
                                    .toUpperCase()));
        }
    }

    public static void assertStepNull(String name, String valueResponse) {
        org.junit.Assert.assertNull("Ключ-значение '" + name + "':''" + valueResponse
                + "' в ответе, не совпадает с ожидаемым\n", valueResponse);
    }

    public static void assertStepNotNull(String name, String valueResponse) {
        org.junit.Assert.assertNotNull("Ключ-значение '" + name + "':''" + valueResponse
                + "' в ответе, не совпадает с ожидаемым\n", valueResponse);
    }

}
