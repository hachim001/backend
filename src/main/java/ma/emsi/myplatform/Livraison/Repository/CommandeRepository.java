package ma.emsi.myplatform.Livraison.Repository;
import ma.emsi.myplatform.Livraison.Entite.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByFournisseurId(Integer fournisseurId);
}

