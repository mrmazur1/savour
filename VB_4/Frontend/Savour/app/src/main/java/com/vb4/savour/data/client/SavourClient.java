package com.vb4.savour.data.client;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Client of remote Savour server
 */
public class SavourClient {
    /** Base URL this client is pointed at */
    private static final String BASE_URL = "http://10.0.2.2:8080";

//    private static final String BASE_URL = "http://coms-309-037.cs.iastate.edu:8080";

    private static final String LOG_TAG = "SavourClient";

    /** Key of header used to store user id */
    private static final String USER_ID_HEADER_KEY = "x-savour-user-id";

    /** Singleton instance of client */
    private static SavourClient instance;

    /** The queue of requests for the Savour API */
    private final RequestQueue requestQueue;

    /** Headers sent with each request */
    private final Map<String, String> globalHeaders;

    /** Is the app being tested? Makes getApiUrl return an empty string if true */
    public boolean testing = false;

    private SavourClient(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.start();
        globalHeaders = new HashMap<>();
    }

    /**
     * Create a singleton instance of the client
     * @param context app's application context
     */
    public static void initialize(Context context) {
        if (instance == null) {
            instance = new SavourClient(context);
        }
    }

    /**
     * Get an instance of the client
     * @return instance of client
     */
    public static SavourClient getInstance() {
        return instance;
    }

    /**
     * Set a header to be send out with each request
     * @param key the header key
     * @param value the header value
     */
    public void setHeader(String key, String value) {
        globalHeaders.put(key, value);
        Log.i("SavourClient", "Setting header '" + key + "' to '" + value + "'");
    }

    /**
     * Remove a header that is sent out with each request
     * @param key the header key
     */
    public void removeHeader(String key) {
        globalHeaders.remove(key);
    }

    /**
     * Set's the user id token for this client (put in headers of all requests)
     * @param id the user id
     */
    public void setUserId(int id) {
        setHeader(USER_ID_HEADER_KEY, String.valueOf(id));
    }

    /**
     * Removes the authentication header from this client
     */
    public void clearUserId() {
        removeHeader(USER_ID_HEADER_KEY);
    }

    /**
     * Fetches an endpoint using the client and posts response to a {@link MutableLiveData} on
     * completion.
     * @param endpoint the endpoint to point a base request at
     * @param type the type of class the JSON payload decodes into
     * @param liveData the data that will be posted to on completion
     * @param <T> the JSON-decoded payload type
     */
    public <T> void get(String endpoint, Class<T> type, MutableLiveData<AsyncData<T>> liveData) {
        get(endpoint, type,
            r -> liveData.postValue(AsyncData.success(r)),
            e -> liveData.postValue(AsyncData.error(e)),
            () -> liveData.postValue(AsyncData.loading())
        );
    }

    public <Req, Res> void post(String endpoint, Req request, Class<Res> resClass, MutableLiveData<AsyncData<Res>> liveData) {
        post(endpoint, request, resClass,
            r -> liveData.postValue(AsyncData.success(r)),
            e -> liveData.postValue(AsyncData.error(e)),
            () -> liveData.postValue(AsyncData.loading())
        );
    }

    /**
     * Fetches an endpoint using the client and posts response to a {@link MutableLiveData} on
     * completion.
     * @param endpoint the endpoint to point a base request at
     * @param type the type of class the JSON payload decodes into
     * @param responseListener callback fired when response is received
     * @param errorListener callback fired when an error occurs
     * @param loadListener callback fired when loading begins
     * @param <Res> the JSON-decoded payload type
     */
    public <Res> void get(
            String endpoint,
            Class<Res> type,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener,
            @Nullable LoadListener loadListener
    ) {
        String fullUrl = getApiUrl(endpoint);

        Log.d(LOG_TAG, "GET " + endpoint);
        if (loadListener != null) {
            loadListener.onLoadBegin();
        }

        Response.Listener<Res> resListenerWithLog = response -> {
            responseListener.onResponse(response);
            Log.d(LOG_TAG, "Response from GET " + fullUrl + " - " + response);
        };

        GsonRequest<Void, Res> request = GsonRequest.Builder
                .<Res>get()
                .url(fullUrl)
                .headers(globalHeaders)
                .responseClass(type)
                .responseListener(resListenerWithLog)
                .errorListener(error -> {
                    Log.e(LOG_TAG, "Error fetching " + fullUrl + " - " + error.getMessage());
                    error.printStackTrace();
                    errorListener.onErrorResponse(error);
                })
                .build();

        requestQueue.add(request);
    }

    public <Req, Res> void post(
            String endpoint,
            Req requestBody,
            Class<Res> type,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener,
            @Nullable LoadListener loadListener
    ) {
        String fullUrl = getApiUrl(endpoint);

        Log.d(LOG_TAG, "POST " + endpoint);
        if (loadListener != null) {
            loadListener.onLoadBegin();
        }

        Response.Listener<Res> resListenerWithLog = response -> {
            responseListener.onResponse(response);
            Log.d(LOG_TAG, "Response from POST " + fullUrl + " - " + response);
        };

        GsonRequest<Req, Res> request = GsonRequest.Builder
                .<Req, Res>post()
                .url(fullUrl)
                .headers(globalHeaders)
                .requestBody(requestBody)
                .responseClass(type)
                .responseListener(resListenerWithLog)
                .errorListener(error -> {
                    Log.e(LOG_TAG, "Error fetching " + fullUrl + " - " + error.getMessage());
                    error.printStackTrace();
                    errorListener.onErrorResponse(error);
                })
                .build();

        requestQueue.add(request);
    }

    /**
     * Get full url from endpoint
     * @param endpoint the endpoint to append to the API's base url
     * @return the full url to the endpoint
     */
    private String getApiUrl(String endpoint) {
        if (testing) {
            return "";
        }

        return String.format("%s/%s", BASE_URL, endpoint);
    }
}
