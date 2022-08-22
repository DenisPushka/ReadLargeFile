package com.example.readLargeFile.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@lombok.Data
@Entity
@Table(name = "receive_data")
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    @Id
    private BigInteger id;

    private String name;
    private String text;
    private BigInteger number;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ElementCollection
    @Transient
    private List<UUID> numbers;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ElementCollection
    @Transient
    private List<String> array_text;
}
