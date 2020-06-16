package pl.zzpj.pharmacy.api.helpers;

import pl.zzpj.pharmacy.api.model.Client;
import pl.zzpj.pharmacy.api.model.Employee;
import pl.zzpj.pharmacy.api.model.Medicine;
import pl.zzpj.pharmacy.api.model.MedicineOrder;
import pl.zzpj.pharmacy.api.model.Order;
import pl.zzpj.pharmacy.api.objectDTO.ClientDTO;
import pl.zzpj.pharmacy.api.objectDTO.EmployeeDTO;
import pl.zzpj.pharmacy.api.objectDTO.OrderDTO;

import java.util.HashSet;

public class EntityHelper {

    public static ClientDTO prepareClientDTO(Long id) {
        return new ClientDTO().builder()
                              .id(id)
                              .address("jakis")
                              .firstName("Marek")
                              .lastName("Mostowiak")
                              .build();
    }

    public static Client prepareClient(Long id) {
        return new Client().builder()
                           .id(id)
                           .address("jakis")
                           .firstName("Marek")
                           .lastName("Mostowiak")
                           .orders(new HashSet<>())
                           .build();
    }

    public static Order prepareOrder(Long id, Client client) {
        return new Order().builder()
                          .client(client)
                          .id(id)
                          .build();
    }

    public static OrderDTO prepareOrderDTO(Long id, ClientDTO client) {
        return new OrderDTO().builder()
                             .client(client)
                             .id(id)
                             .build();
    }

    public static Medicine prepareMedicine(Long id) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.set_prescript(false);
        medicine.setName("Acodin");
        return medicine;
    }

    public static MedicineOrder prepareMedicineOrder(Medicine medicine, Order order) {
        MedicineOrder medicineOrder = new MedicineOrder();
        medicineOrder.setAmount(4);
        medicineOrder.setMedicine(medicine);
        medicineOrder.setOrder(order);
        return medicineOrder;
    }

    public static Employee prepareEmployee1(){
        return Employee.builder()
                .id(1L)
                .firstName("mateuszek")
                .lastName("staruszek")
                .login("mati")
                .password("2345678")
                .build();
    }

    public static Employee prepareEmployee2(){
        return Employee.builder()
                .id(2L)
                .firstName("adam")
                .lastName("tadam")
                .login("Addaaamm")
                .password("2345678")
                .build();
    }

    public static EmployeeDTO prepareEmployeeDTO(Employee employee){
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .build();
    }
}
