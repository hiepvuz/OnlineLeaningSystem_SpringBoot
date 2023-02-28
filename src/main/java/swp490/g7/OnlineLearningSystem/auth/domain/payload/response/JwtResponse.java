package swp490.g7.OnlineLearningSystem.auth.domain.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    public static final String TYPE = "Bearer";

    private String token;
    private String type = TYPE;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private Boolean disable;
    private Boolean verify;
    private Boolean googleAuth;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, Boolean disable, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.disable = disable;
        this.roles = roles;
    }

    public JwtResponse(Long id, String username, String email, Boolean disable) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.disable = disable;
    }

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, Boolean disable, List<String> roles,
                       Boolean verify, Boolean googleAuth) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.disable = disable;
        this.roles = roles;
        this.verify = verify;
        this.googleAuth = googleAuth;
    }

}
