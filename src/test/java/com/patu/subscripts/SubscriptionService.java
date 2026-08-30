package com.patu.subscripts;


import com.patu.subscripts.model.Subscription;
import com.patu.subscripts.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@SpringBootTest
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Test
    public void save(Subscription s) {
        subscriptionRepository.save(s); }

    @Test
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll(); }

    @Test
    public void delete(Integer id) {
        subscriptionRepository.deleteById(id); }

    @Test
    public Subscription getById(Integer id) {
        return subscriptionRepository.findById(id).orElse(null); }

    @Test
    public List<Subscription> getSortedTimeline() {
        return subscriptionRepository.findAllByOrderByBillingDateAsc();
    }

    @Test
    public Double calculateTotalBurn() {
        double total = 0.0;
        for (Subscription sub : subscriptionRepository.findAllByActiveTrue()) {
            total += sub.getAmount();
        }
        return total;
    }

    @Test
    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> data = new HashMap<>();
        for (Subscription s : subscriptionRepository.findAll()) {
            data.put(s.getCategory(), data.getOrDefault(s.getCategory(), 0.0) + s.getAmount());
        }
        return data;
    }
}
