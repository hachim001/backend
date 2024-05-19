package ma.emsi.myplatform.Livraison.Entite;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ImageProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private String cheminImage;
}
