package fontys.sem3.school.configuration.security.token;

import java.util.Set;

public interface AccessToken {
    String getSubject();

    Long getPlayerId();

    Set<String> getRoles();

    boolean hasRole(String roleName);
}
