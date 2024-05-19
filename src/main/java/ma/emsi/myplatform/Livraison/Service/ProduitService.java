package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository produitRepository;

    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProductById(Integer id) {
        return produitRepository.findById(id);
    }

    public Produit createProduct(Produit produit, MultipartFile imageFile) throws IOException {
        // Optionally, set the image file
        // produit.setImage(imageFile.getBytes());
        return produitRepository.save(produit);
    }

    public Produit updateProduct(Produit produit, MultipartFile imageFile) throws IOException {
        // Optionally, set the image file
        // produit.setImage(imageFile.getBytes());
        return produitRepository.save(produit);
    }

    public void deleteProduct(Integer id) {
        produitRepository.deleteById(id);
    }
}
