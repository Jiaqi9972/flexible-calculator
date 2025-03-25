package org.ebay.flexiblecalculator.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.dto.response.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global response handler that wraps all controller responses in a standard Response object
 * while ensuring Swagger/OpenAPI endpoints are not affected
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Don't wrap if the response is already a Response object
        if (returnType.getParameterType().equals(Response.class)) {
            return false;
        }

        // Skip for classes with RestControllerAdvice annotation (avoid recursive processing)
        if (returnType.getContainingClass().isAnnotationPresent(RestControllerAdvice.class)) {
            return false;
        }

        // Skip for Swagger/SpringDoc related packages
        String packageName = returnType.getDeclaringClass().getPackage().getName();
        return !packageName.contains("springdoc") &&
                !packageName.contains("swagger") &&
                !packageName.contains("openapi");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // Skip for Swagger/OpenAPI endpoints
        String path = request.getURI().getPath();
        if (path.contains("/v3/api-docs") ||
                path.contains("/swagger-ui") ||
                path.contains("/swagger-resources")) {
            return body;
        }

        // Return if already a Response object
        if (body instanceof Response) {
            return body;
        }

        // Handle null values
        if (body == null) {
            return Response.builder()
                    .code("200")
                    .message("Success")
                    .data(null)
                    .build();
        }

        // Special handling for String responses (common issue with ResponseBodyAdvice)
        if (body instanceof String) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return objectMapper.writeValueAsString(
                        Response.builder()
                                .code("200")
                                .message("Success")
                                .data(body)
                                .build()
                );
            } catch (JsonProcessingException e) {
                log.error("Error processing string response", e);
                return Response.builder()
                        .code("500")
                        .message("Internal Server Error")
                        .build();
            }
        }

        // Wrap the response in a standard Response object
        return Response.builder()
                .code("200")
                .message("Success")
                .data(body)
                .build();
    }
}