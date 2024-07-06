/*
 * MIT License
 *
 * Copyright (c) 2024 Open Shop Channel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.oscwii.api;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.oscwii.api.impl.APIBackend;

/**
 * A builder for creating an instance of {@link OSCAPI}.
 */
public final class OSCAPIBuilder
{
    private Gson gson;
    private OkHttpClient httpClient;
    private final String userAgent;

    private OSCAPIBuilder(String userAgent)
    {
        if(userAgent == null || userAgent.isBlank())
            throw new IllegalArgumentException("userAgent cannot be null or empty!");
        this.userAgent = userAgent;
    }

    /**
     * Sets the {@link Gson} instance to use for JSON serialization and deserialization.<br>
     * If not set, will create a new instance.
     *
     * @param gson the Gson instance to use
     */
    public void setGson(Gson gson)
    {
        this.gson = gson;
    }

    /**
     * Sets the {@link OkHttpClient} instance to use for HTTP requests.<br>
     * If not set, will create a new instance.
     *
     * @param httpClient the OkHttpClient instance to use
     */
    public void setHttpClient(OkHttpClient httpClient)
    {
        this.httpClient = httpClient;
    }

    /**
     * Builds the {@link OSCAPI} instance.
     *
     * @return the OSCAPI instance ready to use
     */
    public OSCAPI build()
    {
        return new APIBackend(gson == null ? new Gson() : gson,
                httpClient == null ? new OkHttpClient() : httpClient,
                API_HOST, userAgent);
    }

    /**
     * Constructs a new instance of the builder.
     * <br>
     * <b>Note:</b> The user agent cannot be null or empty.
     * <br>
     * <b>Please use an appropriate User-Agent so we can identify your application.</b>
     *
     * @param userAgent The user agent to use for the API requests.
     */
    public static OSCAPI createWrapper(String userAgent)
    {
        if(userAgent == null || userAgent.isBlank())
            throw new IllegalArgumentException("userAgent cannot be null or empty!");
        return new APIBackend(new Gson(), new OkHttpClient(), API_HOST, userAgent);
    }

    private static final String API_HOST = "https://hbb1.oscwii.org";
}
