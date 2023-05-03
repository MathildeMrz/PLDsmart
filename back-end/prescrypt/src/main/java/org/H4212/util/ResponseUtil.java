package org.H4212.util;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Objects;

public class ResponseUtil {

    private ResponseUtil() {}

    public static Response.ResponseBuilder ok(Object entity) {
        return Response.ok(entity)
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder ok() {
        return Response.ok()
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder noContent() {
        return Response.noContent()
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder accepted(
            Object entity) {
        return Response.accepted(entity)
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder created(
            Object entity, String uri) {
        return Response.created(URI.create(uri))
                .entity(entity)
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder serverError() {
        return Response
                // TODO: add tracking number or anything else that would be useful for a server error
                .serverError()
                .header(HttpHeaders.CONTENT_LANGUAGE, null);
    }

    public static Response.ResponseBuilder notFound() {
        return Response
                // TODO: add tracking number or anything else that would be useful for a server error
                .status(Response.Status.NOT_FOUND);
    }

    public static Response.ResponseBuilder badRequest() {
        return Response
                // TODO: add tracking number or anything else that would be useful for a server error
                .status(Response.Status.BAD_REQUEST);
    }

    public static Response.ResponseBuilder gatewayTimeout() {
        return Response.status(Response.Status.GATEWAY_TIMEOUT);
    }

    public static Response.ResponseBuilder redirectExisting(String uri) {
        return Response.seeOther(URI.create(uri));
    }

}
