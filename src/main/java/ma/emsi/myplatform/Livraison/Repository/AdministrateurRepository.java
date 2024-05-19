package ma.emsi.myplatform.Livraison.Repository;

import ma.emsi.myplatform.Livraison.Entite.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {
    Optional<Administrateur> findByEmail(String email);
}
