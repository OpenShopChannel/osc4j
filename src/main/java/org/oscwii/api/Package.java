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
                      String[] contributors, Description description, Map<Asset.Type, Asset> assets,
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
