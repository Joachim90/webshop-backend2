package jke.webshopbackend2.dto;

public record ProductDto(
        String title,
        double price,
        String description,
        String category,
        String image,
        RatingDto rating
) {
    public record RatingDto(
            double rate,
            int count
    ) {
    }
}
