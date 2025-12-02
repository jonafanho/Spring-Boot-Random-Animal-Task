package org.animal.controller

import org.animal.Main
import org.animal.service.AnimalService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
// For debugging locally
@CrossOrigin(origins = Main.CROSS_ORIGIN)
@RequestMapping("/api")
class AnimalController {

	private final AnimalService animalService

	AnimalController(AnimalService animalService) {
		this.animalService = animalService
	}

	/**
	 * Start the service to generate random animals.
	 * @return {@code true}
	 */
	@GetMapping("/start")
	boolean start() {
		return animalService.start()
	}

	/**
	 * Stop the service.
	 * @return {@code true}
	 */
	@GetMapping("/stop")
	boolean stop() {
		return animalService.stop()
	}

	/**
	 * @return whether the service is running
	 */
	@GetMapping("/status")
	boolean status() {
		return animalService.status()
	}
}
