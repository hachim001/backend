package ma.emsi.myplatform.Livraison.Entite;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    private BigDecimal total;

    private LocalDateTime dateFacture;

    @OneToOne(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private Paiement paiement;
}

