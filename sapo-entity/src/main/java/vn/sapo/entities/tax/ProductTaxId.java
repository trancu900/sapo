package vn.sapo.entities.tax;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.sapo.entities.product.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class ProductTaxId implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tax_id")
    private Tax tax;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type")
    private TaxType taxType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTaxId that = (ProductTaxId) o;
        return Objects.equals(product, that.product) && Objects.equals(tax, that.tax) && Objects.equals(taxType, that.taxType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, tax, taxType);
    }
}
