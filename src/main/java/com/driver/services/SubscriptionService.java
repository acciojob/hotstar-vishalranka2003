package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){  
        User user  = userRepository.findById(subscriptionEntryDto.getUserId()).orElse(null);
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setStartSubscriptionDate(new Date());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        System.out.println("Subscription Type: " + subscriptionEntryDto.getSubscriptionType());
        int baseAmount = 0;
        int screenPrice =0;
        if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.BASIC){
            baseAmount = 500;
            screenPrice = 200;
        } else if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.PRO){
            baseAmount = 800;
            screenPrice = 250;
            
        } else if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.ELITE){
            baseAmount = 1000;
            screenPrice = 350;
        }
        int total = baseAmount+ (subscriptionEntryDto.getNoOfScreensRequired()) * screenPrice;
        subscription.setTotalAmountPaid(total);
        subscriptionRepository.save(subscription);
        return total;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        //and return the difference of price
        //Hint: use findById function from the SubscriptionDb
        int difference = 0;
        int differencePerScreen = 0;
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new Exception("User not found");
        }
        Subscription subscription = subscriptionRepository.findById(user.getId()).orElse(null);
        if(subscription == null){
            throw new Exception("Subscription not found");
        }
        if(subscription.getSubscriptionType() == SubscriptionType.ELITE){
            throw new Exception("Already the best Subscription");
        }
        if(subscription.getSubscriptionType() == SubscriptionType.BASIC){
            difference = 300;
            differencePerScreen = 50;
            subscription.setSubscriptionType(SubscriptionType.PRO);

        } else if(subscription.getSubscriptionType() == SubscriptionType.PRO){
            difference = 200;
            differencePerScreen = 100;
            subscription.setSubscriptionType(SubscriptionType.ELITE);
        }
        int overallDifference = difference + differencePerScreen * subscription.getNoOfScreensSubscribed();
        subscription.setTotalAmountPaid(subscription.getTotalAmountPaid()+overallDifference);
        subscriptionRepository.save(subscription);

        return difference;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        int totalRevenue = 0;
        for(Subscription subscription : subscriptionList){
            totalRevenue += subscription.getTotalAmountPaid();
        }

        return totalRevenue;
    }

}
