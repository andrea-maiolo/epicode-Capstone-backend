package andreamaiolo.backend.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Value("${stripe.secret.key}")
    private String stripeSecretkey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretkey;
    }

    public String createCheckoutSession(Long totalInCents, Long bookingId) throws StripeException {
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(totalInCents)
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Booking ref :" + bookingId)
                                                .setDescription("Payment confirmation for your booking")
                                                .build()
                                )
                                .build()
                )
                .setQuantity(1L)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(lineItem)
                .putMetadata("booking_id", String.valueOf(bookingId))
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
