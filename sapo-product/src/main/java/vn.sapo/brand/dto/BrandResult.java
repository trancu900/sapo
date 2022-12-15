package vn.sapo.brand.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BrandResult {

    private Integer id;
    private String name;

}