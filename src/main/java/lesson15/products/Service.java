package lesson15.products;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Service extends Product {

    public Service(Integer id, String model, Integer price, String manufacturer) {
        super(id, model, price, manufacturer);
    }

    public Service(String model, Integer price, String manufacturer) {
        super(model, price, manufacturer);
    }

    public Service(Integer id) {
        super(id);
    }
}
