# Bootcamp Santander 2024 - Backend com Java
Java RESTful API criada para o desafio de projeto do Bootcamp Santander 2024 - Backend com Java

## Diagrama de classes:
```mermaid
classDiagram
    class Customer {
        Long id
        String name
        String cpf
        List~Order~ orders
        +Customer()
        +Long getId()
        +void setId(Long id)
        +String getName()
        +void setName(String name)
        +String getCpf()
        +void setCpf(String cpf)
        +List~Order~ getOrders()
        +void setOrders(List~Order~ orders)
        +void addOrder(Order order)
        +void removeOrder(Order order)
    }

    class Order {
        Long id
        BigDecimal totalAmount
        boolean delivered
        boolean paid
        boolean orderCompleted
        Customer customer
        List~Product~ products
        +Order()
        +Long getId()
        +void setId(Long id)
        +BigDecimal getTotalAmount()
        +void setTotalAmount(BigDecimal totalAmount)
        +boolean isDelivered()
        +void setDelivered(boolean delivered)
        +boolean isPaid()
        +void setPaid(boolean paid)
        +boolean isOrderCompleted()
        +void setOrderCompleted(boolean orderCompleted)
        +Customer getCustomer()
        +void setCustomer(Customer customer)
        +List~Product~ getProducts()
        +void setProducts(List~Product~ products)
    }

    class Product {
        Long id
        String name
        BigDecimal price
        +Product()
        +Product(String name, BigDecimal price)
        +Long getId()
        +void setId(Long id)
        +String getName()
        +void setName(String name)
        +BigDecimal getPrice()
        +void setPrice(BigDecimal price)
    }

    Customer "1" --> "0..*" Order : has
    Order "0..*" --> "1" Customer : belongs to
    Order "0..*" --> "0..*" Product : includes
```