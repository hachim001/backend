package ma.emsi.myplatform.Livraison.Controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Service.FournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fournisseurs/{fournisseurId}/produits")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping
    public ResponseEntity<List<Produit>> getProductsByFournisseur(@PathVariable Integer fournisseurId) {
        List<Produit> produits = fournisseurService.getProductsByFournisseur(fournisseurId);
        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

    @GetMapping("/{produitId}")
    public ResponseEntity<Optional<Produit>> getProductById(@PathVariable Integer fournisseurId, @PathVariable Integer produitId) {
        Optional<Produit> produit = fournisseurService.getProductById(produitId);
        if (produit.isPresent() && produit.get().getFournisseur().getId().equals(fournisseurId)) {
            return new ResponseEntity<>(produit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Produit> createProduct(@PathVariable Integer fournisseurId, @RequestBody Produit produit, @RequestParam("image") MultipartFile imageFile) throws IOException {
        Produit newProduit = fournisseurService.createProduct(produit, fournisseurId, imageFile);
        return new ResponseEntity<>(newProduit, HttpStatus.CREATED);
    }

    @PutMapping("/{produitId}")
    public ResponseEntity<Produit> updateProduct(@PathVariable Integer fournisseurId, @PathVariable Integer produitId, @RequestBody Produit produit, @RequestParam("image") MultipartFile imageFile) throws IOException {
        Optional<Produit> existingProduit = fournisseurService.getProductById(produitId);
        if (existingProduit.isPresent() && existingProduit.get().getFournisseur().getId().equals(fournisseurId)) {
            Produit updatedProduit = fournisseurService.updateProduct(produit, fournisseurId, imageFile);
            return new ResponseEntity<>(updatedProduit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

