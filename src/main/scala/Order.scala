class Order(val orderId: String, val items: List[Item], val discountCode: Option[String])

def calculateTotal(order: Order): Double = {
  val total = order.items.map(_.price).sum
  Math.round(total * 100.0) / 100.0
}

def applyDiscount(order: Order, discountRate: Double): Order = {
  val discountedItems = order.items.map(item => {
    val discountedPrice = Math.round(item.price * (1 - discountRate) * 100.0) / 100.0
    new Item(item.id, item.name, discountedPrice)
  })
  new Order(order.orderId, discountedItems, order.discountCode)
}

def processOrders(orders: List[Order], discounts: Map[String, Double]): List[Double] = {
  orders.map { order =>
    val finalOrder = order.discountCode
      .flatMap(code => discounts.get(code).map(rate => applyDiscount(order, rate)))
      .getOrElse(order)

    val total = calculateTotal(finalOrder)
    Math.round(total * 100.0) / 100.0
  }
}