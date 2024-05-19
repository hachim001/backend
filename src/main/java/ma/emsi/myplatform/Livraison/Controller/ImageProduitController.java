package ma.emsi.myplatform.Livraison.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.ImageProduit;
import ma.emsi.myplatform.Livraison.Service.ImageProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "Gestion des Images de Produits", description = "APIs pour la gestion des images de produits")
public class ImageProduitController {
    private final ImageProduitService imageProduitService;

    @Operation(summary = "Obtenir toutes les images de produits")
    @GetMapping
    public ResponseEntity<List<ImageProduit>> getAllImages() {
        List<ImageProduit> images = imageProduitService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @Operation(summary = "Obtenir une image par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<ImageProduit> getImageById(@PathVariable Integer id) {
        Optional<ImageProduit> image = imageProduitService.getImageById(id);
        return image.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Créer une nouvelle image de produit")
    @PostMapping
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<ImageProduit> createImage(@RequestParam("produitId") Integer produitId, @RequestPart("image") MultipartFile imageFile) throws IOException {
        ImageProduit createdImage = imageProduitService.createImage(produitId, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
    }

    @Operation(summary = "Mettre à jour une image existante")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<ImageProduit> updateImage(@PathVariable Integer id, @RequestPart("image") MultipartFile imageFile) throws IOException {
        ImageProduit updatedImage = imageProduitService.updateImage(id, imageFile);
        return ResponseEntity.ok(updatedImage);
    }

    @Operation(summary = "Supprimer une image par son ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Integer id) {
        imageProduitService.deleteImage(id);
    }
}
