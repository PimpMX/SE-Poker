package de.htwg

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.model.gameFieldComponent.playerBaseImpl.PlayerBuilder

class TexasHoldEmModule extends AbstractModule with ScalaModule {
  
    val defaultPlayerCount = 4

    override def configure() = {
        bindConstant().annotatedWith(Names.named("DefaultPlayerCount")).to(defaultPlayerCount)
        bind[ControllerInterface].to[controller.controllerBaseImpl.Controller]
        bind[GameFieldInterface].to[model.gameFieldComponent.gameFieldBaseImpl.GameField]
        bind[PlayerBuilderInterface].to[model.playerComponent.playerBaseImpl.PlayerBuilder]
        bind[]
    }
}
