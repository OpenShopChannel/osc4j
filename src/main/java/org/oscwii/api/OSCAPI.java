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
