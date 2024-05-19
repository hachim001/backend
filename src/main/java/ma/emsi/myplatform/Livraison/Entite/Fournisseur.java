package ma.emsi.myplatform.Livraison.Entite;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import ma.emsi.myplatform.authentication.user.User;

import java.util.List;

@Entity
@Data
public class Fournisseur extends User {
    private String detailsFournisseur;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes;
}

