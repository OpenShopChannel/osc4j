package org.oscwii.api;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * The Open Shop Channel API interface.
 * <br>
 * An instance can be created by using the {@link OSCAPIBuilder} class.
 */
public interface OSCAPI
{
    /**
     * Returns a list of all defined categories by the upstream repository.
     *
     * @return list of {@link Category}
     */
    List<Category> getCategories();

    /**
     * Returns a list of all packages being served by the upstream repository.
     *
     * @return list of {@link Package}
     */
    List<Package> getPackages();

    /**
     * Returns a list of packages filtered by category and/or name.
     * <br>
     * @param category category name, or <code>null</code>
     * @param name package name, or <code>null</code>
     * @return list of {@link Package}
     */
    List<Package> filterPackages(@Nullable String category, @Nullable String name);

    /**
     * Returns a map of the newest packages.
     * <br>
     * Key: category name<br>
     * Value: package slug
     * @return map of newest packages
     */
    Map<String, Package> getNewestPackages();

    /**
     * Returns the featured app as defined by the upstream repository.
     * <br>
     * Also known as "App of the Day" in the official OSC website.
     *
     * @return the featured app or <code>null</code> if it doesn't exist
     */
    @Nullable Package getFeaturedApp();

    /**
     * Returns a package by its slug.
     *
     * @param slug internal name of the package
     * @return the package or <code>null</code> if not found
     */
    @Nullable Package getBySlug(String slug);

    /* Actions */

    /**
     * Fetches the repository information from the upstream repository.
     * <br>
     * This will make a network request to the repository's API.
     */
    void fetchRepositoryInformation();

    /**
     * Fetches the list of packages from the upstream repository.
     * <br>
     * This will make a network request to the repository's API.
     */
    void fetchPackages();

    /**
     * Fetches the featured app from the upstream repository.
     * <br>
     * This will make a network request to the repository's API.
     */
    void fetchFeaturedApp();
}
