package edu.softwareengineeringproject3773.controller;

public class OrderConfirmationController {

    public void setConfirmationData(
            String orderNumber,
            String deliveryMethod,
            double total,
            String email
    ) {
        confirmationOrderNumberLabel.setText(orderNumber);
        confirmationDateLabel.setText(
                java.time.LocalDate.now().format(
                        java.time.format.DateTimeFormatter
                                .ofPattern("MMMM d, yyyy")
                )
        );

        confirmationDeliveryLabel.setText(deliveryMethod);

        confirmationTotalLabel.setText(
                String.format("$%.2f", total)
        );

        confirmationEmailLabel.setText(
                "A confirmation email will be sent to "
                        + email + "."
        );
    }
}
