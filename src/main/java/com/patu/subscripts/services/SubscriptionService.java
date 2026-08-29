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

    public Double calculateTotalBurn() {
        double total = 0.0;
        for (Subscription sub : subscriptionRepository.findAllByActiveTrue()) {
            total += sub.getAmount();
        }
        return total;
    }

    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> data = new HashMap<>();
        for (Subscription s : subscriptionRepository.findAll()) {
            data.put(s.getCategory(), data.getOrDefault(s.getCategory(), 0.0) + s.getAmount());
        }
        return data;
    }
}
