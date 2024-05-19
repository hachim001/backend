package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Acheteur;
import ma.emsi.myplatform.Livraison.Entite.ArticlePanier;
import ma.emsi.myplatform.Livraison.Entite.Panier;
import ma.emsi.myplatform.Livraison.Entite.Produit;
import ma.emsi.myplatform.Livraison.Repository.AcheteurRepository;
import ma.emsi.myplatform.Livraison.Repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcheteurService {
    private final ProduitRepository produitRepository;
    private final AcheteurRepository acheteurRepository;

    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    public void addToCart(Integer acheteurId, Integer produitId, Integer quantite) {
        Acheteur acheteur = acheteurRepository.findById(acheteurId).orElseThrow();
        Panier panier = acheteur.getPanier();
        if (panier == null) {
            panier = new Panier();
            panier.setAcheteur(acheteur);
            acheteur.setPanier(panier);
        }
        ArticlePanier article = new ArticlePanier();
        article.setProduit(produitRepository.findById(produitId).orElseThrow());
        article.setQuantite(quantite);
        panier.ajouterArticle(article);
        acheteurRepository.save(acheteur);
    }

    public void checkout(Integer acheteurId) {
        // Implementation of checkout process
    }
}


