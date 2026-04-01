class Order(val orderId: String, val items: List[Item], val discountCode: Option[String])

def calculateTotal(order: Order): Double = order.items.map(_.price).sum

def applyDiscount(order: Order, discountRate: Double): Order = {
  val discountedItems = order.items.map(item =>
    new Item(item.id, item.name, item.price * (1 - discountRate))
  )
  new Order(order.orderId, discountedItems, order.discountCode)
}
