package ma.emsi.myplatform.authentication.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {


    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not formatted")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min=8,message = "Password should be have 8 characters long minimum")
    private String password;
    @NotEmpty(message = "Role type is mandatory")
    @NotBlank(message = "Role type is mandatory")
    private String roleType;
}
