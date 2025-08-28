package org.mybank.springboot.msvc.customer.person;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mybank.springboot.msvc.customer.person.dto.CustomerResponseDTO;
import org.mybank.springboot.msvc.customer.person.entity.Customer;
import org.mybank.springboot.msvc.customer.person.exception.CustomerException;
import org.mybank.springboot.msvc.customer.person.mapper.CustomerMapper;
import org.mybank.springboot.msvc.customer.person.repository.CustomerRepository;
import org.mybank.springboot.msvc.customer.person.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetCustomerByCustomerId() {
        String customerId = "CUST0001";
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Jorge Toasa");

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setCustomerId(customerId);
        customerResponseDTO.setName("Jorge Toasa");

        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = customerService.getCustomerByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Jorge Toasa", result.getName());

        verify(customerRepository, times(1)).findByCustomerId(customerId);
        verify(customerMapper, times(1)).toDto(customer);
    }

    @Test
    void testGetCustomerByCustomerIdNotFound() {
        String customerId = "CUST0051";

        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerService.getCustomerByCustomerId(customerId);
        });

        assertEquals("Customer with id CUST0051 not found", exception.getMessage());
        verify(customerRepository, times(1)).findByCustomerId(customerId);
        verify(customerMapper, never()).toDto(any());
    }
}