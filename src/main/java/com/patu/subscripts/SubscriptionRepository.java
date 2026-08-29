package com.patu.subscripts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findAllByOrderByBillingDateAsc();

    List<Subscription> findAllByActiveTrue();


}