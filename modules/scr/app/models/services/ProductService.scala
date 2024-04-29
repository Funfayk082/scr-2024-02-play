package models.services

import com.google.inject.Inject
import models.dao.repositories.ProductRepository
import models.dto.{GetProductDTO, ProductDTO}
import utils.Mapper

import scala.collection.mutable

trait ProductService {
  def saveProduct(productDTO: ProductDTO): Unit
  def updateProduct(productDTO: ProductDTO): Unit
  def deleteProduct(productId: String): Unit
  def findAllProducts(): List[GetProductDTO]
  def findProductsByTitle(title: String): List[GetProductDTO]
}

class ProductServiceImpl @Inject()(val productRepository: ProductRepository,
                                   val mapper: Mapper) extends ProductService{
  override def saveProduct(productDTO: ProductDTO): Unit = {
    productRepository.saveProduct(mapper.dtoToProduct(productDTO))
  }

  override def updateProduct(productDTO: ProductDTO): Unit = {
    productRepository.updateProduct(mapper.dtoToProduct(productDTO))
  }


  override def deleteProduct(productId: String): Unit = {
    productRepository.deleteProduct(productId)
  }

  override def findAllProducts(): List[GetProductDTO] = {
    val result = mutable.MutableList[GetProductDTO]()
    productRepository.findAllProducts().foreach(p => {
      result += mapper.productToDto(p._1, p._2)
    })
    result.toList
  }

  override def findProductsByTitle(title: String): List[GetProductDTO] = {
    val result = mutable.MutableList[GetProductDTO]()
    productRepository.findProductsByTitle(title).foreach(p => {
      result += mapper.productToDto(p._1, p._2)
    })
    result.toList
  }
}
