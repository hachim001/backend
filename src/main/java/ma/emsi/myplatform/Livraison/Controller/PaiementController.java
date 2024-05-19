package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Paiement;
import ma.emsi.myplatform.Livraison.Service.PaiementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Gestion des Paiements", description = "APIs pour la gestion des paiements")
public class PaiementController {
    private final PaiementService paiementService;

    @Operation(summary = "Obtenir tous les paiements")
    @GetMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<Paiement>> getAllPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @Operation(summary = "Obtenir un paiement par son ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Integer id) {
        Optional<Paiement> paiement = paiementService.getPaiementById(id);
        return paiement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Créer un nouveau paiement")
    @PostMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        Paiement createdPaiement = paiementService.createPaiement(paiement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaiement);
    }

    @Operation(summary = "Mettre à jour un paiement existant")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable Integer id, @RequestBody Paiement paiement) {
        paiement.setId(id);
        Paiement updatedPaiement = paiementService.updatePaiement(paiement);
        return ResponseEntity.ok(updatedPaiement);
    }

    @Operation(summary = "Supprimer un paiement par son ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaiement(@PathVariable Integer id) {
        paiementService.deletePaiement(id);
    }
}

