package org.github.event.controller;

import org.github.event.application.CargoService;
import org.github.event.client.CargoCreateCmd;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjx
 */
@RestController
public class CargoController {
    private CargoService cargoService;
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping("/cargo")
    public String createCargo(@RequestBody CargoCreateCmd cargoCreateCmd){
        cargoService.creatCargo(cargoCreateCmd);
        return "success";
    }
}
