package lk.ijse.dep11.to;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Item implements Serializable {
    private String code;            // $F{code}
    private String description;     // $F{description}
    private int quantity;                // $F{qty}
    private BigDecimal price;       // $F{price}
}
