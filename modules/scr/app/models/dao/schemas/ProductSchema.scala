package models.dao.schemas

import org.squeryl.Schema
import models.dao.entities.{Product, ProductItem}

object ProductSchema extends Schema{
  val products = table[Product]
  val productItems = table[ProductItem]
}