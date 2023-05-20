package com.company.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * · to authenticate the UI we should use UserDetailsService that Sprint provide. Because of that our SecurityService
 *   extends from UserDetailsService
 */
public interface SecurityService extends UserDetailsService {
}
