package be.vlproject.egcevent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class EgcUserDetailService extends InMemoryUserDetailsManager {

    @Autowired
    public EgcUserDetailService(UserDetailsStore userDetailsStore) {
        super(userDetailsStore.getUsersAsProperties());
    }
}
