package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class GetProductDTO(id: String, title: String, price: Double, amount: Int, isInStock: Boolean)

object GetProductDTO {
  implicit val writes: Writes[GetProductDTO] = Json.writes[GetProductDTO]
  implicit val reads: Reads[GetProductDTO] = Json.reads[GetProductDTO]
}