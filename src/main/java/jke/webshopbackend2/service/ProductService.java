package jke.webshopbackend2.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jke.webshopbackend2.model.Product;
import jke.webshopbackend2.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // Hämtar från API var 5e minut [minuter * sekunder * millisekunder]
    public void fetchProducts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("https://fakestoreapi.com/products");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        try(InputStream in = con.getInputStream()) {
            Product[] products = mapper.readValue(in, Product[].class);
            productRepository.saveAll(Arrays.asList(products));
            System.out.println("Products fetched successfully");
        }

    }
}
