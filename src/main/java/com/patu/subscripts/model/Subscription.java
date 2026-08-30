package com.patu.subscripts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Service name is required")
    private String contactName;

    private String contactMessage;

    @NotNull(message = "Amount is required")
    @Positive(message = "Must be positive")
    private Double amount;

    @NotEmpty(message = "Select a category")
    private String category;

    @NotNull(message = "Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate billingDate;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
}