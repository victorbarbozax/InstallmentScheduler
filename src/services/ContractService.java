package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinepaymentservice;

	public ContractService(OnlinePaymentService onlinepaymentservice) {
		this.onlinepaymentservice = onlinepaymentservice;
	}
	
	public void processContrac(Contract contract, int months) {
		
		double basicQuota = contract.getTotalValue() / months;
		
		for (int i = 1; i<=months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			
			double interest = onlinepaymentservice.interest(basicQuota, i);
			double fee = onlinepaymentservice.paymentFee(basicQuota + interest);
		    double quota = basicQuota + interest + fee;
		    
		    contract.getInstallments().add(new Installment(dueDate, quota));		
		    }
	  }
}
