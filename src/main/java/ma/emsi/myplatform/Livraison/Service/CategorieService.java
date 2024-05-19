package ma.emsi.myplatform.Livraison.Service;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Categorie;
import ma.emsi.myplatform.Livraison.Repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieById(Integer id) {
        return categorieRepository.findById(id);
    }

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(Integer id) {
        categorieRepository.deleteById(id);
    }
}

