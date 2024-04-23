package controllers

import models.Product
import models.dto.{GetProductDTO, ProductDTO}
import models.services.ProductService
import play.api.data.Forms.{boolean, nonEmptyText, number}
import play.api.data.{Form, Forms, Mapping}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

object ProductController extends Controller {
  private val productsService = ProductService

  def getProducts(title: String): Action[AnyContent] = Action {
    if (title.isEmpty) {
      Ok(Json.toJson(productsService.findAllProducts()))
    } else {
      Ok(Json.toJson(productsService.findProductsByTitle(title)))
    }
  }

  val mapping: Mapping[ProductDTO] = Forms.mapping(
    "id" -> nonEmptyText,
    "title" -> nonEmptyText,
    "price" -> number
  )(ProductDTO.apply)(ProductDTO.unapply)

  val form: Form[ProductDTO] = Form(mapping)

  def saveProduct: Action[AnyContent] = Action { implicit rc =>
    var productDTO = ProductDTO("", "", 0)
    form.bindFromRequest.fold(
      _ => BadRequest,
      dto =>
        productDTO = dto
    )
    Ok(Json.toJson(productsService.saveProduct(productDTO)))
  }

  def updateProduct(): Action[AnyContent] = Action { implicit rc =>
    form.bindFromRequest.fold(
      _ => BadRequest,
      dto =>
        Ok(Json.toJson(productsService.updateProduct(dto)))
    )
  }

  def deleteProduct(id: String): Action[AnyContent] = Action {
    productsService.deleteProduct(id)
    Ok("Product deleted")
  }
}
