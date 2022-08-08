package com.example.readLargeFile.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.UUIDArrayType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@lombok.Data
@Entity
@Table(name = "receive_data")
//@Table(name = "buffer")
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "uuid-array",
                typeClass = UUIDArrayType.class
        )
})
public class Data {

    @Id
    private BigInteger id;

    private String name;
    private String text;
    private BigInteger number;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ElementCollection
    @Type(type = "uuid-array")
    @OrderColumn(
            name = "number_2",
            columnDefinition = "uuid[]"
    )
    private UUID[] arrayNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ElementCollection
    @Type(type = "string-array")
    @OrderColumn(
            name = "text_array",
            columnDefinition = "text[]"
    )
    private String[] arrayText;
}
