package models.dao.repositories

import db.SlickDatabase
import models.dao.entities.{Product, ProductItem}
import models.dao.schemas.ProductSchema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table

trait ProductRepository {
  def findProductsByTitle(title: String): List[(Product, ProductItem)]

  def findAllProducts(): List[(Product, ProductItem)]

  def saveProduct(product: Product): Unit

  def deleteProduct(id: String): Unit

  def updateProduct(product: Product): Unit
}

class ProductRepositoryImpl extends ProductRepository with SlickDatabase {
  var products: Table[Product] = ProductSchema.products
  var productItems: Table[ProductItem] = ProductSchema.productItems

  override def findProductsByTitle(title: String): List[(Product, ProductItem)] = {
    transaction {
      from(products, productItems)((p, pi) =>
        where(p.id === pi.id and p.title === title)
          select(p, pi)
          orderBy p.id
      ).toList
    }
  }

  override def findAllProducts(): List[(Product, ProductItem)] = {
    transaction {
      from(products, productItems)((p, pi) =>
        where(p.id === pi.id)
          select(p, pi)
          orderBy p.id
      ).toList
    }
  }

  override def saveProduct(product: Product): Unit = {

    transaction {
      if (!products.exists(p => p.id == product.id)) {
        products.insert(
          product
        )
        productItems.insert(
          ProductItem(
            product.id,
            product.price,
            1,
            true
          )
        )
      } else {
        val oldProductItem = productItems.find(_.id == product.id).get
        oldProductItem.amount += 1
        productItems.update(
          oldProductItem
        )
      }
    }
  }

  override def deleteProduct(id: String): Unit = transaction {
    products.deleteWhere(_.id === id)
    productItems.deleteWhere(_.id === id)
  }

  override def updateProduct(product: Product): Unit = transaction {
    products.update(product)
  }
}
