package com.fetchrewards.receiptprocessor.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @NotNull
    private String retailer;

    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Purchase date must be in format YYYY-MM-DD.")
    private String purchaseDate;

    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Purchase time must be in format HH:MM.")
    private String purchaseTime;

    @NotEmpty
    private List<@Valid @NotNull Item> items;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a valid price format (e.g., 10.99).")
    private String total;
}