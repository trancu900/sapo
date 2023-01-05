package vn.sapo.pricingPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sapo.entities.pricing_policy.PricingPolicy;
import vn.sapo.pricingPolicy.dto.PricingPolicyResult;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PricingPolicyServiceImpl implements PricingPolicyService{

    @Autowired
    private PricingPolicyMapper pricingPolicyMapper;

    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;

    @Override
    @Transactional
    public List<PricingPolicyResult> findAll() {
            return pricingPolicyRepository.findAll()
                    .stream()
                    .map(pricingPolicyMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PricingPolicyResult findById(Integer id) {
        PricingPolicy pricingPolicy = pricingPolicyRepository.findById(id).get();
        return pricingPolicyMapper.toDTO(pricingPolicy);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        pricingPolicyRepository.deleteById(id);
    }
}
