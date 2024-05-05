package dev.jlcorradi.mapstructdemo.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndPointConstants {
    public static final String V1 = "/api/v1";
    public static final String KEY_VALUE_PAIRS = V1 + "/key-value-pairs";
}
