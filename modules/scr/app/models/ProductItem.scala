package models

import org.squeryl.KeyedEntity
import play.api.libs.json.{Json, Reads, Writes}
import play.api.mvc.PathBindable

case class ProductItem(
                        var id: ProductItemId,
                        var price: ProductItemPrice,
                        var amount: ProductItemAmount,
                        var isInStock: ProductItemIsInStock)


object ProductItem {
  implicit val reads: Reads[ProductItem] = Json.reads[ProductItem]
  implicit val writes: Writes[ProductItem] = Json.writes[ProductItem]
}

case class ProductItemId(raw: String)

object ProductItemId {
  implicit val reads: Reads[ProductItemId] = Json.reads[ProductItemId]
  implicit val writes: Writes[ProductItemId] = Json.writes[ProductItemId]
}

case class ProductItemPrice(raw: Int)

object ProductItemPrice {
  implicit val productPrice: PathBindable[ProductItemPrice] = new PathBindable[ProductItemPrice] {
    override def bind(key: String, value: String): Either[String, ProductItemPrice] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(ProductItemPrice(_))

    override def unbind(key: String, value: ProductItemPrice): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }
  implicit val reads: Reads[ProductItemPrice] = Json.reads[ProductItemPrice]
  implicit val writes: Writes[ProductItemPrice] = Json.writes[ProductItemPrice]
}

case class ProductItemAmount(var raw: Int)

object ProductItemAmount {
  implicit val productPrice: PathBindable[ProductItemAmount] = new PathBindable[ProductItemAmount] {
    override def bind(key: String, value: String): Either[String, ProductItemAmount] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(ProductItemAmount(_))

    override def unbind(key: String, value: ProductItemAmount): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }
  implicit val reads: Reads[ProductItemAmount] = Json.reads[ProductItemAmount]
  implicit val writes: Writes[ProductItemAmount] = Json.writes[ProductItemAmount]
}

case class ProductItemIsInStock(raw: Boolean)

object ProductItemIsInStock {
  implicit val productItemIsInStock: PathBindable[ProductItemIsInStock] = new PathBindable[ProductItemIsInStock] {
    override def bind(key: String, value: String): Either[String, ProductItemIsInStock] =
      implicitly[PathBindable[Boolean]].bind(key, value).right.map(ProductItemIsInStock(_))

    override def unbind(key: String, value: ProductItemIsInStock): String =
      implicitly[PathBindable[Boolean]].unbind(key, value.raw)
  }
  implicit val reads: Reads[ProductItemIsInStock] = Json.reads[ProductItemIsInStock]
  implicit val writes: Writes[ProductItemIsInStock] = Json.writes[ProductItemIsInStock]
}

