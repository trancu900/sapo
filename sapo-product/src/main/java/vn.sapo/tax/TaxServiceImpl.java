package vn.sapo.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.sapo.entities.tax.Tax;
import vn.sapo.exceptions.NotFoundException;
import vn.sapo.product_tax.dto.ProductTaxResult;
import vn.sapo.tax.dto.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements TaxService {
    @Autowired
    TaxRepository taxRepository;

    @Autowired
    TaxMapper taxMapper;

    @Override
    @Transactional
    public List<TaxResult> findAll() {
        return taxRepository.findAll()
                .stream()
                .map(taxMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaxResult findById(Integer id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new NotFoundException("Tax not found"));
        return taxMapper.toDTO(tax);
    }

    @Override
    public boolean existsById(Integer id) {
        return taxRepository.existsById(id);
    }

    @Override
    @Transactional
    public TaxResult create(CreateTaxParam taxParam) {
        return taxMapper.toDTO(taxRepository.save(taxMapper.toModel(taxParam)));
    }

    @Override
    public List<TaxResult> findAllByProductId(List<ProductTaxResult> productTaxResults) {
        List<TaxResult> result = new ArrayList<>();
        for (ProductTaxResult productTaxResult : productTaxResults) {
            result.add(taxMapper.toDTO(taxRepository.findById(productTaxResult.getTaxId()).get()));
        }
        return result;
    }
}
