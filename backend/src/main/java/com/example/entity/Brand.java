package com.example.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    Long id;

    @Column(name = "brand_name",unique=true)
    String name;

    @Lob
    @Column(name = "image",columnDefinition = "MEDIUMBLOB")
    String image;


}
