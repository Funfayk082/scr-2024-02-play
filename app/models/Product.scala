package models

import play.api.libs.functional.syntax.{functionalCanBuildApplicative, toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.mvc.PathBindable


case class Product(var id: ProductId, var title: ProductTitle, var price: ProductPrice)

object Product {

  implicit val reads: Reads[Product] = Json.reads[Product]

  implicit val writes: Writes[Product] = Json.writes[Product]
}

case class ProductId(raw: String)
object ProductId {
  implicit val reads: Reads[ProductId] = Json.reads[ProductId]
  implicit val writes: Writes[ProductId] = Json.writes[ProductId]
}

case class ProductTitle(raw: String)
object ProductTitle {
  implicit val reads: Reads[ProductTitle] = Json.reads[ProductTitle]
  implicit val writes: Writes[ProductTitle] = Json.writes[ProductTitle]
}

case class ProductPrice(raw: Int)
object ProductPrice {
  implicit val productPrice: PathBindable[ProductPrice] = new PathBindable[ProductPrice] {
    override def bind(key: String, value: String): Either[String, ProductPrice] =
      implicitly[PathBindable[Int]].bind(key, value).right.map(ProductPrice(_))

    override def unbind(key: String, value: ProductPrice): String =
      implicitly[PathBindable[Int]].unbind(key, value.raw)
  }
  implicit val reads: Reads[ProductPrice] = Json.reads[ProductPrice]
  implicit val writes: Writes[ProductPrice] = Json.writes[ProductPrice]
}
