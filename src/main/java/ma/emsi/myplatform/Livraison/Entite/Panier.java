package ma.emsi.myplatform.Livraison.Entite;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acheteur_id", nullable = false)
    private Acheteur acheteur;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticlePanier> articles;

    public float calculerSousTotal() {
        return (float) articles.stream().mapToDouble(ArticlePanier::calculerSousTotal).sum();
    }

    public void ajouterArticle(ArticlePanier article) {
        articles.add(article);
    }

    public void retirerArticle(ArticlePanier article) {
        articles.remove(article);
    }

    public void viderPanier() {
        articles.clear();
    }
}
