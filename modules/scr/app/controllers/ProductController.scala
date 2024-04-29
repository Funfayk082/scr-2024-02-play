package controllers

import com.google.inject.Inject
import models.dto.ProductDTO
import models.services.ProductService
import play.api.data.Forms.{nonEmptyText, text}
import play.api.data.{Form, Forms, Mapping}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ProductController @Inject()(val productService: ProductService) extends Controller {

  def getProducts(title: String): Action[AnyContent] = Action {
    if (title.isEmpty) {
      Ok(Json.toJson(productService.findAllProducts()))
    } else {
      Ok(Json.toJson(productService.findProductsByTitle(title)))
    }
  }

  val mapping: Mapping[ProductDTO] = Forms.mapping(
    "id" -> nonEmptyText,
    "title" -> nonEmptyText,
    "price" -> text.transform((f1: String) => f1.toDouble, (f2: Double) => f2.toString)
  )(ProductDTO.apply)(ProductDTO.unapply)

  val form: Form[ProductDTO] = Form(mapping)

  def saveProduct: Action[AnyContent] = Action { implicit rc =>
    form.bindFromRequest.fold(
      _ => BadRequest,
      dto => {
        productService.saveProduct(dto)
        Ok(Json.toJson("Product saved"))
      }
    )
  }

  def updateProduct(): Action[AnyContent] = Action { implicit rc =>
    form.bindFromRequest.fold(
      _ => BadRequest,
      dto => {
        productService.updateProduct(dto)
        Ok(Json.toJson("Product updated"))
      }
    )
  }

  def deleteProduct(id: String): Action[AnyContent] = Action {
    productService.deleteProduct(id)
    Ok("Product deleted")
  }
}
