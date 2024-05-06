package module

import di.AppModule
import models.dao.repositories.{ProductRepository, ProductRepositoryImpl}
import models.services.{ProductService, ProductServiceImpl}
import utils.{Mapper, ModelMapper}

class ScrModule extends AppModule {

  override def configure(): Unit = {
    bindSingleton[ProductRepository, ProductRepositoryImpl]
    bindSingleton[ProductService, ProductServiceImpl]
    bindSingleton[Mapper, ModelMapper]
  }
}