package default_crud_app.controller;

import default_crud_app.model.Customer;
import default_crud_app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //STANDARD OPERATIONS
    @PostMapping()
    public String create(@ModelAttribute("customer") Customer customer) {
        customerService.add(customer.getName(), customer.getSurname());
        return "redirect:/customer";
    }

    @GetMapping()
    public String index(@ModelAttribute("customer") Customer customer, Model model) {
        model.addAttribute("customerList", customerService.getList());
        return "customer/index";
    }

    @GetMapping("/searching")
    public String find(@ModelAttribute("customer") Customer customer, Model model) {
        model.addAttribute("customerList", customerService.find(customer));
        return "customer/index";
    }

    @PostMapping("/{id}")
    public String edit(@ModelAttribute("customer") Customer customer, @PathVariable("id") Long id) {
        customerService.edit(id, customer);
        return "redirect:/customer";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        customerService.remove(id);
        return "redirect:/customer";
    }

    //VIEWS
    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "customer/new";
    }

    @GetMapping("/{id}")
    public String showCustomer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/show";
    }

    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/edit";
    }

    //TEST API'S

    @GetMapping("/generate")
    public String generateCustomers() {
        customerService.generate();
        return "redirect:/customer";
    }

}
