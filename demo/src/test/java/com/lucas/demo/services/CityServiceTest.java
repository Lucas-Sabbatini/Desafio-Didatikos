package com.lucas.demo.services;

import com.lucas.demo.models.City;
import com.lucas.demo.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testFindAll() {
        // Cenário: criando duas cidades de exemplo
        City city1 = new City(1L, "City 1");
        City city2 = new City(2L, "City 2");
        List<City> cities = Arrays.asList(city1, city2);

        // Configurando o mock para retornar a lista de cidades
        when(cityRepository.findAll()).thenReturn(cities);

        // Execução: invoca o método do service
        List<City> result = cityService.findAll();

        // Validação: garante que a lista retornada não é nula e contém 2 cidades
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cityRepository, times(1)).findAll();
    }
}

