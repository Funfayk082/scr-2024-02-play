package models.services

import models.{ProductItem, ProductItemAmount, ProductItemId, ProductItemIsInStock, ProductItemPrice}
import models.dto.{GetProductDTO, ProductItemDTO}

import scala.collection.mutable.MutableList

object ProductItemService {
  var productItems: MutableList[ProductItem] = MutableList[ProductItem]()

  def addProductItem(productItem: ProductItemDTO): Unit = {
    if (!productItems.exists(p => p.id.raw == productItem.id)) {
      productItems += ProductItem(
        ProductItemId(productItem.id),
        ProductItemPrice(productItem.price),
        ProductItemAmount(1),
        ProductItemIsInStock(true)
      )
    }
    else {
      productItems.find(p => {
        if (p.id.raw == productItem.id) {
          p.amount.raw = p.amount.raw + 1
          true
        } else false
      })
    }
  }

  def getProductItems: List[ProductItem] = {
    productItems.toList
  }
}
