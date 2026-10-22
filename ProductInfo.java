public class ProductInfo {
    public final String name;
    public final double price;
    public final String description;
    public final double rating;

    public ProductInfo(String name, double price, String description, double rating) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ProductInfo{name='" + name + "', price=" + price
                + ", description='" + description + "', rating=" + rating + "}";
    }
}
