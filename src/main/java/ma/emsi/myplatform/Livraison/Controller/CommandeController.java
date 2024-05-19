package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Commande;
import ma.emsi.myplatform.Livraison.Entite.Fournisseur;
import ma.emsi.myplatform.Livraison.Service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs/commandes")
@RequiredArgsConstructor
@Tag(name = "Gestion des Commandes", description = "APIs pour la gestion des commandes par les fournisseurs")
public class CommandeController {
    private final CommandeService commandeService;

    @Operation(summary = "Obtenir toutes les commandes pour le fournisseur authentifié")
    @GetMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<Commande>> getOrders(@AuthenticationPrincipal Fournisseur fournisseur) {
        List<Commande> orders = commandeService.getOrdersByFournisseur(fournisseur.getId());
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Obtenir une commande par son ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Commande> getOrder(@PathVariable Integer id) {
        return commandeService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mettre à jour le statut d'une commande")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Commande> updateOrderStatus(@PathVariable Integer id, @RequestBody String status) {
        return commandeService.updateOrderStatus(id, status)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
