package lesson15.products;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Accessory extends Product {
    public Accessory(Integer id, String model, Integer price, String manufacturer) {
        super(id, model, price, manufacturer);
    }

    public Accessory(String model, Integer price, String manufacturer) {
        super(model, price, manufacturer);
    }

    public Accessory(Integer id) {
        super(id);
    }
}
