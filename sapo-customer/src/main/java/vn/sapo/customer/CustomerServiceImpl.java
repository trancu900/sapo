package vn.sapo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sapo.address.AddressService;
import vn.sapo.customer.dto.CreateCustomerParam;
import vn.sapo.customer.dto.CustomerResult;
import vn.sapo.customer.dto.UpdateCustomerParam;
import vn.sapo.entities.customer.Customer;
import vn.sapo.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

//    @Autowired
//    OrderItemService orderItemService;
//
//    @Autowired
//    PaymentSaleOrderService paymentSaleOrderService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService;
//    @Autowired
//    SaleOrderService saleOrderService;


    @Override
    @Transactional(readOnly = true)
    public CustomerResult findById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cutomer not found"));
        Integer customerId = customer.getId();
        CustomerResult dto = customerMapper.toDTO(customer);
        BigDecimal spendTotal = getSpendTotalByCustomerId(customerId);

        BigDecimal paidTotal = getPaidTotalByCustomerId(customerId);

        dto.setSpendTotal(spendTotal);
        dto.setDebtTotal(spendTotal.subtract(paidTotal));

//        Integer quantityProductOrder = saleOrderService.getQuantityProductOrder(customerResult.getId());
//        if (quantityProductOrder == null) {
//            quantityProductOrder = 0;
//        }
//        customerResult.setQuantityProductOrder(quantityProductOrder);
//        Integer quantityItemOrder = orderItemService.getQuantityItemCustomerOrderById(customerResult.getId());
//        if (quantityItemOrder == null) {
//            quantityItemOrder = 0;
//        }
//        customerResult.setQuantityItemOrder(quantityItemOrder);
//
//        Instant lastDayOrder = saleOrderService.getLastDayOrderByCustomerId(customerResult.getId());
//
//        customerResult.setLastDayOrder(lastDayOrder);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResult> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerResult dto = customerMapper.toDTO(customer);
                    Integer customerId = customer.getId();
                    BigDecimal spendTotal = getSpendTotalByCustomerId(customerId);
                    BigDecimal paidTotal = getPaidTotalByCustomerId(customerId);//paymentSaleOrderService.getPaidTotalByCustomerId(customer.getId());
                    dto.setSpendTotal(spendTotal);
                    dto.setDebtTotal(spendTotal.subtract(paidTotal));
                    return dto;
                }).collect(Collectors.toList());
    }

    public BigDecimal getSpendTotalByCustomerId(Integer customerId) {
        BigDecimal spendTotal = null;// saleOrderService.getSpendTotalByCustomerId(customer.getId());
        if (spendTotal == null)
            spendTotal = BigDecimal.valueOf(0);
        return spendTotal;
    }

    public BigDecimal getPaidTotalByCustomerId(Integer customerId) {
        BigDecimal paidTotal = null;//paymentSaleOrderService.getPaidTotalByCustomerId(customer.getId());
        if (paidTotal == null)
            paidTotal = BigDecimal.valueOf(0);
        return paidTotal;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }


//    @Override
//    @Transactional(readOnly = true)
//    public List<CustomerResult> findCustomerByStatus() {
//        return customerRepository.findCustomersByCustomerStatus()
//                .stream()
//                .map(customer -> {
//                    CustomerResult dto = customerMapper.toDTO(customer);
////                    BigDecimal spendTotal = saleOrderService.getSpendTotalByCustomerId(customer.getId());
////                    if (spendTotal == null)
////                        spendTotal = BigDecimal.valueOf(0);
////                    BigDecimal debtTotal = paymentSaleOrderService.getDebtTotalByCustomerId(customer.getId());
////                    if (debtTotal == null)
////                        debtTotal = BigDecimal.valueOf(0);
////                    dto.setSpendTotal(spendTotal);
////                    dto.setDebtTotal(debtTotal);
//                    return dto;
//                }).collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public CustomerResult create(CreateCustomerParam createCustomerParam) {
        Customer customer = customerMapper.toModel(createCustomerParam);
        customer = customerRepository.save(customer);
        if (customer.getCode() == null)
            customer.setCode("CUZN" + customer.getId());
        return customerMapper.toDTO(customer);
    }

    @Override
    @Transactional
    public CustomerResult update(UpdateCustomerParam updateCustomerParam) {
        Customer customer = customerRepository.findById(updateCustomerParam.getId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        customerMapper.transferFields(updateCustomerParam, customer);
        return customerMapper.toDTO(customer);
    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<SaleOrderResult> findHistoryCustomerOrder(Integer id) {
//        List<SaleOrderResult> saleOrderByCustomer = saleOrderService.findAllSaleOrderByCustomerId(id);
//        return saleOrderByCustomer;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CustomerDebtImpl> findCustomerDebtsByCustomerId(Integer customerId) {
//        List<CustomerDebt> customerDebts = customerRepository.findCustomerDebtsByCustomerId(customerId);
//
//        List<CustomerDebtImpl> customerDebts1 = customerDebts.stream().map(customerDebt -> {
//            CustomerDebtImpl customerDebtImpl = new CustomerDebtImpl();
//            customerDebtImpl.setFromICustomerOwer(customerDebt);
//            return customerDebtImpl;
//        }).collect(Collectors.toList());
//        BigDecimal tam = BigDecimal.valueOf(0);
//        for (CustomerDebtImpl customerDebtImpl : customerDebts1) {
//            tam = tam.add(customerDebtImpl.getTransaction());
//            customerDebtImpl.setTotalDebt(tam);
//            System.out.println(customerDebtImpl.getTransaction());
//        }
//        return customerDebts1;
//    }
//
//    @Override
//    public void deleteById(Integer customerId) {
//        Customer customer = customerRepository.findById(customerId).get();
//        try {
//            shippingAddressService.delete(customer.getShippingAddress().getId());
//            customerRepository.deleteById(customerId);
//        } catch (Exception e) {
//            throw new DataInputException("Lỗi không xác định");
//        }
//
//    }

}