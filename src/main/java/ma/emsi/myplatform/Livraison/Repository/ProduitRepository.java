package ma.emsi.myplatform.Livraison.Repository;

import ma.emsi.myplatform.Livraison.Entite.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByFournisseurId(Integer fournisseurId);
}
