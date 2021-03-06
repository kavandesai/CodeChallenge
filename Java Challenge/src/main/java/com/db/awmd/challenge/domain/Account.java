package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Account {

  @NotNull
  @NotEmpty
  private final String accountId;

  @NotNull
  @Min(value = 0, message = "Initial balance must be positive.")
  private BigDecimal balance;
  
  @JsonIgnore
  private final Lock lock = new ReentrantLock();

  public Account(String accountId) {
    this.accountId = accountId;
    this.balance = BigDecimal.ZERO;
  }
  
  public Lock lock() {
	  return lock;
  }

  @JsonCreator
  public Account(@JsonProperty("accountId") String accountId,
    @JsonProperty("balance") BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
  }
  
  public void debit (final BigDecimal amountToDebit) {
	  this.balance = this.balance.subtract(amountToDebit);
  }
  
  public void credit (final BigDecimal amountToCredit) {
	  this.balance = this.balance.add(amountToCredit);
  }
}
