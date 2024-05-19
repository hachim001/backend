package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.authentication.user.User;
import ma.emsi.myplatform.authentication.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}

