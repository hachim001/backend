package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Acheteur;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Service.AcheteurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acheteurs")
@RequiredArgsConstructor
@Tag(name = "Gestion des Acheteurs", description = "APIs pour la gestion des acheteurs")
public class AcheteurController {
    private final AcheteurService acheteurService;

    @Operation(summary = "Obtenir tous les produits")
    @GetMapping("/produits")
    @PreAuthorize("hasRole('ACHETEUR')")
    public ResponseEntity<List<Produit>> getProducts() {
        List<Produit> products = acheteurService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Ajouter un produit au panier")
    @PostMapping("/panier")
    @PreAuthorize("hasRole('ACHETEUR')")
    public ResponseEntity<Void> addToCart(@AuthenticationPrincipal Acheteur acheteur, @RequestParam Integer produitId, @RequestParam Integer quantite) {
        acheteurService.addToCart(acheteur.getId(), produitId, quantite);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Passer à la caisse et créer une commande")
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('ACHETEUR')")
    public ResponseEntity<Void> checkout(@AuthenticationPrincipal Acheteur acheteur) {
        acheteurService.checkout(acheteur.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

