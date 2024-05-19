package ma.emsi.myplatform.Livraison.Repository;

import ma.emsi.myplatform.Livraison.Entite.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Integer> {
}
