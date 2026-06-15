package cyclechronicles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ShopTest {

  @Test
  void acceptsRaceBikeWithEmptyQueue() {
    Shop shop = new Shop();
    Order order = createOrder(Type.RACE, "Anna");

    boolean result = shop.accept(order);

    assertTrue(result);
  }

  @Test
  void rejectsGravelBike() {
    Shop shop = new Shop();
    Order order = createOrder(Type.GRAVEL, "Anna");

    boolean result = shop.accept(order);

    assertFalse(result);
  }

  @Test
  void rejectsEBike() {
    Shop shop = new Shop();
    Order order = createOrder(Type.EBIKE, "Anna");

    boolean result = shop.accept(order);

    assertFalse(result);
  }

  @Test
  void rejectsSecondOrderFromSameCustomer() {
    Shop shop = new Shop();
    Order firstOrder = createOrder(Type.RACE, "Anna");
    Order secondOrder = createOrder(Type.FIXIE, "Anna");

    shop.accept(firstOrder);
    boolean result = shop.accept(secondOrder);

    assertFalse(result);
  }

  @Test
  void acceptsOrderFromDifferentCustomer() {
    Shop shop = new Shop();
    Order firstOrder = createOrder(Type.RACE, "Anna");
    Order secondOrder = createOrder(Type.SINGLE_SPEED, "Bob");

    shop.accept(firstOrder);
    boolean result = shop.accept(secondOrder);

    assertTrue(result);
  }

  @Test
  void acceptsOrderWhenFourOrdersExist() {
    Shop shop = new Shop();

    for (int i = 1; i <= 4; i++) {
      Order existingOrder = createOrder(Type.RACE, "Customer" + i);
      shop.accept(existingOrder);
    }

    Order newOrder = createOrder(Type.FIXIE, "Anna");

    boolean result = shop.accept(newOrder);

    assertTrue(result);
  }

  @Test
  void rejectsOrderWhenFiveOrdersExist() {
    Shop shop = new Shop();

    for (int i = 1; i <= 5; i++) {
      Order existingOrder = createOrder(Type.RACE, "Customer" + i);
      shop.accept(existingOrder);
    }

    Order newOrder = createOrder(Type.FIXIE, "Anna");

    boolean result = shop.accept(newOrder);

    assertFalse(result);
  }

  private Order createOrder(Type type, String customer) {
    Order order = mock(Order.class);

    when(order.getBicycleType()).thenReturn(type);
    when(order.getCustomer()).thenReturn(customer);

    return order;
  }
}
