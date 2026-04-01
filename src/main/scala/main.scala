@main
def main(): Unit = {
  val itemAxiom = new Item(id = "1", name = "Axiom", price = 10.1)
  val itemBravos = new Item(id = "2", name = "Bravos", price = 20.2)
  val itemLyra = new Item(id = "3", name = "Lyra", price = 30.3)
  val itemMuna = new Item(id = "4", name = "Muna", price = 40.4)
  val itemOrdis = new Item(id = "5", name = "Ordis", price = 50.5)
  val itemYzmir = new Item(id = "6", name = "Yzmir", price = 60.6)

  val items = List(itemAxiom, itemBravos, itemLyra, itemMuna, itemOrdis, itemYzmir)

  val order = new Order(orderId = "1", items = items, discountCode = Option("order_20"))

  println(s"Sum order : ${calculateTotal(order)}")
  val orderDiscount = applyDiscount(order, 0.1)
  println(s"Sum order : ${calculateTotal(orderDiscount)}")
}