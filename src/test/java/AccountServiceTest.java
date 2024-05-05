import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.bankapp.entity.Account;
import com.github.bankapp.repository.AccountRepository;
import com.github.bankapp.service.AccountService;
import com.github.bankapp.service.TransactionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpenAccount() {
        doNothing().when(accountRepository).save(any(Account.class));
        String accountId = accountService.openAccount();
        verify(accountRepository).save(any(Account.class));
        assertNotNull(accountId);
    }

    @Test
    public void testDeposit(){
        String accountId = "12345678";
        double depositAmount = 100.0;
        double originalAmount = 500.0;
        Account account = new Account(accountId);
        account.setBalance(originalAmount);

        when(accountRepository.findByAccountId(accountId)).thenReturn(account);
        when(transactionService.deposit(accountId, depositAmount)).thenReturn(1L);

        Long transactionId = accountService.deposit(accountId, depositAmount);
        assertEquals(1L, transactionId);
        assertEquals(originalAmount + depositAmount, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDeposit_AccountNotFound(){
        String accountId = "12345678";
        when(accountRepository.findByAccountId(accountId)).thenReturn(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(accountId, 1.0);
        });
        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testDeposit_NegativeAmount(){
        String accountId = "12345678";
        double amount = -100.0;
        Account account = new Account(accountId);
        when(accountRepository.findByAccountId(accountId)).thenReturn(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(accountId, amount);
        });
        assertEquals("Deposit amount must be positive.", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    
    @Test
    public void testWithdraw(){
        String accountId = "12345678";
        double withdrawAmount = 100.0;
        double originalAmount = 500.0;
        Account account = new Account(accountId);
        account.setBalance(originalAmount);

        when(accountRepository.findByAccountId(accountId)).thenReturn(account);
        when(transactionService.withdraw(accountId, withdrawAmount)).thenReturn(1L);

        Long transactionId = accountService.withdraw(accountId, withdrawAmount);
        assertEquals(1L, transactionId);
        assertEquals(originalAmount - withdrawAmount, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testWithdraw_AccountNotFound(){
        String accountId = "12345678";
        when(accountRepository.findByAccountId(accountId)).thenReturn(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(accountId, 1.0);
        });
        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testWithdraw_NegativeAmount(){
        String accountId = "12345678";
        double amount = -100.0;
        Account account = new Account(accountId);
        when(accountRepository.findByAccountId(accountId)).thenReturn(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(accountId, amount);
        });
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testWithdraw_InvalidAmount(){
        String accountId = "12345678";
        double withdrawAmount = 100.0;
        double originalAmount = 50.0;
        Account account = new Account(accountId);
        account.setBalance(originalAmount);

        when(accountRepository.findByAccountId(accountId)).thenReturn(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(accountId, withdrawAmount);
        });

        assertEquals("Withdrawal amount must be greater than current balance.", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testCheckBalance(){
        String accountId = "12345678";
        double amount = 100.0;
        Account account = new Account(accountId);
        account.setBalance(amount);

        when(accountRepository.findByAccountId(accountId)).thenReturn(account);

        double balance = accountService.checkBalance(accountId);
        assertEquals(balance, amount);
    }

}

