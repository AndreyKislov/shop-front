package ua.kislov.shop_front.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.*;
import ua.kislov.shop_front.utils.ImageUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private long id;

    @NotEmpty(message = "Name is empty")
    @Size(min = 1, max = 100, message = "Name length must be between 1 and 100 characters")
    private String name;

    @NotEmpty(message = "Description is empty")
    @Size(min = 1, max = 500, message = "Description length must be between 1 and 500 characters")
    private String description;

    @Min(value = 0, message = "Cost must be a positive number")
    private int cost;
    private byte[] byteImage;

    public String convertToBase64() {
        return ImageUtils.convertToBase64(byteImage);
    }
}
