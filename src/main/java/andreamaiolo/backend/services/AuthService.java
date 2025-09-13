package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.exceptions.UnAuthorizedException;
import andreamaiolo.backend.payloads.LoginPayload;
import andreamaiolo.backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkAndCreateToken(LoginPayload payload) {
        User found = this.userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), found.getPassword())) {
            String accessToken = jwtTools.createToken(found);
            return accessToken;
        } else {
            throw new UnAuthorizedException("Invalid credentials");
        }
    }
}

