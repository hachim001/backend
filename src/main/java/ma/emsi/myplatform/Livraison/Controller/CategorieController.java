package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Categorie;
import ma.emsi.myplatform.Livraison.Service.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Gestion des Catégories", description = "APIs pour la gestion des catégories")
public class CategorieController {
    private final CategorieService categorieService;

    @Operation(summary = "Obtenir toutes les catégories")
    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Obtenir une catégorie par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Integer id) {
        Optional<Categorie> categorie = categorieService.getCategorieById(id);
        return categorie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Créer une nouvelle catégorie")
    @PostMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody Categorie categorie) {
        Categorie createdCategorie = categorieService.createCategorie(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategorie);
    }

    @Operation(summary = "Mettre à jour une catégorie existante")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Integer id, @Valid @RequestBody Categorie categorie) {
        categorie.setId(id);
        Categorie updatedCategorie = categorieService.updateCategorie(categorie);
        return ResponseEntity.ok(updatedCategorie);
    }

    @Operation(summary = "Supprimer une catégorie par son ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategorie(@PathVariable Integer id) {
        categorieService.deleteCategorie(id);
    }
}

