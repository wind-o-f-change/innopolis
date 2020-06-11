package lesson15.products;

public class Mobile extends Product {
    public Mobile(Integer id, String model, Integer price, String manufacturer) {
        super(id, model, price, manufacturer);
    }

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

    public Mobile() {
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

    public Mobile(String model, Integer price, String manufacturer) {
        super(model, price, manufacturer);
    }

    public Mobile(Integer id) {
        super(id);
    }
}
