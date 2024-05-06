package utils

import models.dao.entities.{Product, ProductItem}
import models.dto.{GetProductDTO, ProductDTO}

trait Mapper {
  def dtoToProduct(p: ProductDTO): Product
  def productToDto(p: Product, pi: ProductItem): GetProductDTO
}

class ModelMapper extends Mapper {

  override def dtoToProduct(p: ProductDTO): Product = {
    var result: Product = null
    result = Product(
      p.id,
      p.title,
      p.price
    )
    result
  }

  override def productToDto(p: Product, pi: ProductItem): GetProductDTO = {
    var result: GetProductDTO = null
    result = GetProductDTO(
      p.id,
      p.title,
      p.price,
      pi.amount,
      pi.isInStock
    )
    result
  }
}
