package com.store.batch.batch;

import com.store.batch.user.model.User;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User item) throws Exception {
        // If the user's age is greater than 17, keep the user; otherwise, filter them out.
        if (item.getAge() > 17) {
            return item;  // Keep the user in the processing flow.
        }
        return null;  // Filter out users with age 17 or younger.
    }
}

