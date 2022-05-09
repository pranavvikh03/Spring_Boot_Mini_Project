package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.AmcRepo;
import com.paytmmoney.sipmanagement.model.Amc;
import com.paytmmoney.sipmanagement.model.Scheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
    @ExtendWith(SpringExtension.class)
class AmcServiceTest {

    @MockBean
    private AmcRepo amcRepo;

    @InjectMocks
    private AmcService amcService;

    @Captor
    private ArgumentCaptor<Amc> amcArgumentCaptor;

//    @BeforeEach
//    void setUp() {
//    }

//    @Test
//    void fetchAllAmcs_whenAmcAvailable() {
//        System.out.println("Data"+amcRepo.findAll());
//        assertThat(amcRepo.findAll().toArray()).isEqualTo(amcService.fetchAllAmcs().toArray());
//    }

    @Test
    void fetchAllAmcs_whenNoAmcPresent(){
        System.out.println("Data"+amcRepo.findAll());
        assertThat(amcRepo.findAll().toArray()).isEmpty();
    }

    @Test
    void addNewAmc() {
        Scheme scheme =  Scheme.builder()
                .schemeId("sh1")
                .rtaSchemeCode("Code1")
                .series("series 1")
                .isinNo("ABC123")
                .schemeName("Practice Scheme 1")
                .build();
        Amc amc = Amc.builder()
                .amcId("AMC1")
                .name("Paytm Money")
                .schemes(List.of(scheme))
                .build();
        amcService.addNewAmc(amc);
        Mockito.verify(amcRepo, Mockito.times(1)).save(amcArgumentCaptor.capture());
    }

//    @Test
//    void fetchAmcUniquely() {
//    }
//
//    @Test
//    void deleteAmc() {
//    }
}