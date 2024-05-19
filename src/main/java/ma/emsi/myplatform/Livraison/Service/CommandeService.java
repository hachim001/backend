package ma.emsi.myplatform.Livraison.Service;
import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Commande;
import ma.emsi.myplatform.Livraison.Repository.CommandeRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeRepository commandeRepository;

    public List<Commande> getOrdersByFournisseur(Integer fournisseurId) {
        return commandeRepository.findByFournisseurId(fournisseurId);
    }

    public Optional<Commande> getOrderById(Integer id) {
        return commandeRepository.findById(id);
    }

    public Optional<Commande> updateOrderStatus(Integer id, String status) {
        Optional<Commande> order = commandeRepository.findById(id);
        if (order.isPresent()) {
            Commande commande = order.get();
            commande.setStatus(status);
            commandeRepository.save(commande);
        }
        return order;
    }
}

