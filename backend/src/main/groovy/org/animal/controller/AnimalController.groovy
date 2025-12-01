package org.animal.controller

import org.animal.service.AnimalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
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
		animalService.start()
		return true
	}

	/**
	 * Stop the service.
	 * @return {@code true}
	 */
	@GetMapping("/stop")
	boolean stop() {
		animalService.stop()
		return true
	}
}
