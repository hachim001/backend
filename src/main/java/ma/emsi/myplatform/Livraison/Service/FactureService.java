package ma.emsi.myplatform.Livraison.Service;

import lombok.RequiredArgsConstructor;
import ma.emsi.myplatform.Livraison.Entite.Facture;
import ma.emsi.myplatform.Livraison.Repository.FactureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactureService {
    private final FactureRepository factureRepository;

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getFactureById(Integer id) {
        return factureRepository.findById(id);
    }

    public Facture createFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public Facture updateFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFacture(Integer id) {
        factureRepository.deleteById(id);
    }
}

