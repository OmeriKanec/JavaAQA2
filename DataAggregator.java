import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class DataAggregator {
    public ProductInfo aggregateProductInfo(String productName) {
        return aggregateProductInfoAsync(productName).join();
    }

    public CompletableFuture<ProductInfo> aggregateProductInfoAsync(String productName) {
        CompletableFuture<Double> price = CompletableFuture
                .supplyAsync(() -> fetchPrice(productName))
                .exceptionally(error -> 0.0);
        CompletableFuture<String> description = CompletableFuture
                .supplyAsync(() -> fetchDescription(productName))
                .exceptionally(error -> "Нет данных");
        CompletableFuture<Double> rating = CompletableFuture
                .supplyAsync(() -> fetchRating(productName))
                .exceptionally(error -> 0.0);

        return price.thenCombine(description,
                        (p, d) -> new ProductInfo(productName, p, d, 0.0))
                .thenCombine(rating,
                        (info, r) -> new ProductInfo(info.name, info.price, info.description, r));
    }

    protected double fetchPrice(String productName) {
        simulateSource("Цена");
        return 899.99;
    }

    protected String fetchDescription(String productName) {
        simulateSource("Описание");
        return "Ноутбук для работы и учёбы";
    }

    protected double fetchRating(String productName) {
        simulateSource("Рейтинг");
        return 4.7;
    }

    private void simulateSource(String sourceName) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3001));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ожидание источника прервано: " + sourceName, e);
        }

        if (ThreadLocalRandom.current().nextInt(5) == 0) {
            throw new RuntimeException("Источник недоступен: " + sourceName);
        }
    }

    public static void main(String[] args) {
        ProductInfo product = new DataAggregator().aggregateProductInfo("Ноутбук");
        System.out.println(product);
    }
}
