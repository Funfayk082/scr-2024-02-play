package models.services

import models._
import models.dto.{GetProductDTO, ProductDTO, ProductItemDTO}

import scala.collection.mutable._


object ProductService {
  var products: MutableList[Product] = MutableList[Product]()
  private val productItemService = ProductItemService

  def saveProduct(productDto: ProductDTO): Product = {
    if (!products.exists(p => p.id.raw == productDto.id)) {
    products += Product(
      ProductId(productDto.id),
      ProductTitle(productDto.title),
      ProductPrice(productDto.price)
    )
  }
    productItemService.addProductItem(
      ProductItemDTO(productDto.id, productDto.price)
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

  def findAllProducts(): List[GetProductDTO] = {
    val productItems = productItemService.getProductItems()
    val result = MutableList[GetProductDTO]()
    products.foreach(p => {
      productItems.find(pi => {
        if(pi.id.raw == p.id.raw) {
          result += GetProductDTO(
            p.id.raw,
            p.title.raw,
            p.price.raw,
            pi.amount.raw,
            pi.isInStock.raw)
          true
        } else {
          false
        }
      })
    })
    result.toList
  }

  def findProductsByTitle(title: String): List[GetProductDTO] = {
    findAllProducts().filter(p => p.title == title)
  }
}
