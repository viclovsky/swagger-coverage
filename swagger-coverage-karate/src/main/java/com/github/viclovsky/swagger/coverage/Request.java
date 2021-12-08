package com.github.viclovsky.swagger.coverage;

import java.util.List;
import java.util.Map;

public class Request {
    private String baseUrl;
    private String path;
    private Map<String, List<String>> requestParams;
    private Map<String, List<String>> headerParams;
    private Map<String, List<Map<String, String>>> requestParts;
    private Boolean hasBody;
    private String method;
    private int statusCode;
    private Map<String, List<String>> responseHeaders;

    // #region Getter/Setter
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String uri) {
        this.baseUrl = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, List<String>> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, List<String>> requestParams) {
        this.requestParams = requestParams;
    }

    public Map<String, List<Map<String, String>>> getRequestParts() {
        return requestParts;
    }

    public void setRequestParts(Map<String, List<Map<String, String>>> requestParts) {
        this.requestParts = requestParts;
    }

    public Map<String, List<String>> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, List<String>> headerParams) {
        this.headerParams = headerParams;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean hasBody() {
        return hasBody;
    }

    public void setHasBody(Boolean hasBody) {
        this.hasBody = hasBody;
    }
    // #endregion
}
