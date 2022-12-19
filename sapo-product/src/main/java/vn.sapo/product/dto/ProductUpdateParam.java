package vn.sapo.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import vn.sapo.media.dto.MediaParam;
import vn.sapo.product_tax.dto.ProductTaxParam;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductUpdateParam implements Serializable {

//    private String title;
//    private String image;
//    private String description;
//    private String unit;
//    private String sku;
//    private String barCode;
//    private Integer quantity;
//    private BigDecimal retailPrice;
//    private BigDecimal importPrice;
//    private BigDecimal wholesalePrice;
//    private Integer categoryId;
//    private Integer brandId;

    private Integer id;
    private String title;
    private String unit;
    private String sku;
    private String description;
    private String barCode;
    private Float mass;
    private BigDecimal retailPrice;
    private BigDecimal importPrice;
    private BigDecimal wholesalePrice;
    private Integer categoryId;
    private Integer brandId;
    private boolean enableSell;
    private boolean applyTax;
    private boolean isTaxInclusive;
    private List<ProductTaxParam> taxList;
    private List<MediaParam> mediaList;

}