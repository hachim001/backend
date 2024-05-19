package ma.emsi.myplatform.Livraison.Controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProducts() {
        List<Produit> produits = produitService.getAllProducts();
        return new ResponseEntity<>(produits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProductById(@PathVariable Integer id) {
        Optional<Produit> produit = produitService.getProductById(id);
        return produit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Produit> createProduct(@RequestBody Produit produit, @RequestParam("image") MultipartFile imageFile) throws IOException {
        Produit newProduit = produitService.createProduct(produit, imageFile);
        return new ResponseEntity<>(newProduit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduct(@PathVariable Integer id, @RequestBody Produit produit, @RequestParam("image") MultipartFile imageFile) throws IOException {
        if (produitService.getProductById(id).isPresent()) {
            Produit updatedProduit = produitService.updateProduct(produit, imageFile);
            return new ResponseEntity<>(updatedProduit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (produitService.getProductById(id).isPresent()) {
            produitService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
