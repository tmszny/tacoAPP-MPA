package tms.cloud.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tms.cloud.Ingredient.Type;
import tms.cloud.Ingredient;
import tms.cloud.Order;
import tms.cloud.Taco;
import tms.cloud.data.IngredientRepository;
import tms.cloud.data.TacoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository tacoRepo;

    @ModelAttribute (name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute (name = "design")
    public Taco taco() {
        return new Taco();
    }

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        return "design";
    }

        @PostMapping
        public String processDesign (@ModelAttribute("design") @Valid Taco design, Errors errors, @ModelAttribute("order") Order order, Model model){

            if (errors.hasErrors()) {
                List<Ingredient> ingredients = new ArrayList<>();
                ingredientRepository.findAll().forEach(i -> ingredients.add(i));

                Type[] types = Ingredient.Type.values();
                for (Type type : types) {
                    model.addAttribute(type.toString().toLowerCase(),
                            filterByType(ingredients, type));
                }


                model.addAttribute("design", design);
                return "design";
            }
            Taco saved = tacoRepo.save(design);
            order.addDesign(saved);
            log.info("Przetwarzanie projektu taco: " + design);
            return "redirect:/orders/current";
        }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
