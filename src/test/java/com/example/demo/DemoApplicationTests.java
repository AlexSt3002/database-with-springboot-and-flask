package com.example.demo;

import com.example.demo.controller.MedicController;
import com.example.demo.repository.MedicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.example.demo.model.Medic;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@SpringBootTest
class DemoApplicationTests {
    @Mock
    private MedicRepository medicRepository;

    @Mock
    private Model model;
    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private MedicController medicController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowLoginForm() {
        String viewName = medicController.showLoginForm();
        assertEquals("login", viewName);
    }

    @Test
    public void testLogin_Success() {
        String viewName = medicController.login("user", "password", model);
        assertEquals("redirect:/medici", viewName);
        verify(model).addAttribute("username", "user");
    }

    @Test
    public void testLogin_Failure() {
        String viewName = medicController.login("wronguser", "wrongpassword", model);
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Credențiale incorecte.Te rugam sa incerci din nou.");
    }

    @Test
    public void testShowMedici_WithUsername() {
        when(model.getAttribute("username")).thenReturn("user");

        List<Medic> medicList = new ArrayList<>();
        medicList.add(new Medic());
        when(medicRepository.findAll()).thenReturn(medicList);

        String viewName = medicController.showMedici(model);
        assertEquals("medici", viewName);
        verify(model).addAttribute("medici", medicList);
    }

    @Test
    public void testShowMedici_WithoutUsername() {
        when(model.getAttribute("username")).thenReturn(null);
        String viewName = medicController.showMedici(model);
        assertEquals("redirect:/medici/login", viewName);
    }

    @Test
    public void testSearchMediciBySpecializare_Found() {
        List<Medic> medicList = new ArrayList<>();
        Medic medic = new Medic();
        medic.setSpecializare("Cardiologie");
        medicList.add(medic);

        when(medicRepository.findBySpecializareContainingIgnoreCase("Cardiologie")).thenReturn(medicList);

        String viewName = medicController.searchMediciBySpecializare("Cardiologie", model);

        assertEquals("medici", viewName);
        verify(model).addAttribute("medici", medicList);
        verify(model).addAttribute("specializare", "Cardiologie");
    }

    @Test
    public void testSearchMediciBySpecializare_NotFound() {
        when(medicRepository.findBySpecializareContainingIgnoreCase("Chirurgie")).thenReturn(new ArrayList<>());

        String viewName = medicController.searchMediciBySpecializare("Chirurgie", model);

        assertEquals("medici", viewName);
        verify(model).addAttribute("error", "Nu exista niciun medic care are specializarea selectata.");
    }

    @Test
    public void testCreateMedic_Success() {
        Medic medic = new Medic();
        medic.setNume("Ion");
        medic.setPrenume("Popescu");
        medic.setSex("M");
        medic.setSpecializare("Cardiologie");
        medic.setEmail("ion.popescu@example.com");
        medic.setTelefon("0712345678");
        medic.setSpital("Spitalul Judetean");

        String viewName = medicController.createMedic(medic.getNume(), medic.getPrenume(), medic.getSex(), medic.getSpecializare(), medic.getEmail(), medic.getTelefon(), medic.getSpital(), model,redirectAttributes);

        assertEquals("redirect:/medici", viewName);
        verify(medicRepository).save(any(Medic.class));
    }

    @Test
    public void testCreateMedic_MissingField() {
        String viewName = medicController.createMedic("", "", "", "", "", "", "", model,redirectAttributes);

        assertEquals("medici", viewName);
        verify(model).addAttribute(eq("errorNume"), anyString());
    }

    @Test
    public void testUpdateMedic_Success() {
        Medic medic = new Medic();
        medic.setId(1L);
        medic.setNume("Ion");

        when(medicRepository.findById(1L)).thenReturn(Optional.of(medic));

        String viewName = medicController.updateMedic(1L, "Ion", "Popescu", "M", "Cardiologie", "ion.popescu@example.com", "0712345678", "Spitalul Judetean", model,redirectAttributes);

        assertEquals("redirect:/medici", viewName);
        verify(medicRepository).save(any(Medic.class));
    }

    @Test
    public void testUpdateMedic_NotFound() {
        when(medicRepository.findById(1L)).thenReturn(Optional.empty());

        String viewName = medicController.updateMedic(1L, "Ion", "Popescu", "M", "Cardiologie", "ion.popescu@example.com", "0712345678", "Spitalul Judetean", model,redirectAttributes);

        assertEquals("medici", viewName);
        verify(model).addAttribute("error", "Medic nu găsit.");
    }

    @Test
    public void testDeleteMedic() {
        String viewName = medicController.deleteMedic(1L,redirectAttributes);

        assertEquals("Medicul a fost șters cu succes.", viewName);
        verify(medicRepository).deleteById(1L);
    }
}




