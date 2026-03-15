package financial.price.service;

import java.util.List;

import financial.price.pojo.Price;

public interface PriceBatchService {
	void startBatch(String batchId);

    void uploadChunk(String batchId, List<Price> records);

    void completeBatch(String batchId);

    void cancelBatch(String batchId);

    List<Price> getBatch(String batchId); // only available after completion

}
