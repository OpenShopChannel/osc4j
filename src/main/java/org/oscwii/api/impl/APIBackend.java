package org.oscwii.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Nullable;
import org.oscwii.api.Category;
import org.oscwii.api.OSCAPI;
import org.oscwii.api.Package;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class APIBackend implements OSCAPI
{
    private final Gson gson;
    private final OkHttpClient httpClient;
    private final String apiHost, userAgent;

    private String featuredApp;
    private List<Category> categories;
    private List<Package> packages;
    private Map<String, String> newestApps;

    public APIBackend(Gson gson, OkHttpClient httpClient, String apiHost, String userAgent)
    {
        this.gson = Objects.requireNonNull(gson);
        this.httpClient = Objects.requireNonNull(httpClient);
        this.apiHost = Objects.requireNonNull(apiHost);
        this.userAgent = Objects.requireNonNull(userAgent).trim() +
                " - Open Shop Channel API Java Wrapper (v4)";

        this.categories = Collections.emptyList();
        this.packages = Collections.emptyList();
        this.newestApps = Collections.emptyMap();
    }

    @Override
    public List<Category> getCategories()
    {
        return Collections.unmodifiableList(categories);
    }

    @Override
    public List<Package> getPackages()
    {
        return Collections.unmodifiableList(packages);
    }

    @Override
    public List<Package> filterPackages(@Nullable String category, @Nullable String name)
    {
        return packages.stream()
            .filter(pkg -> category == null || pkg.category().equals(category))
            .filter(pkg -> name == null || pkg.name().toLowerCase().contains(name.toLowerCase()))
            .toList();
    }

    @Override
    public Map<String, Package> getNewestPackages()
    {
        Map<String, Package> packages = new HashMap<>();
        for(Map.Entry<String, String> entry : newestApps.entrySet())
            packages.put(entry.getKey(), getBySlug(entry.getValue()));
        return Collections.unmodifiableMap(packages);
    }

    @Override
    public @Nullable Package getFeaturedApp()
    {
        if(featuredApp == null)
            return null;
        return packages.stream()
            .filter(pkg -> pkg.slug().equalsIgnoreCase(featuredApp))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Featured app not found!"));
    }

    @Override
    public @Nullable Package getBySlug(String slug)
    {
        return packages.stream()
            .filter(pkg -> pkg.slug().equalsIgnoreCase(slug))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void fetchRepositoryInformation()
    {
        Request request = new Request.Builder()
            .url(API_ENDPOINT.formatted(apiHost, API_VERSION, "information"))
            .header("User-Agent", userAgent)
            .build();

        JsonObject obj = doRequest(request, TypeToken.get(JsonObject.class));
        JsonArray availableCategories = obj.getAsJsonArray("available_categories");
        this.categories = gson.fromJson(availableCategories, new TypeToken<>(){});
    }

    @Override
    public void fetchPackages()
    {
        Request request = new Request.Builder()
            .url(API_ENDPOINT.formatted(apiHost, API_VERSION, "contents"))
            .header("User-Agent", userAgent)
            .build();

        this.packages = doRequest(request, new TypeToken<>(){});
        this.newestApps = calculateNewestPackages();
    }

    @Override
    public void fetchFeaturedApp()
    {
        Request request = new Request.Builder()
            .url(API_ENDPOINT.formatted(apiHost, API_VERSION, "featured-app"))
            .header("User-Agent", userAgent)
            .build();

        this.featuredApp = doRequest(request, TypeToken.get(Package.class)).slug();
    }

    private Map<String, String> calculateNewestPackages()
    {
        Map<String, String> packages = new HashMap<>();
        packages.put("newest", getNewest(getPackages()));

        for(Category category : categories)
        {
            String newest = getNewest(filterPackages(category.name(), null));
            if(newest != null)
                packages.put(category.name(), newest);
        }

        return packages;
    }

    private String getNewest(List<Package> selection)
    {
        long date = 0;
        Package selected = null;

        for(Package app : selection)
        {
            if(date < app.releaseDate())
            {
                date = app.releaseDate();
                selected = app;
            }
        }

        return selected != null ? selected.slug() : null;
    }

    private <T> T doRequest(Request request, TypeToken<T> type)
    {
        try(Response response = httpClient.newCall(request).execute())
        {
            if(!response.isSuccessful())
                throw new IOException("HTTP request failed with status code " + response.code());
            if(response.body() == null)
                throw new IllegalStateException("Response body is null!");
            return gson.fromJson(new InputStreamReader(response.body().byteStream()), type);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed to fetch data from Open Shop Channel API:", e);
        }
    }

    public static final int API_VERSION = 4;
    private static final String API_ENDPOINT = "%s/api/v%d/%s";
}
