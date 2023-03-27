package de.app.fivegla.integration.farm21.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * Soil scout token request.
 */
@Getter
@Builder
public class AccessTokenRequest {

    /**
     * Email address of the user.
     */
    private final String email;

    /**
     * Password of the user.
     */
    private final String password;

}
