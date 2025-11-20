package com.ltp.globalsuperstore;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class StoreController {
    List<Item> items = new ArrayList<>();

    @GetMapping("/")
    public String getMethodName(Model model, @RequestParam(required = false) String id) {
        int index = getIndexFromID(id);

        // If no item found, create a new item
        Item item = (index == Constants.NOT_FOUND) ? new Item() : items.get(index);

        // Pass item + category
        model.addAttribute("item", item);
        model.addAttribute("categories", Constants.CATEGORIES);

        return "form";
    }
    
    @PostMapping("/submitItem")
    public String handleSubmit(Item item, RedirectAttributes redirectAttributes) {
        int index = getIndexFromID(item.getId());
        String status = Constants.SUCESS_STATUS;

        // If new item, add it to the list
        if (index == Constants.NOT_FOUND) {
            items.add(item);
        } else {
            // If existing item, only update it if hte date difference is within 5 days
            if (within5days(item.getDate(), items.get(index).getDate())) {
                items.set(index, item);
            } else {
                status = Constants.FAILED_STATUS;
            }
        }

        redirectAttributes.addFlashAttribute("status", status);

        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("items", items);
        return "inventory";
    }

    // Helper to find item index by id
    public int getIndexFromID(String id) {
        if (id == null) return Constants.NOT_FOUND;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId().equals(id)) {
                return index;
            }
        }

        return Constants.NOT_FOUND;
    }

    // Helper to check if the new date is within 5 days
    public boolean within5days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        return days <= 5;
    }
}
