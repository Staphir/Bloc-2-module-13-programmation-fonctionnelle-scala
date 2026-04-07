import org.scalatest.funsuite.AnyFunSuite

class MainTests extends AnyFunSuite {

  val itemAxiom = new Item(id = "1", name = "Axiom", price = 10.1)
  val itemBravos = new Item(id = "2", name = "Bravos", price = 20.2)
  val itemLyra = new Item(id = "3", name = "Lyra", price = 30.3)
  val itemMuna = new Item(id = "4", name = "Muna", price = 40.4)
  val itemOrdis = new Item(id = "5", name = "Ordis", price = 50.5)
  val itemYzmir = new Item(id = "6", name = "Yzmir", price = 60.6)

  val items = List(itemAxiom, itemBravos, itemLyra, itemMuna, itemOrdis, itemYzmir)
  val totalItems = 10.1 + 20.2 + 30.3 + 40.4 + 50.5 + 60.6

  // ----------------------- Utils -----------------------
  def roundToTwoDecimals(value: Double): Double = {
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  // ----------------------- Tests pour calculateTotal -----------------------
  test("calculateTotal should sum all item prices correctly") {
    val order = new Order(orderId = "1", items = items, discountCode = Option("order_20"))
    val result = calculateTotal(order)
    assert(roundToTwoDecimals(result) === 212.1)
  }

  test("calculateTotal with empty items should return 0") {
    val order = new Order(orderId = "2", items = List(), discountCode = None)
    val result = calculateTotal(order)
    assert(result === 0.0)
  }

  test("calculateTotal with single item should return item price") {
    val order = new Order(orderId = "3", items = List(itemAxiom), discountCode = None)
    val result = calculateTotal(order)
    assert(result === 10.1)
  }

  // ----------------------- Tests pour applyDiscount -----------------------
  test("applyDiscount should apply 20% discount correctly") {
    val order = new Order(orderId = "1", items = items, discountCode = Option("order_20"))
    val discountedOrder = applyDiscount(order, 0.2)
    val result = calculateTotal(discountedOrder)
    assert(roundToTwoDecimals(result) === roundToTwoDecimals(212.1 * 0.8))
  }

  test("applyDiscount with 0% should not change total") {
    val order = new Order(orderId = "1", items = items, discountCode = Option("order_0"))
    val discountedOrder = applyDiscount(order, 0.0)
    val result = calculateTotal(discountedOrder)
    assert(roundToTwoDecimals(result) === 212.1)
  }

  test("applyDiscount with 100% should result in 0 total") {
    val order = new Order(orderId = "1", items = items, discountCode = None)
    val discountedOrder = applyDiscount(order, 1.0)
    val result = calculateTotal(discountedOrder)
    assert(roundToTwoDecimals(result) === 0.0)
  }

  // ----------------------- Tests pour processOrders -----------------------
  test("processOrders should apply discounts to matching codes") {
    val orderDiscount20 = new Order(orderId = "1", items = items, discountCode = Option("order_20"))
    val orderDiscount15 = new Order(orderId = "2", items = items, discountCode = Option("order_15"))
    val orders = List(orderDiscount20, orderDiscount15)
    val discounts = Map("order_20" -> 0.2, "order_15" -> 0.15)

    val results = processOrders(orders, discounts)

    assert(results.length === 2)
    assert(roundToTwoDecimals(results.head) === roundToTwoDecimals(212.1 * 0.8))
    assert(roundToTwoDecimals(results(1)) === roundToTwoDecimals(212.1 * 0.85))
  }

  test("processOrders should return original total for non-matching discount codes") {
    val orderDiscount50 = new Order(orderId = "3", items = items, discountCode = Option("order_50"))
    val orders = List(orderDiscount50)
    val discounts = Map("order_20" -> 0.2, "order_15" -> 0.15)

    val results = processOrders(orders, discounts)

    assert(results.length === 1)
    assert(roundToTwoDecimals(results.head) === 212.1)
  }

  test("processOrders should return original total for orders without discount code") {
    val orderNoDiscount = new Order(orderId = "4", items = items, discountCode = None)
    val orders = List(orderNoDiscount)
    val discounts = Map("order_20" -> 0.2)

    val results = processOrders(orders, discounts)

    assert(results.length === 1)
    assert(roundToTwoDecimals(results.head) === 212.1)
  }

  test("processOrders with mixed orders should handle all cases") {
    val orderDiscount20 = new Order(orderId = "1", items = items, discountCode = Option("order_20"))
    val orderDiscount15 = new Order(orderId = "2", items = items, discountCode = Option("order_15"))
    val orderDiscount50 = new Order(orderId = "3", items = items, discountCode = Option("order_50"))
    val orders = List(orderDiscount20, orderDiscount15, orderDiscount50)
    val discounts = Map("order_20" -> 0.2, "order_15" -> 0.15)

    val results = processOrders(orders, discounts)

    assert(results.length === 3)
    assert(roundToTwoDecimals(results.head) === roundToTwoDecimals(212.1 * 0.8))
    assert(roundToTwoDecimals(results(1)) === roundToTwoDecimals(212.1 * 0.85))
    assert(roundToTwoDecimals(results(2)) === 212.1)
  }
}