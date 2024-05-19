package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.authentication.user.User;
import ma.emsi.myplatform.Livraison.Service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Gestion des Administrateurs", description = "APIs pour la gestion des administrateurs")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Obtenir tous les utilisateurs")
    @GetMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Supprimer un utilisateur par son ID")
    @DeleteMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        adminService.deleteUser(id);
    }
}

