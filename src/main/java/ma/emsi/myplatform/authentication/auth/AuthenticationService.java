package ma.emsi.myplatform.authentication.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.authentication.role.Role;
import ma.emsi.myplatform.authentication.role.Rolerepository;
import ma.emsi.myplatform.authentication.security.JwtService;
import ma.emsi.myplatform.authentication.token.Token;
import ma.emsi.myplatform.authentication.token.TokenRepository;
import ma.emsi.myplatform.authentication.user.User;
import ma.emsi.myplatform.authentication.user.UserRepository;
import ma.emsi.myplatform.Livraison.Entite.Acheteur;
import ma.emsi.myplatform.Livraison.Entite.Fournisseur;
import ma.emsi.myplatform.Livraison.Repository.AcheteurRepository;
import ma.emsi.myplatform.Livraison.Repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Rolerepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AcheteurRepository acheteurRepository;
    private final FournisseurRepository fournisseurRepository;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request) throws MessagingException {
        String roleName;
        User user;

        switch (request.getRoleType().toLowerCase()) {
            case "fournisseur":
                roleName = "FOURNISSEUR";
                user = new Fournisseur();
                break;
            case "acheteur":
                roleName = "ACHETEUR";
                user = new Acheteur();
                break;
            default:
                throw new IllegalArgumentException("Invalid role type");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setEnabled(false);
        user.setRoles(List.of(role));

        if (user instanceof Acheteur) {
            acheteurRepository.save((Acheteur) user);
        } else if (user instanceof Fournisseur) {
            fournisseurRepository.save((Fournisseur) user);
        } else {
            userRepository.save(user);
        }

        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int len) {
        String characters = "0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        var user = (User) auth.getPrincipal();
        boolean hasCorrectRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(request.getRoleType()));

        if (!hasCorrectRole) {
            throw new IllegalArgumentException("User does not have the correct role");
        }

        String token = jwtService.generateToken(user, request.getRoleType());
        return AuthenticationResponse.builder().token(token).build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email");
        }
        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
