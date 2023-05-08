package ua.kislov.shop_front.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private long id;
    private String name;
    private String description;
    private int cost;
    private String url;
}

