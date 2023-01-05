package vn.sapo.controllers.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sapo.pricingPolicy.PricingPolicyService;
import vn.sapo.pricingPolicy.dto.PricingPolicyResult;

import java.util.List;

@RestController
@RequestMapping("/api/pricingPolicys")
public class PricingPolicyAPI {
    @Autowired
    private PricingPolicyService pricingPolicyService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(pricingPolicyService.findAll(), HttpStatus.OK);
    }
}
