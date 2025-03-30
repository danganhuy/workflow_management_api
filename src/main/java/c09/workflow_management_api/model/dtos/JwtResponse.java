package c09.workflow_management_api.model.dtos;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(Long id, String token, String username, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }
}
