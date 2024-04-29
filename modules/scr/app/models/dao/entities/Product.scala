package models.dao.entities

import org.squeryl.KeyedEntity

case class Product(
                    var id: String,
                    var title: String,
                    var price: Double
                  ) extends KeyedEntity[String]