package financial.price.service;

import java.util.List;

import financial.price.pojo.Price;

public interface ProducerService {

    /**
     * Places a new order.
     *
     * @param order the order to place
     * @return confirmation ID
     */
    String ProducePrice();

    /**
     * Retrieves an order by ID.
     *
     * @param orderId the ID of the order
     * @return the order details
     */
    Price getLastPrices(String priceId); 

    /**
     * Cancels an existing order.
     *
     * @param orderId the ID of the order
     * @return true if cancellation succeeded, false otherwise
     */
    boolean cancelPrice(String orderId);
}

