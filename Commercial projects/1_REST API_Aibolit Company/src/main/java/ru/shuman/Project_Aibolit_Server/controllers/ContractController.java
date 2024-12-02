package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.ContractDTO;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.services.ContractService;
import ru.shuman.Project_Aibolit_Server.util.errors.ContractErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ContractNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ContractNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.ContractIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.ContractValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.collectErrorsToString;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;
    private final ContractValidator contractValidator;
    private final ContractIdValidator contractIdValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public ContractController(ContractService contractService, ContractValidator contractValidator, ContractIdValidator contractIdValidator, ModelMapper modelMapper) {
        this.contractService = contractService;
        this.contractValidator = contractValidator;
        this.contractIdValidator = contractIdValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> sendListContracts() {

        List<Contract> contracts = contractService.findAll();

        List<ContractDTO> contractDTOList = contracts.stream().map(this::convertToContractDTO).collect(Collectors.toList());

        return new ResponseEntity<>(contractDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> sendOneContract(@PathVariable(value = "id") int contractId,
                                                       @ModelAttribute(value = "contract") Contract contract,
                                                       BindingResult bindingResult) {

        contract.setId(contractId);

        contractIdValidator.validate(contract, bindingResult);

        collectErrorsToString(bindingResult, ContractNotFoundException.class);

        ContractDTO contractDTO = convertToContractDTO(contractService.findById(contractId).get());

        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ContractDTO contractDTO,
                                             BindingResult bindingResult) {

        Contract contract = convertToContract(contractDTO);

        contractValidator.validate(contract, bindingResult);

        collectErrorsToString(bindingResult, ContractNotCreatedOrUpdatedException.class);

        contractService.create(contract);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid ContractDTO contractDTO,
                                             BindingResult bindingResult) {

        Contract contract = convertToContract(contractDTO);

        contractIdValidator.validate(contract, bindingResult);
        contractValidator.validate(contract, bindingResult);

        collectErrorsToString(bindingResult, ContractNotCreatedOrUpdatedException.class);

        contractService.update(contract);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения ContractNotFound
    @ExceptionHandler
    private ResponseEntity<ContractErrorResponse> handleException(ContractNotFoundException e) {
        ContractErrorResponse response = new ContractErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения ContractNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<ContractErrorResponse> handleException(ContractNotCreatedOrUpdatedException e) {
        ContractErrorResponse response = new ContractErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод конверсии из DTO в модель
    private Contract convertToContract(ContractDTO contractDTO) {
        return this.modelMapper.map(contractDTO, Contract.class);
    }

    // Метод конверсии из модели в DTO
    private ContractDTO convertToContractDTO(Contract contract) {
        return this.modelMapper.map(contract, ContractDTO.class);
    }
}
