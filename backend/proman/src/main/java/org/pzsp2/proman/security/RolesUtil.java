package org.pzsp2.proman.security;

import java.util.Map;

public class RolesUtil {
    public enum Roles {
        USER,
        ADMIN,
        OWNER
    }

    public final static Map<Roles, String> roles = Map.of(
        Roles.USER, "USER",
        Roles.ADMIN, "ADMIN",
        Roles.OWNER, "OWNER"
    );
}
