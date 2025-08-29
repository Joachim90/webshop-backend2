package jke.webshopbackend2;

import jke.webshopbackend2.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Test
    void fetchProduct() {
        String url = "https://fakestoreapi.com/products/1";

        Product product = restTemplate.getForObject(url, Product.class);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isGreaterThan(0);
        assertThat(product.getTitle()).isNotBlank();
        assertThat(product.getPrice()).isGreaterThan(0);
        assertThat(product.getDescription()).isNotBlank();
        assertThat(product.getCategory()).isNotBlank();
        assertThat(product.getImage()).isNotBlank();
        assertThat(product.getRating()).isNotNull();
        assertThat(product.getRating().getRate()).isGreaterThan(0);
        assertThat(product.getRating().getCount()).isGreaterThan(0);
    }

    @Test
    void fetchAllProduct() {
        String url = "https://fakestoreapi.com/products";

        Product[] productsArray = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(productsArray);

        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();

        for (Product product : products) {
            assertThat(product.getId()).isGreaterThan(0);
            assertThat(product.getTitle()).isNotBlank();
            assertThat(product.getPrice()).isGreaterThan(0);
            assertThat(product.getDescription()).isNotBlank();
            assertThat(product.getCategory()).isNotBlank();
            assertThat(product.getImage()).isNotBlank();
            assertThat(product.getRating()).isNotNull();
            assertThat(product.getRating().getRate()).isGreaterThan(0);
            assertThat(product.getRating().getCount()).isGreaterThan(0);
        }
    }

    @Test
    void fetchProductsByCategory() {
        String category = "electronics";
        String url = "https://fakestoreapi.com/products/category/" + category;

        Product[] productsArray = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(productsArray);

        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();

        for (Product product : products) {
            assertThat(product.getCategory()).isEqualToIgnoringCase(category);
        }
    }
}
