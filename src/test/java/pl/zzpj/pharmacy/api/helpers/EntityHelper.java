package pl.zzpj.pharmacy.api.helpers;

import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Employee;
import pl.zzpj.pharmacy.api.model.Medicine;
import pl.zzpj.pharmacy.api.model.MedicineOrder;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.objectDTO.MedicineDTO;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;

import java.util.HashSet;

public class EntityHelper {

    public static ClientDTO prepareClientDTO(Long id) {
        return ClientDTO.builder()
                .id(id)
                .address("jakis")
                .firstName("Marek")
                .lastName("Mostowiak")
                .build();
    }

    public static Client prepareClient(Long id) {
        return Client.builder()
                .id(id)
                .address("jakis")
                .firstName("Marek")
                .lastName("Mostowiak")
                .orders(new HashSet<>())
                .build();
    }

    public static Order prepareOrder(Long id, Client client) {
        return Order.builder()
                .client(client)
                .id(id)
                .build();
    }

    public static OrderDTO prepareOrderDTO(Long id, ClientDTO client) {
        return OrderDTO.builder()
                .client(client)
                .id(id)
                .build();
    }

    public static Medicine prepareMedicine(Long id) {
        return Medicine.builder()
                .id(id)
                .name("Acodin")
                .isPrescript(false)
                .price(3.33)
                .description("Bardzo mocny lek")
                .medicineOrders(new HashSet<>())
                .prescriptions(new HashSet<>())
                .build();
    }

    public static MedicineDTO prepareMedicineDTO(Medicine medicine) {
        return MedicineDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .isPrescript(medicine.isPrescript())
                .price(medicine.getPrice())
                .description(medicine.getDescription())
                .build();
    }
    
    public static MedicineOrder prepareMedicineOrder(Medicine medicine, Order order) {
        return MedicineOrder.builder()
                .amount(4)
                .medicine(medicine)
                .order(order)
                .build();
    }

    public static Employee prepareEmployee1() {
        return Employee.builder()
                .id(1L)
                .firstName("mateuszek")
                .lastName("staruszek")
                .login("mati")
                .password("2345678")
                .build();
    }

    public static Employee prepareEmployee2() {
        return Employee.builder()
                .id(2L)
                .firstName("adam")
                .lastName("tadam")
                .login("Addaaamm")
                .password("2345678")
                .build();
    }

    public static EmployeeDTO prepareEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .build();
    }
}
