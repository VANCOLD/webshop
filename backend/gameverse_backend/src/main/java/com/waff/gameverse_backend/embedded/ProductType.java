package com.waff.gameverse_backend.embedded;

import com.waff.gameverse_backend.datamodel.Product;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor @Setter @Getter
public class ProductType {
    private String className;
    private Class<? extends Product> classType;


    public ProductType(Class classType)
    {
        this.className = classType.getSimpleName();
        this.classType = classType;

    }

}
