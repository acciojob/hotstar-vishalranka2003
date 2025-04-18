package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        User SavedUser = userRepository.save(user);
        return SavedUser.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        //and then check for each webseries if the user can watch it or not
        //If the user is not present in the DB then return 0

        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return 0;
        }
        List<WebSeries> webSeriesList = webSeriesRepository.findAll();
        int count = 0;
        int userAge = user.getAge();
        SubscriptionType userSubscription = user.getSubscription().getSubscriptionType();
        for(WebSeries webSeries : webSeriesList){
            if(webSeries.getAgeLimit() <= user.getAge()){
                System.out.println("Age Limit: " + webSeries.getAgeLimit() + ", User Age: " + user.getAge());
                System.out.println("User Subscription: " + userSubscription + ", WebSeries Subscription: " + webSeries.getSubscriptionType());
                if(userSubscription == SubscriptionType.BASIC && webSeries.getSubscriptionType() == SubscriptionType.BASIC){
                    count++;
                } else if(userSubscription == SubscriptionType.PRO && (webSeries.getSubscriptionType() == SubscriptionType.BASIC || webSeries.getSubscriptionType() == SubscriptionType.PRO)){
                    count++;
                } else if(userSubscription == SubscriptionType.ELITE){
                    count++;
                }
            }
        }


        return count;
    }


}
