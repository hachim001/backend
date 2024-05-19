package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Facture;
import ma.emsi.myplatform.Livraison.Service.FactureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
@Tag(name = "Gestion des Factures", description = "APIs pour la gestion des factures")
public class FactureController {
    private final FactureService factureService;

    @Operation(summary = "Obtenir toutes les factures")
    @GetMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<Facture>> getAllFactures() {
        List<Facture> factures = factureService.getAllFactures();
        return ResponseEntity.ok(factures);
    }

    @Operation(summary = "Obtenir une facture par son ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Facture> getFactureById(@PathVariable Integer id) {
        Optional<Facture> facture = factureService.getFactureById(id);
        return facture.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Créer une nouvelle facture")
    @PostMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        Facture createdFacture = factureService.createFacture(facture);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacture);
    }

    @Operation(summary = "Mettre à jour une facture existante")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<Facture> updateFacture(@PathVariable Integer id, @RequestBody Facture facture) {
        facture.setId(id);
        Facture updatedFacture = factureService.updateFacture(facture);
        return ResponseEntity.ok(updatedFacture);
    }

    @Operation(summary = "Supprimer une facture par son ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacture(@PathVariable Integer id) {
        factureService.deleteFacture(id);
    }
}

