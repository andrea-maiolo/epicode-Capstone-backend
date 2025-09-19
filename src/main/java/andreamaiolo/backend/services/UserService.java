package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.enums.Role;
import andreamaiolo.backend.exceptions.BadRequestException;
import andreamaiolo.backend.exceptions.NotFoundException;
import andreamaiolo.backend.payloads.UserPayload;
import andreamaiolo.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public User findById(Long userId) {
        return this.userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public User findAndUpdate(Long userId, UserPayload payload) {
        User found = this.findById(userId);
        if (!found.getEmail().equals(payload.email())) {
            this.userRepo.findByEmail(payload.email()).ifPresent(user -> {
                throw new BadRequestException("Email already in use");
            });
        }
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setPassword(payload.password());
        this.userRepo.save(found);
        return found;
    }

    public void findAndDelete(Long userId) {
        User found = this.findById(userId);
        this.userRepo.delete(found);
    }

    public Page<User> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 5) pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.userRepo.findAll(pageable);
    }
}
