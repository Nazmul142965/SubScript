package com.patu.subscripts.services;


import com.patu.subscripts.model.Subscription;
import com.patu.subscripts.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public void save(Subscription s) {
        subscriptionRepository.save(s); }
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll(); }
    public void delete(Integer id) {
        subscriptionRepository.deleteById(id); }
    public Subscription getById(Integer id) {
        return subscriptionRepository.findById(id).orElse(null); }

    public List<Subscription> getSortedTimeline() {
        return subscriptionRepository.findAllByOrderByBillingDateAsc();
    }


    public Double calculateTotalForList(List<Subscription> subs) {
        double total = 0.0;
        for (int i = 0; i < subs.size(); i++) {
            Subscription s = subs.get(i);
            if (s.isActive() == true) {
                total = total + s.getAmount();
            }
        }
        return total;
    }


    public Map<String, Double> getCategoryTotalsForList(List<Subscription> subs) {
        Map<String, Double> data = new HashMap<>();
        for (Subscription s : subs) {
            String cat = s.getCategory();
            double price = s.getAmount();


            if (data.containsKey(cat)) {
                data.put(cat, data.get(cat) + price);
            } else {
                data.put(cat, price);
            }
        }
        return data;
    }


    public String getMostExpensiveName(List<Subscription> subs) {
        if (subs.isEmpty()) {
            return "None";
        }
        Subscription expensive = subs.get(0);
        for (Subscription s : subs) {
            if (s.getAmount() > expensive.getAmount()) {
                expensive = s;
            }
        }
        return expensive.getContactName();
    }
}
