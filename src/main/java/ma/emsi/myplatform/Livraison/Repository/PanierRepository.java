package ma.emsi.myplatform.Livraison.Repository;

import ma.emsi.myplatform.Livraison.Entite.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
}
