package org.animal

import org.animal.service.AnimalService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServiceTest {

  @Autowired
  AnimalService animalService

  @Test
  void "service is correctly autowired"() {
    Assertions.assertNotNull(animalService)
  }

  @Test
  void "status is correct when starting and stopping the service"() {
    boolean status1 = animalService.status()
    Assertions.assertEquals(false, status1)
    boolean status2 = animalService.start()
    Assertions.assertEquals(true, status2)
    boolean status3 = animalService.status()
    Assertions.assertEquals(true, status3)
    boolean status4 = animalService.stop()
    Assertions.assertEquals(false, status4)
    boolean status5 = animalService.status()
    Assertions.assertEquals(false, status5)
  }
}
