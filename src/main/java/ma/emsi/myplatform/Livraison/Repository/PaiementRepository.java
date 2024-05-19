package ma.emsi.myplatform.Livraison.Repository;

import ma.emsi.myplatform.Livraison.Entite.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
}
