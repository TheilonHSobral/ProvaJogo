package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.model.Jogos;
import application.model.JogosRepository;

@Controller
@RequestMapping("/jogos")
public class JogosController {
    @Autowired
    private JogosRepository jogosRepo;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("jogos", jogosRepo.findAll());
        return "/jogos/list";
    }

    @RequestMapping("/insert")
    public String insert() {
        return "/jogos/insert";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam("titulo") String titulo) {
        Jogos jogos = new Jogos();
        jogos.setTitulo(titulo);

        jogosRepo.save(jogos);

        return "redirect:/jogos/list";
    }

    @RequestMapping("/update")
    public String update(Model model, @RequestParam("id") int id) {
        Optional<Jogos> jogos = jogosRepo.findById(id);

        if(jogos.isPresent()) {
            model.addAttribute("jogos", jogos.get());
            return "/jogos/update";
        }

        return "redirect:/jogos/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
        @RequestParam("id") int id,
        @RequestParam("titulo") String titulo
    ) {
        Optional<Jogos> jogos = jogosRepo.findById(id);

        if(jogos.isPresent()) {
            jogos.get().setTitulo(titulo);

            jogosRepo.save(jogos.get());
        }
        
        return "redirect:/jogos/list";
    }

    @RequestMapping("/delete")
    public String delete(Model model, @RequestParam("id") int id) {
        Optional<Jogos> jogos = jogosRepo.findById(id);

        if(jogos.isPresent()) {
            model.addAttribute("jogos", jogos.get());
            return "/jogos/delete";
        }

        return "redirect:/jogos/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") int id) {
        jogosRepo.deleteById(id);

        return "redirect:/jogos/list";
    }
}