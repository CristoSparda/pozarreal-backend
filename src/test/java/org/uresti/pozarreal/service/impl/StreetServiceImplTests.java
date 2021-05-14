package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        assertEquals(2, streets.size());
        assertEquals("id1", streets.get(0).getId());
        assertEquals("Street 1", streets.get(0).getName());
        assertEquals("id2", streets.get(1).getId());
        assertEquals("Street 2", streets.get(1).getName());
    }


    @Test
    public void WhenStreetServiceImplisAdded(){
        //Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        HousesRepository housesRepository = Mockito.mock(HousesRepository.class);
        RepresentativeRepository representativeRepository = Mockito.mock(RepresentativeRepository.class);
        StreetServiceImpl info = new StreetServiceImpl(streetRepository,representativeRepository,housesRepository);
        String streetID = "108412";


        Street street = new Street();
        street.setName("Test Street");
        Mockito.when(streetRepository.findById(streetID)).thenReturn(Optional.of(street));
        Representative representative = new Representative();
        representative.setName("Cristopher");
        representative.setId("ABC123");
        representative.setStreet("Street Representative");
        representative.setPhone("444498751");
        representative.setAddress("Cerro del faro #1920");
        Mockito.when(representativeRepository.findRepresentativeByStreet(streetID)).thenReturn(representative);
        List<House> list1 = new LinkedList<>();
        House house1 = new House();
        house1.setId("001");
        house1.setStreet("Street001");
        house1.setNumber("004");
        house1.setChipsEnabled(true);
        list1.add(house1);
        House house2 = new House();
        house2.setId("002");
        house2.setStreet("Street002");
        house2.setNumber("009");
        house2.setChipsEnabled(true);
        list1.add(house2);
        House house3 = new House();
        house3.setId("003");
        house3.setStreet("Street003");
        house3.setNumber("003");
        house3.setChipsEnabled(true);
        list1.add(house3);
        Mockito.when(housesRepository.findAllByStreet(streetID)).thenReturn(list1);

        //When:
        StreetInfo streetInfo = info.getStreetInfo(streetID);


        //Then:
        assertEquals(  "Test Street", streetInfo.getName() );
        assertEquals("108412",streetInfo.getId());
        assertEquals("Cristopher",streetInfo.getRepresentative().getName());
        assertEquals("ABC123", streetInfo.getRepresentative().getId());
        assertEquals("Street Representative", streetInfo.getRepresentative().getStreet());
        assertEquals(3,streetInfo.getHouses().size());
        assertEquals("004", streetInfo.getHouses().get(0).getNumber());
        assertEquals("009", streetInfo.getHouses().get(1).getNumber());
        assertEquals("003", streetInfo.getHouses().get(2).getNumber());
        assertEquals("Street003", streetInfo.getHouses().get(2).getStreet());
        assertEquals("003", streetInfo.getHouses().get(2).getId());

    }

}
