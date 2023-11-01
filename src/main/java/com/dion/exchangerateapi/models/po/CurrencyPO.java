package com.dion.exchangerateapi.models.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Currency")
@Entity
@EntityListeners(AutoCloseable.class)
public class CurrencyPO implements Serializable {
    private static final long serialVersionUID = 2861305130703731465L;
    @Id
    @Column(nullable = false, unique = true)
    private String code;
    private String chName;
    private BigDecimal rate;
    private String description;
    private boolean active;
    private Date createdDate;
    private Date lastModifiedDate;
}
