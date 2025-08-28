package org.mybank.springboot.msvc.account.movement;

import org.mybank.springboot.msvc.account.movement.dto.AccountRequestDTO;
import org.mybank.springboot.msvc.account.movement.dto.AccountResponseDTO;
import org.mybank.springboot.msvc.account.movement.entity.Account;
import org.mybank.springboot.msvc.account.movement.mapper.AccountMapper;
import org.mybank.springboot.msvc.account.movement.repository.AccountRepository;
import org.mybank.springboot.msvc.account.movement.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Test
    void testCreateAccount() {
        AccountRequestDTO request = AccountRequestDTO.builder()
                .accountNumber("123456")
                .accountType("Ahorros")
                .initialBalance(BigDecimal.valueOf(1000))
                .state(true)
                .customerId("CUST000111")
                .build();

        Account entity = new Account();
        entity.setAccountNumber(request.getAccountNumber());
        entity.setAccountType(request.getAccountType());
        entity.setInitialBalance(request.getInitialBalance());
        entity.setState(request.getState());
        entity.setCustomerId(request.getCustomerId());

        AccountResponseDTO response = AccountResponseDTO.builder()
                .accountNumber("123456")
                .accountType("Ahorros")
                .initialBalance(BigDecimal.valueOf(1000))
                .state(true)
                .initialBalance(BigDecimal.valueOf(1000))
                .customerId("CUST000111")
                .build();

        Mockito.lenient().when(accountMapper.toEntity(request)).thenReturn(entity);
        Mockito.lenient().when(accountRepository.save(entity)).thenReturn(entity);
        Mockito.lenient().when(accountMapper.toDto(entity)).thenReturn(response);

        AccountResponseDTO result = accountService.createAccount(request);

        Assertions.assertEquals("123456", result.getAccountNumber());
    }
}