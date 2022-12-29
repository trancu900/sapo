package vn.sapo.entities.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.sapo.entities.BaseEntity;
import vn.sapo.entities.Employee;
import vn.sapo.entities.order.purchase.PurchaseOrder;
import vn.sapo.entities.supplier.Supplier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_Id")
    private Supplier supplier;

    @Column(name = "supplier_Id", insertable = false, updatable = false)
    private Integer supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "purchase_order_id", insertable = false, updatable = false)
    private Integer purchaseOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Integer employeeId;

    @Column(name = "sold")
    private Integer sold;

    @Column(name = "available")
    private Integer available;

    @Column(name = "trading")
    private Integer trading;

    @Column(name = "defective")
    private Integer defective;

    public Item(Integer productId, Integer supplierId, Integer orderId, Integer employeeId) {
        setProductId(productId);
        setSupplierId(supplierId);
        setOrderId(orderId);
        setEmployeeId(employeeId);
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item setProductId(Integer productId) {
        this.product = new Product(this.productId = productId);
        return this;
    }

    public Item setSupplierId(Integer supplierId) {
        this.supplier = new Supplier(this.supplierId = supplierId);
        return this;
    }

    public Item setOrderId(Integer purchaseOrderId) {
        this.purchaseOrder = new PurchaseOrder(this.purchaseOrderId = purchaseOrderId);
        return this;
    }

    public Item setEmployeeId(Integer employeeId) {
        this.employee = new Employee(this.employeeId = employeeId);
        return this;
    }

}