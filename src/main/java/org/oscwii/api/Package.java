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

import com.google.gson.annotations.SerializedName;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Represents a package available on the Open Shop Channel.
 * <br>
 * For more information on the fields please check our API docs.
 *
 * @see <a href="https://docs.oscwii.org/repository-manager/api/retrieving-contents#application-object">API Documentation</a>
 */
public record Package(String slug, String name, String author, String[] authors, String category,
                      String[] contributors, int downloads, Description description, Map<Asset.Type, Asset> assets,
                      EnumSet<Flag> flags, @SerializedName("package_type") String packageType,
                      List<String> peripherals, @SerializedName("release_date") long releaseDate,
                      @SerializedName("shop") ShopTitle titleInfo, List<String> subdirectories,
                      @SerializedName("supported_platforms") List<String> supportedPlatforms,
                      @SerializedName("uncompressed_size") long uncompressedSize, String version)
{
    /**
     * Represents an asset of a package.
     *
     * @see <a href="https://docs.oscwii.org/repository-manager/api/retrieving-contents#assets">API Documentation</a>
     */
    public record Asset(String url, String hash, long size)
    {
        /**
         * Asset type.
         */
        public enum Type
        {
            @SerializedName("archive")
            ARCHIVE,
            @SerializedName("binary")
            BINARY,
            @SerializedName("icon")
            ICON,
            @SerializedName("meta")
            META
        }
    }

    /**
     * Represents the title information of a package.
     *
     * @see <a href="https://docs.oscwii.org/repository-manager/api/retrieving-contents#shop-information">API Documentation</a>
     */
    public record ShopTitle(@SerializedName("contents_size") long contentsSize,
                            @SerializedName("title_id") String titleId, int inodes,
                            @SerializedName("title_version") int titleVersion,
                            @SerializedName("tmd_size") long tmdSize)
    {}

    /**
     * Enum of available flags a package can have.
     */
    public enum Flag
    {
        /**
         * Signals this application is not recommended to use unless necessary, and an alternative should be used instead.
         */
        @SerializedName("deprecated")
        DEPRECATED,
        /**
         * Signals this application can write to NAND.
         */
        @SerializedName("writes_to_nand")
        WRITES_TO_NAND
    }

    /**
     * Represents a description of a package.
     */
    public record Description(@SerializedName("short") String shortDesc, @SerializedName("long") String longDesc) {}
}
