package vn.sapo.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.sapo.tax.dto.TaxParam;
import vn.sapo.tax.dto.TaxResult;

import java.util.List;

@RestController
@RequestMapping("/api/taxes")
public class TaxAPI {
    @Autowired
    TaxService taxService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<TaxResult> taxes =  taxService.findAll();
        System.out.println(taxes);
        return new ResponseEntity<>(taxes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTax(@RequestBody TaxParam taxParam){
        TaxResult taxResult = taxService.create(taxParam);
        return new ResponseEntity<>(taxResult, HttpStatus.OK);
    }
}
