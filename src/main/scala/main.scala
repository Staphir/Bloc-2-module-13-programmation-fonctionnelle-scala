@main
def main(): Unit = {
  val itemAxiom = new Item(id = "1", name = "Axiom", price = 10.1)
  val itemBravos = new Item(id = "2", name = "Bravos", price = 20.2)
  val itemLyra = new Item(id = "3", name = "Lyra", price = 30.3)
  val itemMuna = new Item(id = "4", name = "Muna", price = 40.4)
  val itemOrdis = new Item(id = "5", name = "Ordis", price = 50.5)
  val itemYzmir = new Item(id = "6", name = "Yzmir", price = 60.6)

  val items = List(itemAxiom, itemBravos, itemLyra, itemMuna, itemOrdis, itemYzmir)

  val orderDiscount20 = new Order(orderId = "1", items = items, discountCode = Option("order_20"))
  val orderDiscount15 = new Order(orderId = "1", items = items, discountCode = Option("order_15"))
  val orderDiscount50 = new Order(orderId = "1", items = items, discountCode = Option("order_50"))

  println(s"CalculateTotal : ${calculateTotal(orderDiscount20)}")

  val orderDiscount = applyDiscount(orderDiscount20, 0.2)
  println(s"ApplyDiscount + CalculateTotal : ${calculateTotal(orderDiscount)}")

  println(s"ProcessOrders : " +
    s"${processOrders(
      List(orderDiscount20, orderDiscount15, orderDiscount50),
      Map("order_20" -> 0.2, "order_15" -> 0.15))}")
}