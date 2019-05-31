package be.vlproject.egcevent.marshaller;

//import be.vlproject.egcevent.marshaller.mixin.UserMixin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jackson2.CoreJackson2Module;

public class EgcObjectMapper extends ObjectMapper {

    public EgcObjectMapper() {
        super();

//        addMixIn(User.class, UserMixin.class);
//        addMixIn(GrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
//        addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);

        this.registerModule(new CoreJackson2Module());
    }
}
