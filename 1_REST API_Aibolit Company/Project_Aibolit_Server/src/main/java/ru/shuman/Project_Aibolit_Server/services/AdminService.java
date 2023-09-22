package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.util.ApplicationContextProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final TypeEmploymentService typeEmploymentService;
    private final TypeContractService typeContractService;
    private final TypeDocService typeDocService;
    private final TypeRelationshipWithPatientService typeRelationshipWithPatientService;
    private final GenderService genderService;
    private final BloodService bloodService;
    private final RegionService regionService;
    private final RoleService roleService;

    private final ApplicationContextProvider context;

    @Autowired
    public AdminService(TypeEmploymentService typeEmploymentService, TypeContractService typeContractService,
                        TypeDocService typeDocService, TypeRelationshipWithPatientService typeRelationshipWithPatientService,
                        GenderService genderService, BloodService bloodService, RegionService regionService,
                        RoleService roleService, ApplicationContextProvider context) {
        this.typeEmploymentService = typeEmploymentService;
        this.typeContractService = typeContractService;
        this.typeDocService = typeDocService;
        this.typeRelationshipWithPatientService = typeRelationshipWithPatientService;
        this.genderService = genderService;
        this.bloodService = bloodService;
        this.regionService = regionService;
        this.roleService = roleService;
        this.context = context;
    }

    public List<Table> fillTables() {
        List<Table> tables = new ArrayList<>();
        tables.add(new Table("Тип занятости для родителей"));
        tables.add(new Table("Тип договора"));
        tables.add(new Table("Тип документа"));
        tables.add(new Table("Тип отношения родителя к пациенту"));
        tables.add(new Table("Гендерный признак"));
        tables.add(new Table("Группа крови"));
        tables.add(new Table("Регионы"));
        tables.add(new Table("Роли"));
        return tables;
    }

    private class Container {
        private Class<?> serviceClass;
        private Class<?> model;
        private String nameForNewValue;

        public Class<?> getServiceClass() {
            return serviceClass;
        }

        public void setServiceClass(Class<?> serviceClass) {
            this.serviceClass = serviceClass;
        }

        public Class<?> getModel() {
            return model;
        }

        public void setModel(Class<?> model) {
            this.model = model;
        }

        public String getNameForNewValue() {
            return nameForNewValue;
        }

        public void setNameForNewValue(String nameForNewValue) {
            this.nameForNewValue = nameForNewValue;
        }
    }

    private Container formServiceClassAndModel(Table table, Value value) {
        Container container = new Container();
        switch (table.getTableName()) {
            case "Тип занятости для родителей":
                container.setServiceClass(TypeEmploymentService.class);
                container.setModel(TypeEmployment.class);
                if (value != null) {
                    container.setNameForNewValue(typeEmploymentService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Тип договора":
                container.setServiceClass(TypeContractService.class);
                container.setModel(TypeContract.class);
                if (value != null) {
                    container.setNameForNewValue(typeContractService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Тип документа":
                container.setServiceClass(TypeDocService.class);
                container.setModel(TypeDoc.class);
                if (value != null) {
                    container.setNameForNewValue(typeDocService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Тип отношения родителя к пациенту":
                container.setServiceClass(TypeRelationshipWithPatientService.class);
                container.setModel(TypeRelationshipWithPatient.class);
                if (value != null) {
                    container.setNameForNewValue(typeRelationshipWithPatientService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Гендерный признак":
                container.setServiceClass(GenderService.class);
                container.setModel(Gender.class);
                if (value != null) {
                    container.setNameForNewValue(genderService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Группа крови":
                container.setServiceClass(BloodService.class);
                container.setModel(Blood.class);
                if (value != null) {
                    container.setNameForNewValue(bloodService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Регионы":
                container.setServiceClass(RegionService.class);
                container.setModel(Region.class);
                if (value != null) {
                    container.setNameForNewValue(regionService.findById(value.getValueId()).get().getName());
                }
                break;

            case "Роли":
                container.setServiceClass(RoleService.class);
                container.setModel(Role.class);
                if (value != null) {
                    container.setNameForNewValue(roleService.findById(value.getValueId()).get().getName());
                }
                break;
        }
        return container;
    }

    public List<Value> fillValues(Table table) {

        List<Value> values = new ArrayList<>();
        Container container = formServiceClassAndModel(table, null);

        Class<?> serviceClass = container.getServiceClass();
        Class<?> model = container.getModel();

        try {
            Method findAll = serviceClass.getMethod("findAll");
            Object instanceOfServiceClass = context.getContext().getBean(serviceClass);

            List<Object> objects = (List<Object>) findAll.invoke(instanceOfServiceClass);
            for (Object o: objects) {
                Method getId = model.getMethod("getId");
                Method getName = model.getMethod("getName");
                values.add(new Value((Integer) getId.invoke(o), (String) getName.invoke(o)));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return values;
    }

    public String fillNameForNewValue(Table table, Value value) {

        Container container = formServiceClassAndModel(table, value);

        return container.getNameForNewValue();
    }

    @Transactional
    public void create(NewValue newValue) {

        Container container = formServiceClassAndModel(new Table(newValue.getTableNameForNewValue()), null);

        Class<?> serviceClass = container.getServiceClass();
        Class<?> model = container.getModel();

        try {
            Object createdValue = model.newInstance();
            Method setName = model.getMethod("setName", String.class);
            setName.invoke(createdValue, newValue.getNewValueName());

            Method create = serviceClass.getMethod("create", model);

            Object instanceOfServiceClass = context.getContext().getBean(serviceClass);
            create.invoke(instanceOfServiceClass, createdValue);

            Method getId = model.getMethod("getId");
            newValue.setNewValueId((Integer) getId.invoke(createdValue));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void update(CurrentValue currentValue) {

        Container container = formServiceClassAndModel(new Table(currentValue.getTableNameForCurrentValue()), null);

        Class<?> serviceClass = container.getServiceClass();
        Class<?> model = container.getModel();

        try {
            Object updatedValue = model.newInstance();

            Method setId = model.getMethod("setId", Integer.class);
            setId.invoke(updatedValue, currentValue.getCurrentValueId());

            Method setName = model.getMethod("setName", String.class);
            setName.invoke(updatedValue, currentValue.getCurrentValueName());

            Method update = serviceClass.getMethod("update", model);

            Object instanceOfServiceClass = context.getContext().getBean(serviceClass);
            update.invoke(instanceOfServiceClass, updatedValue);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void delete(CurrentValue currentValue) {

        Container container = formServiceClassAndModel(new Table(currentValue.getTableNameForCurrentValue()), null);

        Class<?> serviceClass = container.getServiceClass();
        Class<?> model = container.getModel();

        try {
            Object deletedValue = model.newInstance();

            Method setId = model.getMethod("setId", Integer.class);
            setId.invoke(deletedValue, currentValue.getCurrentValueId());

            Method delete = serviceClass.getMethod("delete", model);

            Object instanceOfServiceClass = context.getContext().getBean(serviceClass);
            delete.invoke(instanceOfServiceClass, deletedValue);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public class Table {
        private final String tableName;

        public Table(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return this.tableName;
        }
    }

    public class Value {
        private final Integer valueId;
        private final String valueName;

        public Value(Integer valueId, String valueName) {
            this.valueId = valueId;
            this.valueName = valueName;
        }

        public String getValueName() {
            return this.valueName;
        }

        public Integer getValueId() {
            return this.valueId;
        }
    }

    public class NewValue {
        private Integer newValueId;
        private String newValueName;
        private String tableNameForNewValue;

        public NewValue(Integer newValueId, String newValueName, String tableNameForNewValue) {
            this.newValueId = newValueId;
            this.newValueName = newValueName;
            this.tableNameForNewValue = tableNameForNewValue;
        }

        public Integer getNewValueId() {
            return newValueId;
        }

        public void setNewValueId(Integer newValueId) {
            this.newValueId = newValueId;
        }

        public String getNewValueName() {
            return this.newValueName;
        }

        public String getTableNameForNewValue() {
            return this.tableNameForNewValue;
        }

        public void setTableNameForNewValue(String tableNameForNewValue) {
            this.tableNameForNewValue = tableNameForNewValue;
        }
    }

    public class CurrentValue {

        private Integer currentValueId;
        private String currentValueName;
        private String tableNameForCurrentValue;

        public CurrentValue(Integer currentValueId, String currentValueName, String tableNameForCurrentValue) {
            this.currentValueId = currentValueId;
            this.currentValueName = currentValueName;
            this.tableNameForCurrentValue = tableNameForCurrentValue;
        }

        public Integer getCurrentValueId() {
            return currentValueId;
        }

        public void setCurrentValueId(Integer currentValueId) {
            this.currentValueId = currentValueId;
        }

        public String getCurrentValueName() {
            return this.currentValueName;
        }

        public String getTableNameForCurrentValue() {
            return this.tableNameForCurrentValue;
        }

        public void setCurrentValueName(String currentValueName) {
            this.currentValueName = currentValueName;
        }

        public void setTableNameForCurrentValue(String tableNameForCurrentValue) {
            this.tableNameForCurrentValue = tableNameForCurrentValue;
        }
    }

}
