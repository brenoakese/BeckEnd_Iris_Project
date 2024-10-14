package com.springwebundf.securityjwtproject.infra.security;

import java.time.Instant;

public record TokenData(String token, Instant expirationDate) {
}
