import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.bankapp.controller.AccountController;
import com.github.bankapp.dto.AccountResponse;
import com.github.bankapp.dto.BalanceResponse;
import com.github.bankapp.dto.ErrorResponse;
import com.github.bankapp.dto.TransactionRequest;
import com.github.bankapp.dto.TransactionResponse;
import com.github.bankapp.service.AccountService;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpenAccount() {
        String accountId = "12345678";
        when(accountService.openAccount()).thenReturn(accountId);

        ResponseEntity<?> responseEntity = accountController.openAccount();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(accountId, ((AccountResponse) responseEntity.getBody()).getAccountId());
    }

    @Test
    public void testOpenAccount_handleException() {
        String errMsg = "Error";
        when(accountService.openAccount()).thenThrow(new RuntimeException(errMsg));

        ResponseEntity<?> responseEntity = accountController.openAccount();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to create account: " + errMsg, ((ErrorResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testDepositFunds() {
        String accountId = "12345678";
        Long transactionId = 1L;
        double amount = 100.0;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);

        when(accountService.deposit(accountId, amount)).thenReturn(transactionId);

        ResponseEntity<?> responseEntity = accountController.depositFunds(accountId, transactionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionId, ((TransactionResponse) responseEntity.getBody()).getTransactionId());
    }

    @Test
    public void testDepositFunds_handleException() {
        String errMsg = "Error";
        String accountId = "12345678";
        double amount = 100.0;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);

        when(accountService.deposit(accountId, amount)).thenThrow(new RuntimeException(errMsg));

        ResponseEntity<?> responseEntity = accountController.depositFunds(accountId, transactionRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to deposit funds: " + errMsg, ((ErrorResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testWithdrawFunds() {
        String accountId = "12345678";
        Long transactionId = 1L;
        double amount = 100.0;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);

        when(accountService.withdraw(accountId, amount)).thenReturn(transactionId);

        ResponseEntity<?> responseEntity = accountController.withdrawFunds(accountId, transactionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionId, ((TransactionResponse) responseEntity.getBody()).getTransactionId());
    }

    @Test
    public void testWithdrawFunds_handleException() {
        String errMsg = "Error";
        String accountId = "12345678";
        double amount = 100.0;
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(amount);

        when(accountService.withdraw(accountId, amount)).thenThrow(new RuntimeException(errMsg));

        ResponseEntity<?> responseEntity = accountController.withdrawFunds(accountId, transactionRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to withdraw funds: " + errMsg, ((ErrorResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testCheckBalance() {
        String accountId = "12345678";
        double balance = 100.0;

        when(accountService.checkBalance(accountId)).thenReturn(balance);

        ResponseEntity<?> responseEntity = accountController.checkBalance(accountId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(balance, ((BalanceResponse) responseEntity.getBody()).getBalance());
    }

    @Test
    public void testCheckBalance_handleException() {
        String errMsg = "Error";
        String accountId = "12345678";

        when(accountService.checkBalance(accountId)).thenThrow(new RuntimeException(errMsg));

        ResponseEntity<?> responseEntity = accountController.checkBalance(accountId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to fetch balance: " + errMsg, ((ErrorResponse) responseEntity.getBody()).getMessage());
    
    }
}
