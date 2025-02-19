package com.sparta.twotwo.common.config;

import com.sparta.twotwo.auth.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long memberId = SecurityUtil.getMemberIdFromSecurityContext();
        log.info("Current Auditor: {}", memberId);
        return Optional.ofNullable(memberId);
    }
}
