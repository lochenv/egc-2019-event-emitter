package be.vlproject.egcevent.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;

@Component
public class UserDetailsStore {

    private static final String USER_PROP_PREFIX = "security.user.";
    @Autowired
    private Environment environment;

    @SuppressWarnings("unchecked")
    public Properties getUsersAsProperties() {
        return (Properties) ((StandardServletEnvironment) environment).getPropertySources()
                .stream()
                .filter(propertySource -> StringUtils.startsWith(propertySource.getName(), "applicationConfig"))
                .map(PropertySource::getSource)
                .map(Map.class::cast)
                .flatMap(map -> map.entrySet().stream())
                .filter(entry -> ((Map.Entry<CharSequence, CharSequence>) entry).getKey().toString().startsWith(USER_PROP_PREFIX))
                .collect(Collector.of(
                        Properties::new,
                        (prop, entry) -> prop.setProperty(
                                StringUtils.remove(((Map.Entry<CharSequence, CharSequence>) entry).getKey().toString(), USER_PROP_PREFIX),
                                ((Map.Entry<CharSequence, CharSequence>) entry).getValue().toString()
                        ),
                        (left, right) -> left));
    }
}
