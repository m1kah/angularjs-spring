package fi.m1kah.service;

/*
Copyright (c) 2013 Mika Hämäläinen

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

import fi.m1kah.audit.AuditLog;
import fi.m1kah.domain.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    @Cacheable("restaurants")
    @AuditLog(serviceId = "restaurant", serviceName = "getRestaurants")
    public Collection<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        restaurants.add(new Restaurant("Only Hambugers", 3));
        restaurants.add(new Restaurant("Gourmet Place", 5));
        restaurants.add(new Restaurant("Pasta and Pizza", 2));
        restaurants.add(new Restaurant("Traditional Home Made", 4));

        // Let's make our restaurant service really slow so that we can
        // see effect of cache.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        return restaurants;
    }
}
