package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Paiement;
import ma.emsi.myplatform.Livraison.Repository.PaiementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementService {
    private final PaiementRepository paiementRepository;

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Optional<Paiement> getPaiementById(Integer id) {
        return paiementRepository.findById(id);
    }

    public Paiement createPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public Paiement updatePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public void deletePaiement(Integer id) {
        paiementRepository.deleteById(id);
    }
}

