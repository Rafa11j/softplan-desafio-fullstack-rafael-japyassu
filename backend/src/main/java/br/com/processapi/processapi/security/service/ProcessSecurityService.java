package br.com.processapi.processapi.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("processSecurityService")
public class ProcessSecurityService {

    public boolean hasPermissionAdministrator(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));
        return roles.contains("ADMINISTRATOR");
    }

    public boolean hasPermissionTriator(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));
        return roles.contains("TRIATOR");
    }

    public boolean hasPermissionFinisher(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));
        return roles.contains("FINISHER");
    }
}
