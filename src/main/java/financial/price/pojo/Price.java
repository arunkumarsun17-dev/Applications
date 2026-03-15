package financial.price.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {
	
	private String id;
	
	private BigDecimal priceValue;
	
	private LocalDateTime asOf;
	
	
	public Price(String id, BigDecimal priceValue, LocalDateTime asOf) {
		super();
		this.id = id;
		this.priceValue = priceValue;
		this.asOf = asOf;
	}
	public BigDecimal getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(BigDecimal priceValue) {
		this.priceValue = priceValue;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getAsOf() {
		return asOf;
	}
	public void setAsOf(LocalDateTime asOf) {
		this.asOf = asOf;
	}

}
