package com.bim.inventory.Repository;

import com.bim.inventory.entity.Inventory;
import com.bim.inventory.repository.InventoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InventoryRepositoryTests {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void InventoryRepository_SaveAll_ReturnSavedInventory() {

        //Arrange
        Inventory inventory = Inventory.builder()
                .name("Rock")
                .price(12.50)
                .count(5)
                .description("There are came from Madagascar").build();

        //Act
        Inventory savedInventory = inventoryRepository.save(inventory);

        //Assert
        Assertions.assertThat(savedInventory).isNotNull();
        Assertions.assertThat(savedInventory.getId()).isGreaterThan(0);
    }
}
