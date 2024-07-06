package org.oscwii.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a category of packages.
 */
public record Category(String name, @SerializedName("display_name") String displayName)
{
}
