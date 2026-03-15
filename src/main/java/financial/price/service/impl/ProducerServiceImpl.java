package financial.price.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import financial.price.pojo.Price;
import financial.price.service.PriceBatchService;
import financial.price.service.ProducerService;

public class ProducerServiceImpl implements ProducerService{
	PriceBatchService service;
	//public static Map<String,List<String>> batchIdList = new HashMap<>();
	String lastBatchId;
	static public List<String> priceIdList = new ArrayList<>();
	private String priceId;
	public String ProducePrice() {
		service = new PriceBatchServiceImpl();
        String batchId = "batch-001";

        service.startBatch(batchId);

        // Upload chunks in parallel
        IntStream.range(0, 1).parallel().forEach(chunk -> {
        	priceId = "AAPL";
        	priceIdList.add(priceId);
            List<Price> records = IntStream.range(0, 1000)
                    .mapToObj(i -> new Price(
                    		priceId,
                            BigDecimal.valueOf(175 + i * 0.01),
                            LocalDateTime.now()))
                    .toList();

            service.uploadChunk(batchId, records);
        });
        IntStream.range(0, 1).parallel().forEach(chunk -> {
        	priceId = "BSNL";
        	priceIdList.add(priceId);
            List<Price> records = IntStream.range(0, 1000)
                    .mapToObj(i -> new Price(
                    		priceId,
                            BigDecimal.valueOf(185 + i * 0.01),
                            LocalDateTime.now()))
                    .toList();

            service.uploadChunk(batchId, records);
        });
        IntStream.range(0, 1).parallel().forEach(chunk -> {
        	priceId = "MTNL";
        	priceIdList.add(priceId);
            List<Price> records = IntStream.range(0, 1000)
                    .mapToObj(i -> new Price(
                    		priceId,
                            BigDecimal.valueOf(195 + i * 0.01),
                            LocalDateTime.now()))
                    .toList();

            service.uploadChunk(batchId, records);
        });

        // Complete batch atomically
        service.completeBatch(batchId);
        lastBatchId=batchId;
        // Consumers can now access the batch
        List<Price> finalBatch = service.getBatch(batchId);
        //batchIdList.put(batchId,priceIdList);
        System.out.println("Consumer sees " + finalBatch.size() + " records at once.");
		return null;
	}

	public Price getLastPrices(String id) {
        //Get data from last batch id.
		//int listSize = ids.size()-1;
          //String lastBatchId =   ids.get(listSize);
        List<Price> records = service.getBatch(lastBatchId);
      
        Map<String, Price> result = records.stream().filter(price -> price.getId().equalsIgnoreCase(id))
                    .collect(Collectors.toMap(
                            Price::getId,
                            r -> r,
                            (r1, r2) -> r1.getAsOf().isAfter(r2.getAsOf()) ? r1 : r2
                    ));
        for (String key : result.keySet()) {
            Price price = result.get(key);
            System.out.println("Last price Record : "+price.getId() + " => value :" + price.getPriceValue()+" => Date: "+ price.getAsOf());
            return price;
        }
		return null;
        
       
    }

	public boolean cancelPrice(String id) {
		return false;
	}

}
