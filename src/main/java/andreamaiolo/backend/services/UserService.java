package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.enums.Role;
import andreamaiolo.backend.exceptions.NotFoundException;
import andreamaiolo.backend.payloads.UserPayload;
import andreamaiolo.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder bcrypt;

    public User saveUser(UserPayload entryPayload) {
        User newUser = new User();
        newUser.setName(entryPayload.name());
        newUser.setSurname(entryPayload.surname());
        newUser.setEmail(entryPayload.email());
        newUser.setPassword(bcrypt.encode(entryPayload.password()));
        newUser.setRole(Role.USER);
        this.userRepo.save(newUser);
        return newUser;
    }

    public User findByEmail(String email) {
        return this.userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
    }
}
