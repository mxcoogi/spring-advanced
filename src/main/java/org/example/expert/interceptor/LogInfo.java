package org.example.expert.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LogInfo {

    private String startTime;
    private String method;
    private String uri;
    private String ip;
    private String userAgent;
    private long duration;

}
