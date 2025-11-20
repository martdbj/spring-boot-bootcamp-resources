package com.ltp.globalsuperstore.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.validation.BindingResult;

import com.ltp.globalsuperstore.Constants;
import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.Repository.StoreRepository;

public class StoreService {
    
    StoreRepository storeRepository = new StoreRepository();

    public Item getItem(int index) {
        return storeRepository.getItem(index);
    }

    public void addItem(Item item) {
        storeRepository.addItem(item);
    }

    public void updateItem(int index, Item item) {
        storeRepository.updateItem(index, item);
    }

    public List<Item> getItems() {
        return storeRepository.getItems();
    }

    public Item getItemById(String id) {
        int index = getIndexFromId(id);
        return index == Constants.NOT_FOUND ? new Item() : getItem(index);
    }

    public int getIndexFromId(String id) {
        for (int index = 0; index < getItems().size(); index++) {
            if (getItem(index).getId().equals(id)) return index;
        }
        return Constants.NOT_FOUND;
    }
    
    public BindingResult handlePriceValidation(Item item, BindingResult result) {
        if (item.getPrice() < item.getDiscount()) {
            result.rejectValue("price", "", "Price cannot be less than discount");
        }
        return result;
    }

    public String handleSubmit(Item item) {
        int index = getIndexFromId(item.getId());
        String status = Constants.SUCCESS_STATUS;
        if (index == Constants.NOT_FOUND) {
            storeRepository.addItem(item);
        } else if (within5Days(item.getDate(), storeRepository.getItem(index).getDate())) {
            storeRepository.updateItem(index, item);
        } else {
            status = Constants.FAILED_STATUS;
        }
        return status;
    }

    public boolean within5Days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (int) (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }
}
