package models.dao.entities

import org.squeryl.KeyedEntity

case class ProductItem(
                        var id: String,
                        var price: Double,
                        var amount: Int,
                        var isInStock: Boolean)
  extends KeyedEntity[String]