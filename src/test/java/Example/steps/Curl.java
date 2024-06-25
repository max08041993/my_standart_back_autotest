package Example.steps;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

public class Curl {
    @Getter
    public enum METHOD {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        PATCH("PATCH");

        private final String method;

        METHOD(String method) {
            this.method = method;
        }
    }

    public static String getCurl(METHOD method, String url, @Nullable Map<String, String> query,@Nullable Map<String, String> headers, @Nullable Map<String, String> cookies, @Nullable String body) {
        final StringBuilder builder = new StringBuilder("curl -v");
        if (Objects.nonNull(method)) {
            builder.append(" -X ").append(method);
        }
        builder.append(" '").append(url);
        if (Objects.nonNull(query)) {
            builder.append("?");
            for (Map.Entry<String, String> entry : query.entrySet()) {
                builder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append('\'');
        if (Objects.nonNull(headers))headers.forEach((key, value) -> appendHeader(builder, key, value));
        if (Objects.nonNull(cookies)) cookies.forEach((key, value) -> appendCookie(builder, key, value));

        if (Objects.nonNull(body)) {
            body = body.replace("\n", "");
            body = body.replaceAll("\\s+", " ");
            builder.append(" -d '").append(body).append('\'');
        }
        System.out.println(builder);
        return builder.toString();
    }

    private static void appendHeader(final StringBuilder builder, final String key, final String value) {
        builder.append(" -H '")
                .append(key)
                .append(": ")
                .append(value)
                .append('\'');
    }

    private static void appendCookie(final StringBuilder builder, final String key, final String value) {
        builder.append(" -b '")
                .append(key)
                .append('=')
                .append(value)
                .append('\'');
    }

}
