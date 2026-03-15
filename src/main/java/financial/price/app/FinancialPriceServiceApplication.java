/**
 * 
 */
package financial.price.app;

import financial.price.service.ProducerService;
import financial.price.service.impl.ProducerServiceImpl;

/**
 * @author Arun Kumar
 *
 */
public class FinancialPriceServiceApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Run application from main method.
		ProducerService price = new ProducerServiceImpl();

		// Produce the price using ProducerService interface and its method
		price.ProducePrice();
		
		System.out.println("\n Total Price Ids :");
		ProducerServiceImpl.priceIdList.parallelStream()
        .forEach(System.out::println);

		// Produce the price using ProducerService interface and its method
		//String id = ProducerServiceImpl.priceIdList.get(2);
		//String id = ProducerServiceImpl.priceIdList.get(1);
		String id = ProducerServiceImpl.priceIdList.get(0);
		System.out.println("\n Input id : "+id);
		price.getLastPrices(id);
	}

}
