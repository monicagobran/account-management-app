import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.bankapp.entity.Transaction;
import com.github.bankapp.repository.TransactionRepository;
import com.github.bankapp.service.TransactionService;

public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeposit() {
        String accountId = "12345678";
        double amount = 100.0;
        doNothing().when(transactionRepository).save(any(Transaction.class));
        Long transactionId = transactionService.deposit(accountId, amount);
        assertNotNull(transactionId);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdraw() {
        String accountId = "12345678";
        double amount = 100.0;
        doNothing().when(transactionRepository).save(any(Transaction.class));
        Long transactionId = transactionService.withdraw(accountId, amount);
        assertNotNull(transactionId);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

}
