package ru.shuman.Project_Aibolit_Server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.services.AdminService;
import ru.shuman.Project_Aibolit_Server.services.AdminService.Table;
import ru.shuman.Project_Aibolit_Server.services.AdminService.Value;
import ru.shuman.Project_Aibolit_Server.services.AdminService.NewValue;
import ru.shuman.Project_Aibolit_Server.services.AdminService.CurrentValue;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminPanel(@ModelAttribute("table") Table table,
                             @ModelAttribute("value") Value value,
                             @ModelAttribute("currentValue") CurrentValue currentValue,
                             @ModelAttribute("newValue") NewValue newValue,
                             Model model) {

        List<Table> tables = adminService.fillTables();
        model.addAttribute("tables", tables);

        if (table.getTableName() == null) {
            table = tables.get(0);
        }

        List<Value> values = adminService.fillValues(table);
        model.addAttribute("values", values);

        if (value.getValueId() == null && value.getValueName() == null && values.size() > 0) {
            value = values.get(0);
            model.addAttribute("value", value);
        }

        newValue.setTableNameForNewValue(table.getTableName());
        model.addAttribute("newValue", newValue);

        if (value.getValueId() != null || value.getValueName() != null) {
            String nameForNewValue = adminService.fillNameForNewValue(table, value);

            currentValue.setCurrentValueId(value.getValueId());
            currentValue.setCurrentValueName(nameForNewValue);
            currentValue.setTableNameForCurrentValue(table.getTableName());

            model.addAttribute("updatedValue", currentValue);
        }

        return "/admin/adminpanel";
    }

    @PostMapping
    public String create(@ModelAttribute("newValue") NewValue newValue) {

        if (newValue.getNewValueName().equals("") || newValue.getNewValueName() != null) {
            adminService.create(newValue);
        }

        String tableName = URLEncoder.encode(newValue.getTableNameForNewValue(), StandardCharsets.UTF_8);

        return "redirect:/admin?tableName=" + tableName + "&valueId=" + newValue.getNewValueId();
    }

    @PatchMapping
    public String update(@ModelAttribute("currentValue") CurrentValue currentValue) {

        if (currentValue.getCurrentValueName() != null) {
            adminService.update(currentValue);
        }

        String tableName = URLEncoder.encode(currentValue.getTableNameForCurrentValue(), StandardCharsets.UTF_8);

        return "redirect:/admin?tableName=" + tableName + "&valueId=" + currentValue.getCurrentValueId();
    }

    @DeleteMapping
    public String delete(@ModelAttribute("currentValue") CurrentValue currentValue) {

        if (currentValue.getCurrentValueId() != null) {
            adminService.delete(currentValue);
        }

        String tableName = URLEncoder.encode(currentValue.getTableNameForCurrentValue(), StandardCharsets.UTF_8);

        return "redirect:/admin?tableName=" + tableName;
    }
}
