package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.ImageProduit;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Repository.ImageProduitRepository;
import ma.emsi.myplatform.Livraison.Repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageProduitService {
    private final ImageProduitRepository imageProduitRepository;
    private final ProduitRepository produitRepository;

    public List<ImageProduit> getAllImages() {
        return imageProduitRepository.findAll();
    }

    public Optional<ImageProduit> getImageById(Integer id) {
        return imageProduitRepository.findById(id);
    }

    public ImageProduit createImage(Integer produitId, MultipartFile imageFile) throws IOException {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit not found"));
        ImageProduit imageProduit = new ImageProduit();
        imageProduit.setProduit(produit);
        imageProduit.setCheminImage(saveImageFile(imageFile));
        return imageProduitRepository.save(imageProduit);
    }

    public ImageProduit updateImage(Integer id, MultipartFile imageFile) throws IOException {
        ImageProduit imageProduit = imageProduitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));
        imageProduit.setCheminImage(saveImageFile(imageFile));
        return imageProduitRepository.save(imageProduit);
    }

    public void deleteImage(Integer id) {
        imageProduitRepository.deleteById(id);
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        // Logic to save image file and return the file path
        return "path/to/saved/image.jpg";
    }
}
