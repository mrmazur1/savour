package com.vb4.savour.data.client;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Request that expects a JSON response and will decode into a POJO
 * @param <Req> the request type of this request
 * @param <Res> the response type of this request
 */
public class GsonRequest<Req, Res> extends Request<Res> {
    private final Gson gson = new Gson();
    private Class<Res> responseClass;
    private Response.Listener<Res> responseListener;
    private Req requestBody;
    private Map<String, String> headers;

    /**
     * Create a request that expects JSON and decodes into {@code clazz}
     * @param method The method of this request
     * @param url The url of this request
     * @param errorListener listener notified on unsuccessful response
     */
    public GsonRequest(int method, String url, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        headers = Collections.emptyMap();
    }

    @Override
    protected Response<Res> parseNetworkResponse(NetworkResponse response) {
        try {
            // Convert JSON response into requested generic type
            String stringResponse = new String(response.data, StandardCharsets.UTF_8);
            Log.d("GsonRequest", "Raw JSON response " + getUrl() + ": " + stringResponse);
            Res data = gson.fromJson(stringResponse, responseClass);

            return Response.success(data, null);
        } catch (JsonSyntaxException | JsonIOException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e.getMessage(), e));
        }
    }

    @Override
    protected void deliverResponse(Res response) {
        responseListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        if (requestBody != null) {
            return "application/json";
        }

        return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() {
        Log.d("GsonRequest", "Request body " + getUrl() + ": " + gson.toJson(requestBody));
        return gson.toJson(requestBody).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Map<String, String> getHeaders() {
        Log.d("GsonRequest", "Request headers " + getUrl()  + ": " + mapToString(headers));
        return headers;
    }

    private String mapToString(Map<?, ?> map) {
        String inner = map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(", "));
        return "{" + inner + "}";
    }

    public static class Builder<Req, Res> {
        private final int method;
        private String url;
        private Req requestBody;
        private Class<Res> responseClass;
        private Response.Listener<Res> responseListener;
        @Nullable private Response.ErrorListener errorListener;
        private Map<String, String> headers;

        public Builder(int method) {
            this.method = method;
        }

        public static <BuilderRes> Builder<Void, BuilderRes> get() {
            return new Builder<>(Request.Method.GET);
        }

        public static <BuilderReq, BuilderRes> Builder<BuilderReq, BuilderRes> post() {
            return new Builder<>(Request.Method.POST);
        }

        public Builder<Req, Res> responseClass(Class<Res> resClazz) {
            responseClass = resClazz;
            return this;
        }

        public Builder<Req, Res> responseListener(Response.Listener<Res> listener) {
            responseListener = listener;
            return this;
        }

        public Builder<Req, Res> url(String url) {
            this.url = url;
            return this;
        }

        public Builder<Req, Res> headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder<Req, Res> requestBody(Req newRequestBody) {
            requestBody = newRequestBody;
            return this;
        }

        public Builder<Req, Res> errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public GsonRequest<Req, Res> build() {
            GsonRequest<Req, Res> finalRequest = new GsonRequest<>(method, url, errorListener);
            finalRequest.responseListener = responseListener;
            finalRequest.responseClass = responseClass;
            finalRequest.requestBody = requestBody;
            finalRequest.headers = headers;
            return finalRequest;
        }
    }
}
