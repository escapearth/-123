package org.open.market.repository;

import org.open.market.model.cart.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRedisRepository extends CrudRepository<Cart, String> {
}
