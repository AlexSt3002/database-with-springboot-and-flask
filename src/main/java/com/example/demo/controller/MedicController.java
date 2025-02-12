package com.example.demo.controller;

import com.example.demo.model.Medic;
import com.example.demo.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/medici")
@SessionAttributes("username")
public class MedicController {

    @Autowired
    private MedicRepository medicRepository;

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public MedicController(MedicRepository medicRepository) {
        this.medicRepository = medicRepository;
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/search")
    public String searchMediciBySpecializare(@RequestParam String specializare, Model model) {
        List<Medic> medici = medicRepository.findBySpecializareContainingIgnoreCase(specializare);


        if (medici.isEmpty()) {
            model.addAttribute("error", "Nu exista niciun medic care are specializarea selectata.");
        }

        model.addAttribute("medici", medici);
        model.addAttribute("specializare", specializare);
        model.addAttribute("newMedic", new Medic());
        return "medici";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            model.addAttribute("username", username);
            return "redirect:/medici";
        } else {
            model.addAttribute("error", "Credențiale incorecte.Te rugam sa incerci din nou.");
            return "login";
        }
    }


    @GetMapping
    public String showMedici(Model model) {

        String username = (String) model.getAttribute("username");
        if (username == null) {
            return "redirect:/medici/login";
        }

        List<Medic> medici = medicRepository.findAll();
        model.addAttribute("medici", medici);
        model.addAttribute("newMedic", new Medic());
        return "medici";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newMedic", new Medic());
        return "medici";
    }

    @PostMapping("/create")
    public String createMedic(@RequestParam String nume, @RequestParam String prenume, @RequestParam String sex,
                              @RequestParam String specializare, @RequestParam String email,
                              @RequestParam String telefon, @RequestParam String spital, Model model,RedirectAttributes redirectAttributes) {

        boolean hasError = false;


        Medic newMedic = new Medic();


        if (nume == null || nume.trim().isEmpty()) {
            model.addAttribute("errorNume", "Numele este necesar.");
            hasError = true;
        } else {
            newMedic.setNume(nume);
        }

        if (prenume == null || prenume.trim().isEmpty()) {
            model.addAttribute("errorPrenume", "Prenumele este obligatoriu.");
            hasError = true;
        } else {
            newMedic.setPrenume(prenume);
        }


        if (sex == null || sex.trim().isEmpty() || (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("F"))) {
            model.addAttribute("errorSex", "Sexul trebuie să fie 'M' sau 'F'.");
            hasError = true;
        } else {
            newMedic.setSex(sex);
        }

        if (specializare == null || specializare.trim().isEmpty()) {
            model.addAttribute("errorSpecializare", "Specializarea este obligatorie.");
            hasError = true;
        } else {
            newMedic.setSpecializare(specializare);
        }


        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("errorEmail", "Email-ul este obligatoriu.");
            hasError = true;
        } else if (!email.contains("@")) {
            model.addAttribute("errorEmail", "Email-ul trebuie să conțină caracterul '@'.");
            hasError = true;
        } else {
            newMedic.setEmail(email);
        }


        if (telefon == null || telefon.trim().isEmpty() || !telefon.matches("\\d{10}")) {
            model.addAttribute("errorTelefon", "Telefonul trebuie să conțină exact 10 cifre.");
            hasError = true;
        } else {
            newMedic.setTelefon(telefon);
        }

        if (spital == null || spital.trim().isEmpty()) {
            model.addAttribute("errorSpital", "Spitalul este obligatoriu.");
            hasError = true;
        } else {
            newMedic.setSpital(spital);
        }

        if (hasError) {

            model.addAttribute("newMedic", newMedic);
            return "medici";
        }


        medicRepository.save(newMedic);

        redirectAttributes.addFlashAttribute("message", "Medicul a fost adaugat cu succes.");
        return "redirect:/medici";
    }


    @PostMapping("/update")
    public String updateMedic(@RequestParam Long id, @RequestParam String nume, @RequestParam String prenume,
                              @RequestParam String sex, @RequestParam String specializare, @RequestParam String email,
                              @RequestParam String telefon, @RequestParam String spital, Model model,RedirectAttributes redirectAttributes) {

        boolean hasError = false;


        Optional<Medic> existingMedicOptional = medicRepository.findById(id);
        if (!existingMedicOptional.isPresent()) {
            model.addAttribute("error", "Medic nu găsit.");
            return "medici";
        }

        Medic updatedMedic = existingMedicOptional.get();


        if (nume == null || nume.trim().isEmpty()) {
            model.addAttribute("errorNume", "Numele este obligatoriu.");
            hasError = true;
        } else {
            updatedMedic.setNume(nume);
        }


        if (prenume == null || prenume.trim().isEmpty()) {
            model.addAttribute("errorPrenume", "Prenumele este obligatoriu.");
            hasError = true;
        } else {
            updatedMedic.setPrenume(prenume);
        }


        if (sex == null || sex.trim().isEmpty() || (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("F"))) {
            model.addAttribute("errorSex", "Sexul trebuie să fie 'M' sau 'F'.");
            hasError = true;
        } else {
            updatedMedic.setSex(sex);
        }


        if (specializare == null || specializare.trim().isEmpty()) {
            model.addAttribute("errorSpecializare", "Specializarea este obligatorie.");
            hasError = true;
        } else {
            updatedMedic.setSpecializare(specializare);
        }


        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("errorEmail", "Email-ul este obligatoriu.");
            hasError = true;
        } else if (!email.contains("@")) {
            model.addAttribute("errorEmail", "Email-ul trebuie să conțină caracterul '@'.");
            hasError = true;
        } else {
            updatedMedic.setEmail(email);
        }


        if (telefon == null || telefon.trim().isEmpty() || !telefon.matches("\\d{10}")) {
            model.addAttribute("errorTelefon", "Telefonul trebuie să conțină exact 10 cifre.");
            hasError = true;
        } else {
            updatedMedic.setTelefon(telefon);
        }


        if (spital == null || spital.trim().isEmpty()) {
            model.addAttribute("errorSpital", "Spitalul este obligatoriu.");
            hasError = true;
        } else {
            updatedMedic.setSpital(spital);
        }

        if (hasError) {

            model.addAttribute("medici", medicRepository.findAll());
            model.addAttribute("updatedMedic", updatedMedic);
            return "medici";
        }


        medicRepository.save(updatedMedic);

        redirectAttributes.addFlashAttribute("message", "Medicul a fost actualizat cu succes.");
        return "redirect:/medici";
    }


    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteMedic(@RequestParam Long id,RedirectAttributes redirectAttributes) {
        medicRepository.deleteById(id);
        return "Medicul a fost șters cu succes.";
    }
}
