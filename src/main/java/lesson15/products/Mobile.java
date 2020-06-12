package lesson15.products;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mobile extends Product {
    public Mobile(Integer id, String model, Integer price, String manufacturer) {
        super(id, model, price, manufacturer);
    }

    public Mobile(String model, Integer price, String manufacturer) {
        super(model, price, manufacturer);
    }

    public Mobile(Integer id) {
        super(id);
    }
}