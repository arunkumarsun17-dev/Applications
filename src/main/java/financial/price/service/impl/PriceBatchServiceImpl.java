package financial.price.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import financial.price.pojo.Price;
import financial.price.service.PriceBatchService;

public class PriceBatchServiceImpl implements PriceBatchService{

	private final Map<String, List<Price>> inProgress = new ConcurrentHashMap<String, List<Price>>();
    private final Map<String, List<Price>> completed = new ConcurrentHashMap<String, List<Price>>();

    public void startBatch(String batchId) {
        inProgress.put(batchId, Collections.synchronizedList(new ArrayList<Price>()));
        System.out.println("Batch " + batchId + " started.");
    }

    public void uploadChunk(String batchId, List<Price> records) {
        List<Price> batch = inProgress.get(batchId);
        if (batch != null) {
            batch.addAll(records);
            System.out.println("Uploaded chunk of " + records.size() + " records to batch " + batchId);
        }
    }

    public void completeBatch(String batchId) {
        List<Price> batch = inProgress.remove(batchId);
        if (batch != null) {
            completed.put(batchId, Collections.unmodifiableList(new ArrayList<Price>(batch)));
            System.out.println("Batch " + batchId + " completed atomically with " + batch.size() + " records.");
            for (String key : completed.keySet()) {
            	List<Price> prices = completed.get(key);
            	prices.stream()
                .forEach(item -> System.out.print(item.getId()+" => "+item.getPriceValue()+"=> "+item.getAsOf()+"=> \n"));
                
            }
        }
    }

    public void cancelBatch(String batchId) {
        inProgress.remove(batchId);
        System.out.println("Batch " + batchId + " cancelled and discarded.");
    }

    public List<Price> getBatch(String batchId) {
        return completed.get(batchId); // only available if completed
    }

}
