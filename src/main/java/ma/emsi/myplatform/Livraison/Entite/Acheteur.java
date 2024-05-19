package ma.emsi.myplatform.Livraison.Entite;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import ma.emsi.myplatform.authentication.user.User;
import java.util.List;

@Entity
@Data
public class Acheteur extends User {
    private String detailsAcheteur;

    @OneToMany(mappedBy = "acheteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes;

    @OneToOne(mappedBy = "acheteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Panier panier;
}


