package lesson15.products;


public class Accessory extends Product {
    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public void setModel(String model) {
        super.setModel(model);
    }

    @Override
    public void setPrice(Integer price) {
        super.setPrice(price);
    }

    @Override
    public void setManufacturer(String manufacturer) {
        super.setManufacturer(manufacturer);
    }

    public Accessory() {
        super();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public String getModel() {
        return super.getModel();
    }

    @Override
    public Integer getPrice() {
        return super.getPrice();
    }

    @Override
    public String getManufacturer() {
        return super.getManufacturer();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Accessory(String model, Integer price, String manufacturer) {
        super(model, price, manufacturer);
    }

    public Accessory(Integer id) {
        super(id);
    }
}
