package vn.sapo.entities.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.sapo.entities.BaseEntity;
import vn.sapo.entities.media.Media;
import vn.sapo.entities.tax.ProductTax;
import vn.sapo.entities.tax.Tax;
import vn.sapo.entities.tax.TaxType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Accessors(chain = true)
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "mass")
    private float mass;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "sku", length = 50, unique = true)
    private String sku;

    @Column(name = "bar_code", length = 50, unique = true)
    private String barCode;

    @Column(name = "retail_price", precision = 10, scale = 2)
    private BigDecimal retailPrice;

    @Column(name = "import_price", precision = 10, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "wholesale_price", precision = 10, scale = 2)
    private BigDecimal wholesalePrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @Column(name = "brand_id", insertable = false, updatable = false)
    private Integer brandId;

    @Column(name = "apply_tax", nullable = false, columnDefinition = "boolean default false")
    private boolean applyTax;

    @Column(name = "tax_inclusive", nullable = false, columnDefinition = "boolean default false")
    private boolean taxInclusive;
    @OneToMany(mappedBy = "product")
    private Set<Media> mediaSet;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    @OneToMany(mappedBy = "id.product")
//    @OrderBy("tax_id")
    @OrderBy("taxId")
    private Set<ProductTax> productTaxSet;


    public Set<Media> getMediaList() {
        return mediaSet;
    }

    public String getMainImageUrl() {
        if (mediaSet == null)
            return null;
        return mediaSet.stream()
                .filter(Media::isMain)
                .map(Media::getFileUrl)
                .findAny()
                .orElse(null);
    }

    public List<Tax> getTaxList() {
        if (productTaxSet == null)
            return new ArrayList<>();
        return productTaxSet.stream()
                .map(ProductTax::getTax)
                .collect(Collectors.toList());
    }

    public List<Tax> getSaleTaxList() {
        if (productTaxSet == null)
            return new ArrayList<>();
        return productTaxSet.stream()
                .filter(productTax -> productTax.getTaxType() == TaxType.TAX_SALE)
                .map(ProductTax::getTax).collect(Collectors.toList());
    }

    public List<Tax> getPurchaseTaxList() {
        if (productTaxSet == null)
            return new ArrayList<>();
        return productTaxSet.stream()
                .filter(productTax -> productTax.getTaxType() == TaxType.TAX_PURCHASE)
                .map(ProductTax::getTax).collect(Collectors.toList());
    }

    public Product(Integer categoryId, Integer brandId) {
        setCategoryId(categoryId);
        setBrandId(brandId);
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product setCategoryId(Integer categoryId) {
        this.category = new Category(this.categoryId = categoryId);
        return this;
    }

    public Product setBrandId(Integer brandId) {
        this.brand = new Brand(this.brandId = brandId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}