package lesson15.products;

import lombok.*;

/**
 * Create 09.06.2020
 *
 * @autor Evtushenko Anton
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {
    private Integer id;
    private String model;
    private Integer price;
    private String manufacturer;

    public Product(String model, Integer price, String manufacturer) {
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public Product(Integer id) {
        this.id = id;
    }
}
