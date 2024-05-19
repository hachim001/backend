package ma.emsi.myplatform.Livraison.Service;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Fournisseur;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Repository.FournisseurRepository;
import ma.emsi.myplatform.Livraison.Repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final ProduitRepository produitRepository;
    private final FournisseurRepository fournisseurRepository;

    public List<Produit> getProductsByFournisseur(Integer fournisseurId) {
        return produitRepository.findByFournisseurId(fournisseurId);
    }

    public Optional<Produit> getProductById(Integer id) {
        return produitRepository.findById(id);
    }

    public Produit createProduct(Produit produit, Integer fournisseurId, MultipartFile imageFile) throws IOException {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur not found"));
        produit.setFournisseur(fournisseur);
        // Optionally, set the image file
        // produit.setImage(imageFile.getBytes());
        return produitRepository.save(produit);
    }

    public Produit updateProduct(Produit produit, Integer fournisseurId, MultipartFile imageFile) throws IOException {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur not found"));
        produit.setFournisseur(fournisseur);
        // Optionally, set the image file
        // produit.setImage(imageFile.getBytes());
        return produitRepository.save(produit);
    }

    public void deleteProduct(Integer id) {
        produitRepository.deleteById(id);
    }
}


