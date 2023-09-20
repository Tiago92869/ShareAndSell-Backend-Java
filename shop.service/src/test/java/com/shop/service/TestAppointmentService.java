package com.shop.service;

import com.shop.service.domain.Appointment;
import com.shop.service.domain.Shop;
import com.shop.service.dto.AppointmentDto;
import com.shop.service.maps.AppointmentMapper;
import com.shop.service.repositories.AppointmentRepository;
import com.shop.service.repositories.ShopRepository;
import com.shop.service.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestAppointmentService {

    @Mock
    private AppointmentRepository appointmentRepository;

    private AppointmentService appointmentService;

    @Mock
    private ShopRepository shopRepository;

    private final Shop sampleShop = new Shop(UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    private final Appointment sampleAppointment1 = new Appointment(
            UUID.fromString("bdba9df0-d55b-4fa6-b58b-6638b9f9bb93"), LocalTime.now(), LocalDate.now(),
            UUID.fromString("42b3421a-aab6-4a45-92ea-ce556d2f5018"), sampleShop);

    private final Appointment sampleAppointment2 = new Appointment(
            UUID.fromString("0cab18a8-224d-45da-906b-51afde318207"), LocalTime.now().minusHours(1),
            LocalDate.now().minusDays(23), UUID.fromString("e643d2d2-211b-4141-ba51-867296873dda"),
            null);

    private final AppointmentDto sampleAppointmentDto = new AppointmentDto(
            UUID.fromString("bdba9df0-d55b-4fa6-b58b-6638b9f9bb93"), LocalTime.now(), LocalDate.now(),
            UUID.fromString("42b3421a-aab6-4a45-92ea-ce556d2f5018"),
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(appointmentRepository, shopRepository);
    }

    @Test
    public void testProductToDto(){

        AppointmentDto result = AppointmentMapper.INSTANCE.appointmentToDto(sampleAppointment1);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleAppointment1.getUserId());
        assertEquals(result.getDate(), sampleAppointment1.getDate());
    }

    @Test
    public void testDtoToProduct(){

        Appointment result = AppointmentMapper.INSTANCE.dtoToAppointment(sampleAppointmentDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleAppointmentDto.getUserId());
        assertEquals(result.getDate(), sampleAppointmentDto.getDate());
    }

    @Test
    public void testGetAllAppointments(){

        when(appointmentRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleAppointment1, sampleAppointment2)));

        Page<AppointmentDto> result = appointmentService
                .getAllAppointments(PageRequest.of(0, 10), null, null);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllAppointmentShopId(){

        when(appointmentRepository.findByShopId(any(Pageable.class), eq(sampleAppointmentDto.getShopId())))
                .thenReturn(new PageImpl<>(List.of(sampleAppointment1)));

        Page<AppointmentDto> result = appointmentService
                .getAllAppointments(PageRequest.of(0, 10), sampleAppointmentDto.getShopId(), null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllAppointmentUserId(){

        when(appointmentRepository.findByUserId(any(Pageable.class), eq(sampleAppointmentDto.getUserId())))
                .thenReturn(new PageImpl<>(List.of(sampleAppointment1)));

        Page<AppointmentDto> result = appointmentService
                .getAllAppointments(PageRequest.of(0, 10), null, sampleAppointmentDto.getUserId());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllAppointmentShopIdAndUserId(){

        when(appointmentRepository.findByShopIdAndUserId(any(Pageable.class), eq(sampleAppointmentDto.getShopId()),
                eq(sampleAppointmentDto.getUserId()))).thenReturn(new PageImpl<>(List.of(sampleAppointment1)));

        Page<AppointmentDto> result = appointmentService
                .getAllAppointments(PageRequest.of(0, 10), sampleAppointmentDto.getShopId(),
                        sampleAppointmentDto.getUserId());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAppointmentById(){

        when(appointmentRepository.findById(sampleAppointmentDto.getId()))
                .thenReturn(Optional.of(sampleAppointment1));

        AppointmentDto result = appointmentService.getAppointmentById(sampleAppointmentDto.getId());

        assertNotNull(result);
        assertEquals(result.getId(), sampleAppointmentDto.getId());
    }

    @Test
    public void testCreateAppointment(){

        when(appointmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        AppointmentDto result = appointmentService.createAppointment(sampleAppointmentDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleAppointmentDto.getUserId());
    }

    @Test
    public void testUpdateAppointment(){

        when(appointmentRepository.findById(sampleAppointmentDto.getId()))
                .thenReturn(Optional.of(sampleAppointment1));

        when(appointmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        AppointmentDto result = appointmentService.updateAppointment(sampleAppointmentDto.getId(), sampleAppointmentDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleAppointmentDto.getUserId());

    }

    @Test
    public void testdeleteAppointment(){

        when(appointmentRepository.findById(sampleAppointmentDto.getId())).thenReturn(Optional.of(sampleAppointment1));

        assertDoesNotThrow(() -> appointmentService.deleteAppointment(sampleAppointment1.getId()));
    }

    @Test
    public void testDeleteAppointmentByUserId(){

        List<Appointment> appointments = new ArrayList<>();

        appointments.add(sampleAppointment2);

        when(appointmentRepository.findByUserIdAndFutureDateTime(sampleAppointmentDto.getUserId())).thenReturn(appointments);

        appointmentService.deleteAppointmentByUserId(sampleAppointmentDto.getUserId());

        verify(appointmentRepository).deleteAll(appointments);
    }
}
