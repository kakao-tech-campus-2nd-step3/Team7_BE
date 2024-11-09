package team7.inplace.placeMessage.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Link(
    @JsonProperty("web_url") String webUrl,
    @JsonProperty("mobile_web_url") String mobileWebUrl,
    @JsonProperty("android_execution_params") String androidExecutionParams,
    @JsonProperty("ios_execution_params") String iosExecutionParams
) {

    public static Link of(String url) {
        return new Link(url, url, null, null);
    }
}
