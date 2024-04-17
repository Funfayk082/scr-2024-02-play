package models.services

import models._
import models.dto.ProductDTO

import scala.collection.mutable._


object ProductService {
  var products: MutableList[Product] = MutableList[Product]()
  var productsItems: MutableList[ProductItem] = MutableList[ProductItem]()

  def saveProduct(productDto: ProductDTO): Product = {
    if (products.exists(p => p.id.raw == productDto.id)) {
      throw new RuntimeException("Product already exists")
    }
    products += Product(
      ProductId(productDto.id),
      ProductTitle(productDto.title),
      ProductPrice(productDto.price)
    )
    products.last
  }

  def updateProduct(productDTO: ProductDTO): Option[Product] = {
    val product = Product(
      ProductId(productDTO.id),
      ProductTitle(productDTO.title),
      ProductPrice(productDTO.price)
    )
    products.find(p => {
      if (p.id.raw == product.id.raw) {
        p.price = product.price
        p.title = product.title
        true
      } else {
        false
      }
    })
  }

  def deleteProduct(id: String): Boolean = {
    products = products.filter(p => p.id.raw!= id)
    true
  }

  def findAllProducts(): List[Product] = {
    products.toList
  }

  def findProductsByTitle(title: String): List[Product] = {
    val result: MutableList[Product] = MutableList[Product]()
    products.foreach(p => {
      if (p.title.raw == title) {
        result += p
      }
    })
    result.toList
  }
}
